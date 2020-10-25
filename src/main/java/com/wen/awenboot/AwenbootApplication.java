package com.wen.awenboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@ComponentScan(basePackages = {"com.wen.awenboot", "com.migu.tsg.damportal"})
public class AwenbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(AwenbootApplication.class, args);
    }

}
