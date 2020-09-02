package com.wen.awenboot.test;

import com.google.common.util.concurrent.RateLimiter;
import com.wen.awenboot.common.OkHttpUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;

import java.io.IOException;

/**
 * @author wen
 * @version 1.0
 * @date 2020/9/2 13:49
 */
@Slf4j
public class RateLimiterMain {
    private static RateLimiter limiter = RateLimiter.create(3);
    private static OkHttpUtil client = OkHttpUtil.getInstance();
    private static int i = 0;

    public static void main(String[] args) {
        while (true) {
            limit();
        }
    }

    private static void limit() {
        limiter.acquire();
        Response data = client.getData("http://www.baidu.com");
        try {
            log.info("squ={},data={}", ++i, data.body().string());
        } catch (IOException e) {
            log.info("", e);
        }
    }
}
