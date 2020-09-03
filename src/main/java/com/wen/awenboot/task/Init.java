package com.wen.awenboot.task;

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
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
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
public class Init {

    private static ExecutorService executor = new ThreadPoolExecutor(32, 64, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(10240),
            ThreadPoolThreadFactoryUtil.nameThreadFactory("okhttp-pool"));

    private static OkHttpUtil client = OkHttpUtil.getInstance();

    private int printFlag = 100;

    private int printCount = 0;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ZhuangkuConfig cfg;

    //    @Scheduled(cron = "*/5 * * * * ?")
    private void execute() {
        log.info("test" + System.currentTimeMillis());
    }

    @PostConstruct
    private void init() {
        final ZhuangkuFileService zkfs = new ZhuangkuFileService(cfg, DateTime.now().toString("yyyyMMdd"));
        Thread thd = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                log.error("", e);
            }
            execute1(zkfs);
        });
        thd.start();

//        Thread thd2 = new Thread(() -> {
//            try {
//                TimeUnit.SECONDS.sleep(6);
//            } catch (InterruptedException e) {
//                log.error("", e);
//            }
//            int i = 0;
//            while (true) {
//                try {
//                    TimeUnit.SECONDS.sleep(1);
//                } catch (InterruptedException e) {
//                    log.error("", e);
//                }
//                log.info("写日志开始" + i);
//                zkfs.wirteTempFileProperties("testName", (++i));
//            }
//        });
//        thd2.start();
    }

    private void execute1(ZhuangkuFileService zkfs) {
        long start = System.currentTimeMillis();
        File file = new File(cfg.getDataSourceDir());
        if (!file.exists()) {
            log.error("数据目录不存在,path={}", cfg.getDataSourceDir());
            return;
        }

        // 流控qps
        RateLimiter limiter = RateLimiter.create(cfg.getRateLimiterQps());

        File[] files = file.listFiles();


        // TempFileProperties 表示是否当前系统正在执行的文件名称和行数,用来系统崩溃后再次运行
        boolean export = StringUtils.isBlank(zkfs.getTempFileProperties()) ? true : false;
        for (File f : files) {
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
        zkfs.endExport();
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

        int limit = 100;
//        int limit = 6;
        while (start < count) {
            log.info("开始读取文件,name={},start={},limit={}", file.getPath(), start, limit);
            List<String> strings = null;
            try {
                strings = ReadFilePageUtil.readListPage(file.getPath(), start, limit);
                log.info("读取文件行数,size={}", strings.size());
            } catch (Exception e) {
                log.error("读取数据文件异常,path={}", file.getPath(), e);
            }

            if (strings != null) {
                for (String phone : strings) {
                    limiter.acquire();
                    Result result = getResult(phone);
                    wirteData(phone, result, zkfs);
                }
            }
            start += limit;
            zkfs.wirteTempFileProperties(file.getName(), start);
        }
    }


    private void wirteData(String phone, Result result, ZhuangkuFileService zkfs) {
        if ("0000".equalsIgnoreCase(result.getResultCode())) {
            StringBuilder sb = new StringBuilder(50);
            sb.append(phone).append("=").append(result.getProductInfo().getProducts()).append("\r\n");
            zkfs.wirte(sb.toString());
            log.info("成功返回的结果,ret={},phone={}", JSON.toJSONString(result), phone);
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
        } catch (IOException e) {
            log.error("请求异常", e);
        }

        long end = System.currentTimeMillis();
        if (printCount % printFlag == 0) {
            log.info("耗时{}ms,间隔{}次请求打印一次日志,ret={},phone={}", (end - start), printFlag, ret, phone);
        }

        return result;
    }
}

