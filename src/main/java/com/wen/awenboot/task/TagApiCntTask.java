package com.wen.awenboot.task;

import com.wen.awenboot.cache.TagDetailCntCache;
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
    private TagDetailCntCache cntCache;

    @Scheduled(cron = "*/30 * * * * ? ")
    public void task5Min() {
        int i = cntCache.taskUpateCacheToDb();
        log.info("更新缓存到db数量{}", i);
    }


    @Scheduled(cron = "10 0 0 * * ? ")
    public void taskDay() {
        log.info("[每天执行],imei访问数量固定更新");
        cntCache.refreshCache();
    }

    @PreDestroy
    public void destroy() {
        log.info("[关闭应用执行同步一次缓存]");
        cntCache.taskUpateCacheToDb();
    }
}
