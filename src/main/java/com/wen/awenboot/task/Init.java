package com.wen.awenboot.task;

import com.alibaba.fastjson.JSON;
import com.google.common.util.concurrent.RateLimiter;
import com.wen.awenboot.biz.service.ResolverFileService;
import com.wen.awenboot.biz.service.ZhuangkuFileService;
import com.wen.awenboot.cache.TagDetailCntCache;
import com.wen.awenboot.common.OkHttpUtil;
import com.wen.awenboot.common.ReadFilePageUtil;
import com.wen.awenboot.common.SpringUtil;
import com.wen.awenboot.common.ThreadPoolThreadFactoryUtil;
import com.wen.awenboot.config.SpringConfig;
import com.wen.awenboot.config.ZhuangkuConfig;
import com.wen.awenboot.enums.ApiDetailCntEnum;
import com.wen.awenboot.integration.zhuangku.Result;
import com.wen.awenboot.utils.RateLimiterUtils;
import com.wen.awenboot.utils.TagFileUtils;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
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
public class Init {
    private static ExecutorService executor = new ThreadPoolExecutor(32, 32, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(1024),
            ThreadPoolThreadFactoryUtil.nameThreadFactory("okhttp-pool"), new ThreadPoolExecutor.CallerRunsPolicy());

    @Autowired
    private OkHttpUtil client;

    private int printCount = 0;

    private int printWriteCount = 0;

    private int loopCount = 0;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ZhuangkuConfig cfg;

    // 当前时间的分钟数,用来调整流控速率
    private int currentMinute;

    @PostConstruct
    private void init() {
//        # video,imei,music
        if ("video".equalsIgnoreCase(cfg.getTaskName())) {
            log.info("启动,{}", cfg.getTaskName());
        } else {
            return;
        }

        Thread thd = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                log.error("", e);
            }
            executeLoop();
        });
        thd.start();

    }

    private void executeLoop() {
        while (true) {
            loopCount++;
            execute1();
            checkFileEndFlag();
            try {
                TagFileUtils.cpBakRetDir(cfg.getCpRetDir());
            } catch (IOException e) {
                log.error("", e);
            }
            log.info("====执行[{}]轮完毕", loopCount);
            log.info("====执行[{}]轮完毕", loopCount);
            log.info("====执行[{}]轮完毕", loopCount);
            try {
                // 逻辑有拷贝文件功能这里休眠长一点
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                log.error("", e);
            }
            deleteTempFile();
        }
    }

    private void deleteTempFile() {
        try {
            final List<Path> collect = Files.list(Paths.get(cfg.getExportDir())).filter(p -> p.toString().endsWith(".txt")).collect(Collectors.toList());
            collect.forEach(p -> {
                try {
                    Files.delete(p);
                } catch (IOException e) {
                    log.error("删除异常", e);
                }
            });
            log.error("删除临时文件,文件列表={}", collect.toString());
        } catch (Exception e) {
            log.error("Files.list异常", e);
        }
    }

    /**
     * 检查文件是否处理完成,没完成就等
     */
    private void checkFileEndFlag() {
        LinkedList<String> exportFileList = SpringUtil.getBean(SpringConfig.class).getExportFileList();

        while (true) {
            if (exportFileList.size() == 0) {
                log.info("检查文件处理完成");
                return;
            }
            try {
                log.info("当前文件还未处理完成,休眠60s,当前文件内容是{}", exportFileList.iterator().toString());
                // 逻辑有拷贝文件功能这里休眠长一点
                TimeUnit.SECONDS.sleep(60);
            } catch (InterruptedException e) {
                log.error("", e);
            }
        }

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
                log.info("开始读取文件,流控速率={},start={},limit={},lineCount={},printCount={},name={}", (int) limiter.getRate(), start, limit, count, printCount, file.getPath());
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
                            try {
                                TagDetailCntCache.getInstance().incrementAndGet(ApiDetailCntEnum.VIDEO.getCode());
                                Result result = getResult(phone);
                                wirteData(phone, result, zkfs);
                            } catch (Throwable e) {
                                log.error("访问接口写入日志文件异常,phone={}", phone, e);
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


    private void wirteData(String phone, Result result, ZhuangkuFileService zkfs) {
        if ("0000".equalsIgnoreCase(result.getResultCode())) {
            printWriteCount++;
            StringBuilder sb = new StringBuilder(50);
            sb.append(phone).append("=").append(result.getProductInfo().getProducts()).append("\r\n");
            zkfs.wirte(sb.toString());
        }
    }

    private void refreshLimitRateIfNeed(RateLimiter limiter) {
        Integer minute = RateLimiterUtils.refreshLimitRateIfNeed(limiter, cfg, currentMinute);
        if (minute != null) {
            currentMinute = minute;
        }
    }

    //    0000	查询成功，返回产品推荐查询结果。
    //    0001	查询成功，无产品推荐内容。
    //    1002	查询失败，请求手机号码格式不符合要求。
    //    1003	查询失败，系统内部故障。
    private Result getResult(String phone) {
        long start = System.currentTimeMillis();
        String url = cfg.getTargetUrl() + phone;
        Response resp = client.getData(url);
        Result result = null;
        String ret = null;

        printCount++;
        try {
            ret = resp.body().string();
            result = JSON.parseObject(ret, Result.class);
        } catch (Exception e) {
            log.error("请求异常,ret={}", ret, e);
        }

        long end = System.currentTimeMillis();
        if (printCount % cfg.getPrintFlag() == 0) {
            log.info("耗时{}ms,间隔{}次请求打印一次日志,ret={},phone={}", (end - start), cfg.getPrintFlag(), ret, phone);
        }

        return result;
    }


}

