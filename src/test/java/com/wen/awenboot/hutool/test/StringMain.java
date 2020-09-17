package com.wen.awenboot.hutool.test;

import cn.hutool.core.text.StrFormatter;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wen
 * @version 1.0
 * @date 2020/9/9 19:47
 */
@Slf4j
public class StringMain {
    public static void main(String[] args) {
//        test1();

        test2();
    }

    private static void test2() {
//        StrUtil.isBlank();
        log.info("字符串截取.");
        String str = "123456789";
        String strSub1 = StrUtil.sub(str, 3, 11);
//        log.info("strSub2=" + str.substring(3, 10));
        log.info("strSub1=" + strSub1);
    }

    private static void test1() {
        String result1 = StrFormatter.format("this is {} for {}", "a", "b");
        log.info("字符串拼接:" + result1);

        log.info("字符串拼接 {}", "test1");
    }
}
