package com.wen.awenboot.metrics;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @author wen
 * @version 1.0
 * @date 2021/6/23 16:19
 */
public class MetricsExample {
    //创建注册表
    private final static MetricRegistry registry = new MetricRegistry();

    //创建tps测量表
    private final static Meter requestMeter = registry.meter("tps");

    //创建异常测量表
    private final static Meter errorMeter = registry.meter("err_request");

    public static void main(String[] args) {

        //数据生成报告(按每分钟来统计)
        ConsoleReporter report = ConsoleReporter.forRegistry(registry)
                .convertRatesTo(TimeUnit.MINUTES)
                .convertDurationsTo(TimeUnit.MINUTES)
                .build();
        report.start(10, TimeUnit.SECONDS);    //每10秒将数据打印到控制台上

        for (; ; ) {                             //模拟一直调用请求
            getAsk();                        //发送请求
            randomSleep();                     //间隔的发送请求
        }
    }

    //处理请求方法
    public static void getAsk() {
        try {
            requestMeter.mark();
            randomSleep();
            int x = 10 / ThreadLocalRandom.current().nextInt(6);
        } catch (Exception e) {
            System.out.println("Error");
            errorMeter.mark();
        }
    }

    //模拟处理请求耗时
    public static void randomSleep() {
        try {
            TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(10));    //随机休眠时间
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
