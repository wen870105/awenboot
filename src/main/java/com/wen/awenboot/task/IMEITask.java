package com.wen.awenboot.task;

import com.wen.awenboot.config.ZhuangkuConfig;
import com.wen.awenboot.service.MiguTagApiDetailCntServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @author wen
 * @version 1.0
 * @date 2020/9/1 19:57
 */
@Service
@Slf4j
public class IMEITask {

    @Autowired
    private MiguTagApiDetailCntServiceImpl service;
    
    @Autowired
    private ZhuangkuConfig cfg;

    @Scheduled(cron = "10 0 0 * * ? ")
    public void taskDay() {
        if ("imei".equalsIgnoreCase(cfg.getTaskName())) {
            log.info("启动,{}", cfg.getTaskName());
        } else {
            return;
        }
        log.info("[每天执行],imei访问数量固定更新");
        service.addImeiCnt();
    }


}

