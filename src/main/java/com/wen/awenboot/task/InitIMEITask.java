package com.wen.awenboot.task;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.util.concurrent.RateLimiter;
import com.wen.awenboot.biz.service.ZhuangkuFileService;
import com.wen.awenboot.cache.TagDetailCntCache;
import com.wen.awenboot.common.OkHttpUtil;
import com.wen.awenboot.common.ReadFilePageUtil;
import com.wen.awenboot.common.ThreadPoolThreadFactoryUtil;
import com.wen.awenboot.config.ZhuangkuConfig;
import com.wen.awenboot.enums.ApiDetailCntEnum;
import com.wen.awenboot.integration.zhuangku.ResultImei;
import com.wen.awenboot.utils.DateUtils;
import com.wen.awenboot.utils.ImeiUtils;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author wen
 * @version 1.0
 * @date 2020/9/1 19:57
 */
@Service
@Slf4j
public class InitIMEITask {
    private static ExecutorService executor = new ThreadPoolExecutor(32, 64, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(10240),
            ThreadPoolThreadFactoryUtil.nameThreadFactory("okhttp-pool"), new ThreadPoolExecutor.CallerRunsPolicy());


    @Autowired
    private OkHttpUtil client;

    private int printCount = 0;

    @Autowired
    private ZhuangkuConfig cfg;

    // 当前时间的分钟数,用来调整流控速率
    private int currentMinute;

    private long successCounter = 0;
    private long faultCounter = 0;
    private long exceptionCounter = 0;

//    @Value("#{zhuangkuConfig.imeiTaskSchedule}")
//    private String test;

    @Scheduled(cron = "#{zhuangkuConfig.imeiTaskSchedule}")
//    @PostConstruct
    private void task() {
        doTask();
    }

    private void doTask() {
        if ("imei".equalsIgnoreCase(cfg.getTaskName())) {
            log.info("启动,{}", cfg.getTaskName());
        } else {
            return;
        }
        int loopEnd = 3;
        int sleep = 60;
        String last3Day = DateUtils.asString(DateUtils.addDays(new Date(), -3), "yyyyMMdd");
        for (int i = 1; i <= loopEnd; i++) {
            if (execute1(last3Day)) {
                break;
            }
            if (i != loopEnd) {
                log.info("文件不存在等待重复执行,正在休眠等待{}min,轮数={}/{}", sleep, i, loopEnd);
                try {
                    TimeUnit.MINUTES.sleep(sleep);
                } catch (InterruptedException e) {
                    log.error("休眠异常", e);
                }
            }
        }
    }

    public boolean execute1(String date) {
        long start = System.currentTimeMillis();

        successCounter = 0;
        faultCounter = 0;
        exceptionCounter = 0;
        File file = new File(cfg.getDataSourceDir() + cfg.getImeiFilePrefix() + date + ".txt");

        if (!file.exists()) {
            log.error("数据不存在,path={}", file.getPath());
            return false;
        }

        // 流控qps
        RateLimiter limiter = RateLimiter.create(cfg.getRateLimiterQps());

        File f = file;
        log.info("读取文件列表:{}", f.toString());


        ZhuangkuFileService zkfs = new ZhuangkuFileService(cfg, f.getName());
        // TempFileProperties 表示是否当前系统正在执行的文件名称和行数,用来系统崩溃后再次运行
        boolean export = StringUtils.isBlank(zkfs.getTempFileProperties()) ? true : false;
        if (export) {
            exportFile(f, zkfs, limiter, 0);
        } else {
            String[] properties = zkfs.getTempFileProperties().split("\\$");
            // 文件名称相同
            if (f.getName().equalsIgnoreCase(properties[1])) {
                log.info("延续上次导出记录,name={},row={}", f.getName(), properties[2]);
                exportFile(f, zkfs, limiter, Integer.valueOf(properties[2]));
            }
        }
        long end = System.currentTimeMillis();
        log.info("导出完毕path={},耗时{}ms", file.getPath(), end - start);
        return true;
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
        boolean isUpdate = false;
        while (start < count) {
            refreshLimitRateIfNeed(limiter);

            log.info("success={},fault={},exception={},开始读取文件,流控速率={},name={},start={},limit={}", successCounter, faultCounter, exceptionCounter, limiter.getRate(), file.getPath(), start, limit);
            List<String> strings = null;
            try {
                strings = ReadFilePageUtil.readListPage(file.getPath(), start, limit);
            } catch (Exception e) {
                log.error("读取数据文件异常,path={}", file.getPath(), e);
            }


            if (strings != null && strings.size() > 0) {
                isUpdate = true;
                for (String imei : strings) {
                    try {
                        limiter.acquire();
                        executor.execute(() -> {
                            try {
                                TagDetailCntCache.getInstance().incrementAndGet(ApiDetailCntEnum.IMEI.getCode());
                                ResultImei result = getResult(imei);
                                wirteData(imei, result, zkfs);
                            } catch (Throwable e) {
                                log.error("访问接口写入日志文件异常", e);
                            }
                        });
                    } catch (Throwable e) {
                        log.error("访问接口写入日志文件异常,", e);
                    }
                }
                try {
                    // 记录已经完成文件的行数
                    zkfs.wirteTempFileProperties(file.getName(), (start + strings.size()));
                } catch (Exception e) {
                    log.error("", e);
                }
            }
            start += limit;
        }
        zkfs.endExport();
    }


    private void wirteData(String imei, ResultImei result, ZhuangkuFileService zkfs) {
        if ("0000".equalsIgnoreCase(result.getResultCode())) {
            StringBuilder sb = new StringBuilder(50);
            sb.append(result.getServNumInfo().getServ_num()).append("\t").append(imei).append("\r\n");
            zkfs.wirte(sb.toString());
            successCounter++;
        } else {
            faultCounter++;
        }
    }

    private void refreshLimitRateIfNeed(RateLimiter limiter) {
        limiter.setRate(cfg.getRateLimiterQps() + RandomUtil.randomInt(1, 10));
    }

    //    0000	查询成功，返回产品推荐查询结果。
    //    0001	查询成功，无产品推荐内容。
    //    1002	查询失败，请求手机号码格式不符合要求。
    //    1003	查询失败，系统内部故障。
    private ResultImei getResult(String imei) {
        long start = System.currentTimeMillis();
        String rpcImei = ImeiUtils.getImeiBy14(imei);
        String url = cfg.getTargetUrl() + rpcImei;
        Response resp = client.getData(url);
        ResultImei result = null;
        String ret = null;

        printCount++;
        try {
            ret = resp.body().string();
            result = JSON.parseObject(ret, ResultImei.class);
        } catch (Exception e) {
            exceptionCounter++;
            log.error("请求异常,ret={}", ret, e);
        }

        long end = System.currentTimeMillis();
        if (printCount % cfg.getPrintFlag() == 0) {
            log.info("耗时{}ms,间隔{}次请求打印一次日志,ret={},url={}", (end - start), cfg.getPrintFlag(), ret, url);
        }

        return result;
    }
}

