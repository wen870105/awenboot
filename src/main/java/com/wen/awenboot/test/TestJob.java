package com.wen.awenboot.test;

/**
 * @author wen
 * @version 1.0
 * @date 2021/5/12 14:11
 */

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @descrition
 * @since 2020-06-01 15:09
 */
@Component
@Slf4j
public class TestJob {
    @PostConstruct
    private void init() {
        log.info("test111111111111111111111111");
    }

//    /**
//     * @description 每隔1min打印一次
//     * @date 2020/6/1 15:10
//     */
//    @Scheduled(cron = "10/10 * * * * ?")
//    // lockAtMostFor为锁默认持有时间，会覆盖启动类中的默认持有时间
//    @SchedulerLock(name = "print1243", lockAtMostFor = "3m")
//    public void print() {
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
//        log.info(df.format(new Date()));
//    }
}