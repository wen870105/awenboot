package com.wen.awenboot.metrics;

import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.RatioGauge;
import com.codahale.metrics.Slf4jReporter;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @author wen
 * @version 1.0
 * @date 2021/6/23 17:02
 */
public class RatioGaugeExample {


    private static MetricRegistry registry = new MetricRegistry();

    private static final Slf4jReporter reporter = Slf4jReporter.forRegistry(registry)
            .outputTo(LoggerFactory.getLogger("metricsLogger"))
            .convertRatesTo(TimeUnit.SECONDS)
            .convertDurationsTo(TimeUnit.SECONDS)
            .build();

    private static Meter totalMeter = registry.meter("totalCount");
    private static Meter succMeter = registry.meter("succCount");

    public static void main(String[] args) {
//        ConsoleReporter reporter = ConsoleReporter.forRegistry(registry).build();
        reporter.start(10, TimeUnit.SECONDS);    //每5秒发送一次到控制台

        registry.gauge("succ-ratio", () -> new RatioGauge() {
            @Override
            protected Ratio getRatio() {
                return Ratio.of(succMeter.getCount(), totalMeter.getCount());    //第一个参数：分子 第二个参数：分母
            }
        });

        //调用
        for (; ; ) {
            processHandle();
        }
    }

    public static void processHandle() {
        //total count
        totalMeter.mark();
        try {
            int x = 10 / ThreadLocalRandom.current().nextInt(10);
            TimeUnit.MILLISECONDS.sleep(100);
            //succ count
            succMeter.mark();
        } catch (Exception e) {
//            System.out.println("================ err");
        }
    }
}
