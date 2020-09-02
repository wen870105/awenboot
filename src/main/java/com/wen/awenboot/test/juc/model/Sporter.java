package com.wen.awenboot.test.juc.model;

import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author wen
 * @version 1.0
 * @date 2020/6/23 19:46
 */
public class Sporter {
    private static Logger logger = LoggerFactory.getLogger(Sporter.class);
    private String name;
    private CountDownLatch cdl;

    public Sporter(String name, CountDownLatch cdl) {
        this.name = name;
        this.cdl = cdl;
    }

    public void doWhat() {
        logger.info("{} 准备就绪.", name);
        cdl.countDown();
        int i = RandomUtils.nextInt(100, 1000);
        try {
            TimeUnit.MILLISECONDS.sleep(i);
        } catch (InterruptedException e) {
            logger.error("", e);
        }
        logger.info("{} 跑完{}ms.", name, i);

    }

}
