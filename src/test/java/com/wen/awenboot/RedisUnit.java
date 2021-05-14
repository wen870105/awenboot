package com.wen.awenboot;

import com.google.common.util.concurrent.RateLimiter;
import com.wen.awenboot.utils.DateUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wen
 * @version 1.0
 * @date 2021/2/6 21:48
 */
public class RedisUnit {

    private static RateLimiter rateLimiter = RateLimiter.create(20);
    private static Map<String, Map<Boolean, Integer>> map = new HashMap<>();

    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000000; i++) {
            get(10);
        }
        System.out.println(map.toString());
        System.out.println(System.currentTimeMillis() - start + "ms");
    }

    private static void get(int permits) {
        boolean b = rateLimiter.tryAcquire(permits);
        String key = DateUtils.asString(new Date(), "ss");
        Map<Boolean, Integer> tmap = map.get(key);
        if (tmap == null) {
            tmap = new HashMap<>();
            map.put(key, tmap);
        }
        Integer integer = tmap.get(b);
        if (integer == null) {
            integer = 0;
        }
        integer++;
        tmap.put(b, integer);
    }
}
