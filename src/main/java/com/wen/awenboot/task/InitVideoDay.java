package com.wen.awenboot.task;

import cn.hutool.core.date.DateTime;
import com.alibaba.fastjson.JSON;
import com.google.common.util.concurrent.RateLimiter;
import com.wen.awenboot.biz.service.ResolverFileService2;
import com.wen.awenboot.biz.service.ZhuangkuFileService;
import com.wen.awenboot.common.OkHttpUtil;
import com.wen.awenboot.common.ReadFilePageUtil;
import com.wen.awenboot.config.ZhuangkuConfig;
import com.wen.awenboot.integration.zhuangku.Result;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.List;

/**
 * @author wen
 * @version 1.0
 * @date 2020/9/1 19:57
 */
@Service
@Slf4j
public class InitVideoDay {
    @Autowired
    private OkHttpUtil client;

    private int printCount = 0;

    private int printWriteCount = 0;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ZhuangkuConfig cfg;

    // 当前时间的分钟数,用来调整流控速率
    private int currentMinute;


    @Scheduled(cron = "0/20 * *  * * ? ")
    public boolean task() {
//        # video,imei,music
        if ("video".equalsIgnoreCase(cfg.getTaskName())) {
            log.info("[启动员工每日撞库任务],{}", cfg.getTaskName());
        } else {
            return false;
        }
        execute1();

        return true;
    }

    private void execute1() {
        long start = System.currentTimeMillis();
        File file = new File(cfg.getDataSourceDir() + cfg.getDayPeriodFile());
        if (!file.exists()) {
            log.error("咪咕员工文件不存在,path={}", file.getPath());
            return;
        }

        // 流控qps
        // 下面是单线程跑,这个limiter没意义
        RateLimiter limiter = RateLimiter.create(1000);

        File f = file;
        ZhuangkuFileService zkfs = new ZhuangkuFileService(cfg, f.getName() + "_" + DateTime.now().toString("yyyyMMddHHmmss"));

        exportFile(f, zkfs, limiter, 0);

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
                log.info("开始读取[用户文件],流控速率={},start={},limit={},printCount={},name={}", limiter.getRate(), start, limit, printCount, file.getPath());
                strings = ReadFilePageUtil.readListPage(file.getPath(), start, limit);
            } catch (Exception e) {
                log.error("读取数据文件异常,path={}", file.getPath(), e);
            }

            if (strings != null && strings.size() > 0) {
                isUpdate = true;
                for (String phone : strings) {
                    try {
                        Result result = getResult(phone);
                        wirteData(phone, result, zkfs);
                    } catch (Throwable e) {
                        log.error("访问接口写入日志文件异常,phone={}", phone, e);
                    }
                }
            }
            start += limit;
        }
        if (isUpdate) {
            new ResolverFileService2(cfg, zkfs.getFile().getName()).asyncExport();
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

