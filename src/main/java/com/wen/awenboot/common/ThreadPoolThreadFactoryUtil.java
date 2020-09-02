/**
 * LY.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.wen.awenboot.common;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程池工厂工具,主要用来设置线程名称
 *
 * @author wsy48420
 * @version $Id: ThreadPoolThreadFactoryUtil.java, v 0.1 2019年6月5日 下午4:09:31 wsy48420 Exp $
 */
public class ThreadPoolThreadFactoryUtil {

    public static ThreadFactory nameThreadFactory(String namePrefix) {
        return new NameThreadFactory(namePrefix);
    }

    static class NameThreadFactory implements ThreadFactory {
        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        NameThreadFactory(String namePrefix) {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
            this.namePrefix = namePrefix + "-" + poolNumber.getAndIncrement() + "-thread-";
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
            if (t.isDaemon()) {
                t.setDaemon(false);
            }
            if (t.getPriority() != Thread.NORM_PRIORITY) {
                t.setPriority(Thread.NORM_PRIORITY);
            }
            return t;
        }
    }
}
