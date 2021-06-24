package com.wen.awenboot.test.metric;

import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Slf4jReporter;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class Slf4jReporterTest {
    private static MetricRegistry registry = new MetricRegistry();
    Slf4jReporter reporter = Slf4jReporter.forRegistry(registry)
            .outputTo(LoggerFactory.getLogger("demo.metrics"))
            .convertRatesTo(TimeUnit.SECONDS)
            .convertDurationsTo(TimeUnit.MILLISECONDS)
            .build();


    public static void main(String[] args) {
        Meter requestMeter = registry.meter("request");
        requestMeter.mark();
    }

}
