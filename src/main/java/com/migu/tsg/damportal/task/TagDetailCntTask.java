package com.migu.tsg.damportal.task;

import com.migu.tsg.damportal.cache.TagDetailCntCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author wen
 * @version 1.0
 * @date 2020/10/23 15:26
 */
@Service
public class TagDetailCntTask {
    private static final Logger LOGGER = LoggerFactory.getLogger(TagDetailCntTask.class);

    @Autowired
    private TagDetailCntCache cntCache;

    @PostConstruct
    private void init() {
        LOGGER.info("初始化标签详情调用次数任务");
    }

    @Scheduled(cron = "*/5 * * * * ?")
    private void execute5s() {
        cntCache.taskUpateCacheToDb();
    }

    @Scheduled(cron = "0 0 * * * ?")
    private void execute10s() {
        LOGGER.info("每日凌晨执行更新,同步所有调用次数");
        cntCache.refreshCache();
    }

    @PreDestroy
    private void des() {
        LOGGER.info("关闭服务时同步一次缓存,减少数据丢失");
        execute5s();
    }
}
