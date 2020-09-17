package com.wen.awenboot.hutool.test;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wen
 * @version 1.0
 * @date 2020/9/9 19:47
 */
@Slf4j
public class DigesterMain {
    public static void main(String[] args) {
        test1();

    }


    private static void test1() {
        Digester md5 = new Digester(DigestAlgorithm.MD5);
        String testStr = "123456789";
// 5393554e94bf0eb6436f240a4fd71282
        String digestHex = md5.digestHex(testStr);
        log.info("md5({}),摘要编码后={}", testStr, digestHex);
    }
}
