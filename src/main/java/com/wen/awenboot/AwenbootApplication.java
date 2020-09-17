package com.wen.awenboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class AwenbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(AwenbootApplication.class, args);
    }

}