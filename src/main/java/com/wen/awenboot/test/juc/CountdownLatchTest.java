package com.wen.awenboot.test.juc;

import com.wen.awenboot.test.juc.model.Sporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author wen
 * @version 1.0
 * @date 2020/6/23 19:44
 */
public class CountdownLatchTest {
    private static Logger logger = LoggerFactory.getLogger(CountdownLatchTest.class);

    public static void main(String[] args) {
        logger.info("testsetsetset");
        final CountDownLatch latch = new CountDownLatch(2);
        //第一个子线程执行
        ExecutorService es1 = Executors.newCachedThreadPool();
        es1.execute(new Runnable() {
            @Override
            public void run() {
                new Sporter("z1", latch).doWhat();
            }
        });
        es1.execute(new Runnable() {
            @Override
            public void run() {
                new Sporter("lisi", latch).doWhat();
                ;
            }
        });
        try {
            latch.await(100, TimeUnit.MILLISECONDS);
            es1.shutdown();
            logger.info("开跑");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
