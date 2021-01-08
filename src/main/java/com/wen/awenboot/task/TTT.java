package com.wen.awenboot.task;

import com.wen.awenboot.common.ThreadPoolThreadFactoryUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author wen
 * @version 1.0
 * @date 2021/1/8 16:25
 */
@Slf4j
public class TTT {

    public static void main(String[] args) {
        ExecutorService executor = new ThreadPoolExecutor(5, 5, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(2),
                ThreadPoolThreadFactoryUtil.nameThreadFactory("okhttp-pool"), new ThreadPoolExecutor.CallerRunsPolicy());
        for (int i = 0; i < 10; i++) {
            executor.execute(() -> {
                log.info("ha");
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    log.error("", e);
                }
            });
        }

//        try {
//            log.info("wait");
//            TimeUnit.SECONDS.sleep(30);
//        } catch (InterruptedException e) {
//            log.error("", e);
//        }
        log.info("end");
        try {
            log.info("wait");
            TimeUnit.SECONDS.sleep(30);
        } catch (InterruptedException e) {
            log.error("", e);
        }
    }
}
