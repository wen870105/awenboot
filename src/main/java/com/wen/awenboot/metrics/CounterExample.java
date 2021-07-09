package com.wen.awenboot.metrics;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Counter;
import com.codahale.metrics.MetricRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

/**
 * @author wen
 * @version 1.0
 * @date 2021/6/23 17:07
 */
public class CounterExample {
    private static final Logger LOG = LoggerFactory.getLogger(CounterExample.class);

    //度量注册中心
    private static final MetricRegistry registry = new MetricRegistry();

    //度量计数器
    private static final Counter counter = registry.counter(MetricRegistry.name(CounterExample.class, ""));

    private static final ConsoleReporter report = ConsoleReporter.forRegistry(registry)
            .convertRatesTo(TimeUnit.MINUTES)
            .convertDurationsTo(TimeUnit.MINUTES)
            .build();
    private static Queue<String> queue = new LinkedList<String>();

    public static void main(String[] args) throws Exception {

        report.start(1, TimeUnit.SECONDS);        //每5秒将数据打印到控制台上

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    production("abc");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    consume();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        ;
        Thread.currentThread().join();
    }

    public static void production(String s) throws InterruptedException {
//        while (true) {
            for (int i = 0; i < 100; i++) {
                counter.inc();
                queue.offer(s);
            }
//            TimeUnit.MILLISECONDS.sleep(1000);
//        }
    }

    public static void consume() throws InterruptedException {
        while (queue.size() != 0) {
            TimeUnit.MILLISECONDS.sleep(100);
            queue.poll();    //删除第1个元素
            counter.dec();

        }
    }
}
