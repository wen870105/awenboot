package com.wen.awenboot.hutool.test;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wen
 * @version 1.0
 * @date 2020/9/9 19:47
 */
@Slf4j
public class IdMain {
    public static void main(String[] args) {
        test1();
    }


    private static void test1() {
        log.info("全局唯一ID:");
        String uuid = IdUtil.randomUUID();
        log.info("uuid={}", uuid);

        String simpleUUID = IdUtil.simpleUUID();
        log.info("不带-的uuid={}", simpleUUID);

        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        for (int i = 0; i < 5; i++) {
            log.info("雪花算法id={}", snowflake.nextId());
        }

    }
}
