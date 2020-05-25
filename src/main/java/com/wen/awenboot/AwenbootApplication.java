package com.wen.awenboot;

import com.alibaba.fastjson.JSON;
import com.wen.awenboot.domain.Greeting;
import com.wen.awenboot.cfg.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@SpringBootApplication
@RestController
public class AwenbootApplication {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @Autowired
    private Person person;

    public static void main(String[] args) {
        SpringApplication.run(AwenbootApplication.class, args);

    }

    @GetMapping("/hello")
    public Greeting hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        System.out.println(JSON.toJSONString(person));
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }
}
