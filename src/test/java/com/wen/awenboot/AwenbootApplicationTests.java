package com.wen.awenboot;

import com.wen.awenboot.utils.RedisCli;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class AwenbootApplicationTests {

    @Test
    void contextLoads() {
        RedisCli.getInstance().set("wen", "123");

        long t = RedisCli.getInstance().incr("limit_second_phone", 1);
        System.out.println("12111111111111");
        log.info("limit={}", t);
    }

}
