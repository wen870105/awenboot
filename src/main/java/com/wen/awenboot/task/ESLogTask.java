package com.wen.awenboot.task;

import cn.hutool.core.date.TimeInterval;
import com.wen.awenboot.biz.service.BrandFileService;
import com.wen.awenboot.biz.service.ZhuangkuFileService;
import com.wen.awenboot.cache.EsCntSerivce;
import com.wen.awenboot.common.ReadFilePageUtil;
import com.wen.awenboot.common.ThreadPoolThreadFactoryUtil;
import com.wen.awenboot.config.ZhuangkuConfig;
import com.wen.awenboot.utils.RtScopeSerivce;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * brand日志接口采集
 *
 * @author wen
 * @version 1.0
 * @date 2020/9/1 19:57
 */
@Service
@Slf4j
public class ESLogTask {
    private static ExecutorService executor = new ThreadPoolExecutor(64, 64, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(1024),
            ThreadPoolThreadFactoryUtil.nameThreadFactory("okhttp-pool"), new ThreadPoolExecutor.CallerRunsPolicy());

    @Autowired
    private ZhuangkuConfig cfg;

    @Autowired
    private RetLogger retLogger;

    private Map<String, Long> rpcMap = new HashMap<>();

    private static final byte[] SPLIT = {0x01};

    // 当前时间的分钟数,用来调整流控速率
    private int currentMinute;

    @PostConstruct
    private void init() {
        if ("es".equalsIgnoreCase(cfg.getTaskName())) {
            log.info("启动,{}", cfg.getTaskName());
        } else {
            return;
        }

        File dir = new File(cfg.getDataSourceDir());
        File[] files = dir.listFiles(f -> {
            return f.getName().endsWith(".DATA");
        });
        for (File file : files) {
            long start = System.currentTimeMillis();
            execute1(file);
            long end = System.currentTimeMillis();
            log.info("导出{}完毕,耗时{}ms", file.getName(), end - start);
        }

    }

    public void execute1(File file) {
        long start = System.currentTimeMillis();
        exportFile(file);
        long end = System.currentTimeMillis();
        retLogger.getLogger().info("导出耗时{}ms,file={},", end - start, file.getName());
    }


    private void exportFile(File file) {
        RtScopeSerivce rtService = new RtScopeSerivce();
        EsCntSerivce cntService = new EsCntSerivce();
        BrandFileService zkfs = new BrandFileService(cfg, file.getName());
        long count = ZhuangkuFileService.lineCount(file);

        int limit = cfg.getReadFileLimit();
        int start = 0;
        while (start < count) {
            List<String> strings = null;
            try {
                log.info("读取文件,start={},limit={},name={}", start, limit, file.getPath());
                strings = ReadFilePageUtil.readListPage(file.getPath(), start, limit);
            } catch (Exception e) {
                log.error("读取数据文件异常,path={}", file.getPath(), e);
            }

            if (strings != null && strings.size() > 0) {
                for (String logStr : strings) {
                    try {
                        if (StringUtils.isNotBlank(cfg.getEsKey())) {
                            getResult(cfg.getEsKey(), splitStrAndRpc(logStr), rtService, cntService);
                        } else {
                            getResult(file.getName().split("_0_DAY")[0], splitStrAndRpc(logStr), rtService, cntService);
                        }

                    } catch (Throwable e) {
                        log.error("logStr={}", logStr, e);
                    }
                }
            }
            start += limit;
        }
        retLogger.getLogger().info("耗时分布={}", rtService.toString());
        retLogger.getLogger().info("各种调用总次数={}", cntService.toString());

    }

    private Kvs splitStrAndRpc(String logStr) {
        try {
            String[] split = logStr.split(new String(SPLIT));
            Kvs kvs = new Kvs();
            kvs.setPhone(split[0]);
            String[] vals = split[1].split(",");
            List<String> list = new ArrayList<>();
            for (String tmp : vals) {
                list.add(tmp.split(":")[0]);
            }

            kvs.setTags(list);
            return kvs;
        } catch (Exception e) {
            log.error("字符错误", e);
        }
        return null;
    }


    private long getEsRet(String esKey, String tag) {
//        try {
//            return getKeyWord(esKey, tag);
//        } catch (IOException e) {
//            log.error("", e);
//            return 0;
//        }
        return 0;
    }

//    // 关键字查询
//    public long getKeyWord(String name, String val) throws IOException {
//        SearchRequestBuilder searchRequestBuilder = esClient.build()
////                .setQuery(QueryBuilders.wildcardQuery(name, "*" + val.trim() + "*"))
//                .setQuery(QueryBuilders.matchPhraseQuery(name.trim(), val.trim()))
//                .setSize(0);
//        SearchResponse searchResponse = searchRequestBuilder.get();
//
//        // 获取命中次数，查询结果有多少对象
//        return searchResponse.getHits().getTotalHits();
//    }

    private void getResult(String esKey, Kvs kvs, RtScopeSerivce rtService, EsCntSerivce cntService) {
        List<String> tags = kvs.getTags();
        CountDownLatch cdl = new CountDownLatch(tags.size());

        cntService.addPhoneCounter();
        tags.forEach(p -> {
            executor.execute(() -> {
                try {
                    TimeInterval ti = new TimeInterval();
                    cntService.addEnumCounter();
                    String mapKey = buildRpcMapKey(esKey, p);
                    Long rpcRet = rpcMap.get(mapKey);
                    if (rpcRet == null) {
                        long esRet = getEsRet(esKey, p);
                        rpcMap.put(mapKey, esRet);
                        long interval = ti.interval();
                        log.info("es耗时{}ms,count={},esKey={},key={} ,phone={}", interval, esRet, esKey, p, kvs.getPhone());
                        rtService.addVal(interval);
                        cntService.addRpcCounter();
                    }
                } catch (Exception e) {
                    log.error("", e);
                } finally {
                    cdl.countDown();
                }
            });

        });
        try {
            // 这里的目的为了少丢失数据,线程不能一直等待rpc的结果
            cdl.await(6, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            log.error("", e);
        }
    }

    private String buildRpcMapKey(String esKey, String tag) {
        return esKey + "_" + tag;
    }

    @Data
    private static class Kvs {
        private String phone;
        private List<String> tags;
    }
}

