package com.migu.tsg.damportal.task;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import com.migu.tsg.damportal.cache.TagDataCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @author wen
 * @version 1.0
 * @date 2020/10/26 10:29
 */
@Service
public class TagDataCacheTask {
    private static final Logger LOGGER = LoggerFactory.getLogger(TagDataCacheTask.class);

    @Autowired
    private TagDataCache tagDataCache;

    @Scheduled(cron = "0 */1 * * * ?")
    private void refresh1Min() {
        TimeInterval timer = DateUtil.timer();
        boolean flag = tagDataCache.refresh();
        LOGGER.info("每分钟定期检查标签是否更新flag={},耗时{}ms", flag, timer.interval());
    }
}
