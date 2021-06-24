package com.wen.awenboot.metrics;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;

import java.util.concurrent.TimeUnit;

/**
 * 作用：统计请求的速率和处理时间
 * <p>
 * 　　例如：某接口的总在一定时间内的请求总数，平均处理时间
 *
 * @author wen
 * @version 1.0
 * @date 2021/6/23 17:08
 */
public class TimerExample {

    //创建度量中心
    private static final MetricRegistry registry = new MetricRegistry();

    //输出到控制台
    private static final ConsoleReporter report = ConsoleReporter.forRegistry(registry).build();

    //实例化timer
    private static final Timer timer = registry.timer("request");

    public static void main(String[] args) {
        report.start(5, TimeUnit.SECONDS);
        while (true) {
            handleRequest();
        }
    }

    private static void handleRequest() {
        Timer.Context time = timer.time();
        try {
            Thread.sleep(500);    //模拟处理请求时间
        } catch (Exception e) {
            System.out.println("err");
        } finally {
            time.stop();//每次执行完都会关闭
            System.out.println("==== timer 已关闭");
        }
    }
}
