package com.wen.awenboot.task;

import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.util.concurrent.RateLimiter;
import com.wen.awenboot.biz.service.ResolverFileService;
import com.wen.awenboot.biz.service.ZhuangkuFileService;
import com.wen.awenboot.common.OkHttpUtil;
import com.wen.awenboot.common.ReadFilePageUtil;
import com.wen.awenboot.common.ThreadPoolThreadFactoryUtil;
import com.wen.awenboot.config.ZhuangkuConfig;
import com.wen.awenboot.integration.zhuangku.ResultMusic;
import com.wen.awenboot.utils.PaasSecretHandler;
import com.wen.awenboot.utils.RateLimiterUtils;
import com.wen.awenboot.utils.TagFileUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author wen
 * @version 1.0
 * @date 2020/9/1 19:57
 */
@Service
@Slf4j
public class InitMusic {
    private static ExecutorService executor = new ThreadPoolExecutor(64, 64, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(1024),
            ThreadPoolThreadFactoryUtil.nameThreadFactory("okhttp-pool"), new ThreadPoolExecutor.CallerRunsPolicy());


    @Autowired
    private OkHttpUtil client;

    private transient int printCount = 0;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ZhuangkuConfig cfg;

    // 当前时间的分钟数,用来调整流控速率
    private int currentMinute;

    @PostConstruct
    private void init() {
        if ("music".equalsIgnoreCase(cfg.getTaskName())) {
            log.info("启动,{}", cfg.getTaskName());
        } else {
            return;
        }
        Thread thd = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                log.error("", e);
            }
            execute1();
        });
        thd.start();

    }

    private void execute1() {
        long start = System.currentTimeMillis();
        File file = new File(cfg.getDataSourceDir());
        if (!file.exists()) {
            log.error("数据目录不存在,path={}", cfg.getDataSourceDir());
            return;
        }

        // 流控qps
        RateLimiter limiter = RateLimiter.create(cfg.getRateLimiterQps());

        List<File> files = Arrays.stream(file.listFiles()).sorted((f1, f2) -> f1.getName().compareTo(f2.getName()))
                .filter(p -> cfg.getIncludeDataFileList().contains(p.getName()))
                .collect(Collectors.toList());
        log.info("读取文件列表:{}", files.toString());
        for (File f : files) {
            ZhuangkuFileService zkfs = new ZhuangkuFileService(cfg, f.getName());
            // TempFileProperties 表示是否当前系统正在执行的文件名称和行数,用来系统崩溃后再次运行
            boolean export = StringUtils.isBlank(zkfs.getTempFileProperties()) ? true : false;
            if (export) {
                exportFile(f, zkfs, limiter, 0);
            } else {
                String[] properties = zkfs.getTempFileProperties().split("\\$");
                // 文件名称相同
                if (f.getName().equalsIgnoreCase(properties[1])) {
                    export = true;
                    log.info("延续上次导出记录,name={},row={}", f.getName(), properties[2]);
                    exportFile(f, zkfs, limiter, Integer.valueOf(properties[2]));
                }
            }
        }
        try {
            TagFileUtils.cpBakRetDir(cfg.getCpRetDir());
        } catch (IOException e) {
            log.error("", e);
        }
        long end = System.currentTimeMillis();
        log.info("导出完毕,耗时{}ms", end - start);
    }


    /**
     * @param file
     * @param zkfs
     * @param limiter
     * @param start   文件起始位置
     */
    private void exportFile(File file, ZhuangkuFileService zkfs, RateLimiter limiter, int start) {
        long count = ZhuangkuFileService.lineCount(file);

        boolean isUpdate = false;
        int limit = cfg.getReadFileLimit();
        while (start < count) {
            List<String> strings = null;
            try {
                refreshLimitRateIfNeed(limiter);
                log.info("读取文件,流控速率={},start={},limit={},printCount={},name={}", (int) limiter.getRate(), start, limit, printCount, file.getPath());
                strings = ReadFilePageUtil.readListPage(file.getPath(), start, limit);
            } catch (Exception e) {
                log.error("读取数据文件异常,path={}", file.getPath(), e);
            }


            if (strings != null && strings.size() > 0) {
                isUpdate = true;
                CountDownLatch cdl = new CountDownLatch(strings.size());
                for (String phone : strings) {
                    try {
                        limiter.acquire();
                        executor.execute(() -> {
                            String resp = null;
                            try {
                                resp = getResult(phone);
                                if (StrUtil.isBlank(resp)) {
                                    return;
                                }
                                ResultMusic ret = JSON.parseObject(resp, ResultMusic.class);
                                wirteData(phone, ret, zkfs);
                            } catch (Throwable e) {
                                log.error("访问接口写入日志文件异常resp={},phone={}", resp, phone, e);
                            } finally {
                                cdl.countDown();
                            }
                        });
                    } catch (Throwable e) {
                        log.error("访问接口写入日志文件异常,phone={}", phone, e);
                    }
                }
                try {
                    // 这里的目的为了少丢失数据,线程不能一直等待rpc的结果
                    cdl.await(10, TimeUnit.SECONDS);
                    // 记录已经完成文件的行数
                    zkfs.wirteTempFileProperties(file.getName(), (start + strings.size()));
                } catch (InterruptedException e) {
                    log.error("", e);
                }
            }
            start += limit;
        }
        if (isUpdate) {
            new ResolverFileService(cfg, file.getName()).asyncExport();
        }
        zkfs.endExport();
    }


    private void wirteData(String phone, ResultMusic result, ZhuangkuFileService zkfs) {
        if ("0000".equalsIgnoreCase(result.getHead().getResponseCode())) {
            StringBuilder sb = new StringBuilder(50);
            sb.append(phone).append("=").append(result.getResponse().getParam().get(0).getProduct_info()).append("\r\n");
            zkfs.wirte(sb.toString());
        }
    }

    private void refreshLimitRateIfNeed(RateLimiter limiter) {
        Integer minute = RateLimiterUtils.refreshLimitRateIfNeed(limiter, cfg, currentMinute);
        if (minute != null) {
            currentMinute = minute;
        }
    }

    private String getResult(String phone) {
        String url = cfg.getTargetUrl();
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        String body = null;
        try {
            body = PaasSecretHandler.getRequestBody(phone);
        } catch (Exception e) {
            log.error("", e);
            return null;
        }

        TimeInterval timeInterval = new TimeInterval();
        String resp = null;
        try {
            printCount++;
            resp = client.postJson(url, header, body);
        } catch (IOException e) {
            log.error("超时超时重试,耗时{}ms,body={},请求异常url={},phone={}", timeInterval.interval(), body, url, phone, e.getMessage());

            try {
                printCount++;
                resp = client.postJson(url, header, body);
            } catch (IOException e1) {
                log.error("耗时{}ms,body={},请求异常url={},phone={}", timeInterval.interval(), body, url, phone, e);
            }
        } finally {
            if (printCount % cfg.getPrintFlag() == 0) {
                log.info("[定期抽查请求]耗时{}ms,间隔{}次请求打印,ret={},phone={}", timeInterval.interval(), cfg.getPrintFlag(), resp, phone);
            }
        }

        return resp;
    }
}

