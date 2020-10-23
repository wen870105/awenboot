package com.wen.awenboot.task;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.util.concurrent.RateLimiter;
import com.wen.awenboot.biz.service.ZhuangkuFileService;
import com.wen.awenboot.common.OkHttpUtil;
import com.wen.awenboot.common.ReadFilePageUtil;
import com.wen.awenboot.common.ThreadPoolThreadFactoryUtil;
import com.wen.awenboot.config.ZhuangkuConfig;
import com.wen.awenboot.integration.zhuangku.Result;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
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
    private static ExecutorService executor = new ThreadPoolExecutor(32, 64, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(10240),
            ThreadPoolThreadFactoryUtil.nameThreadFactory("okhttp-pool"), new ThreadPoolExecutor.CallerRunsPolicy());


    private static OkHttpUtil client = OkHttpUtil.getInstance();

    private int printCount = 0;

    private int printWriteCount = 0;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ZhuangkuConfig cfg;

    // 当前时间的分钟数,用来调整流控速率
    private int currentMinute;

    //    @Scheduled(cron = "*/5 * * * * ?")
    private void execute() {
        log.info("test" + System.currentTimeMillis());
    }

    @PostConstruct
    private void init() {
        Thread thd = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(10);
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

        int limit = cfg.getReadFileLimit();
        while (start < count) {
//            refreshLimitRateIfNeed(limiter);
            log.info("开始读取文件,流控速率={},name={},start={},limit={}", limiter.getRate(), file.getPath(), start, limit);
            List<String> strings = null;
            try {
                strings = ReadFilePageUtil.readListPage(file.getPath(), start, limit);
            } catch (Exception e) {
                log.error("读取数据文件异常,path={}", file.getPath(), e);
            }


            if (strings != null && strings.size() > 0) {
                for (String phone : strings) {
                    try {
                        limiter.acquire();
                        // 测试阶段单线程跑
//                        executor.execute(() -> {
                        try {
                            Result result = getResult(phone);
                            wirteData(phone, result, zkfs);
                        } catch (Throwable e) {
                            log.error("访问接口写入日志文件异常,phone={}", phone, e);
                        }
//                        });
                    } catch (Throwable e) {
                        log.error("访问接口写入日志文件异常,phone={}", phone, e);
                    }
                }
                zkfs.wirteTempFileProperties(file.getName(), (start + strings.size()));
            }
            start += limit;
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
        DateTime date = DateUtil.date();
        int hour = date.hour(true);
        int minute = date.minute();
        if (minute != currentMinute) {
            int rate = cfg.getRateLimiterQpsByHHmm(hour, minute);
            currentMinute = minute;
            limiter.setRate(rate);
            log.info("每分钟更新流控速率,minute={},流控速率rate={}", minute, rate);
        }
    }

    //    0000	查询成功，返回产品推荐查询结果。
    //    0001	查询成功，无产品推荐内容。
    //    1002	查询失败，请求手机号码格式不符合要求。
    //    1003	查询失败，系统内部故障。
    private Result getResult(String phone) {
        long start = System.currentTimeMillis();
        String url = cfg.getTargetUrl();
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        header.put("secretId", "9155CBA");
        header.put("requestRefId", "20200710616161AdfA335605");
        header.put("signature", "KUHcExujn2Q1n7F2Wc0xPUhQXo9wwiiVfJjz/vmIXmE=");

        Map<String, String> body = new HashMap<>();
        body.put("userId", phone);

        Response resp = client.postData(url, header, body);
        Result result = null;
        String ret = null;

        try {
            printCount++;
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

