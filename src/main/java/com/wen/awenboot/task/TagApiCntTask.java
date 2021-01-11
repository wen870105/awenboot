package com.wen.awenboot.task;

import com.wen.awenboot.cache.TagDetailCntCache;
import com.wen.awenboot.service.MiguTagApiDetailCntServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;

/**
 * @author wen
 * @version 1.0
 * @date 2021/1/11 14:15
 */
@Service
@Slf4j
public class TagApiCntTask {

    @Autowired
    private MiguTagApiDetailCntServiceImpl service;

    @Autowired
    private TagDetailCntCache cntCache;

    @Scheduled(cron = "*/5 * * * * ? ")
//    @Scheduled(cron = "0 */5 * * * ? ")
    public void task5Min() {
        cntCache.taskUpateCacheToDb();
    }


    @Scheduled(cron = "10 0 0 * * ? ")
    public void taskDay() {
        log.info("[每天执行],imei访问数量固定更新");
        cntCache.refreshCache();
        service.addImeiCnt();
    }

    @PreDestroy
    public void destroy() {
        log.info("[关闭应用执行同步一次缓存]");
        cntCache.taskUpateCacheToDb();
    }
}
