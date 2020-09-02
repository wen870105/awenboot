package com.wen.awenboot.controller;

import com.wen.awenboot.redis.RedisClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/redis")
@Slf4j
public class RedisController {
    @Autowired
    private RedisClient redisClient;

    org.springframework.data.redis.core.StringRedisTemplate StringRedisTemplate;

    private static int counter = 1;

    @RequestMapping("")
    @ResponseBody
    public Object index() {
        log.info("redis demo===============");
        redisClient.set("k1", "v1");

        redisClient.set("k2", "v2", 10);
        redisClient.hset("map", "k1", "v1");
        redisClient.hset("map", "k2", "v2");
        String hget = (String) redisClient.hget("map", "k2");
        log.info("hget=" + hget);
        String k1 = (String) redisClient.get("k1");
        return k1;
    }

}
