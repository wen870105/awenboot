package com.wen.awenboot.utils;

import cn.hutool.core.util.RandomUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author wen
 * @version 1.0
 * @date 2021/3/1 10:58
 */
public class TestMain {
    public static void main(String[] args) {
        System.out.println(RandomUtil.randomString(25));
                try {
           Files.move(Paths.get("D:\\test123\\111.txt"), Paths.get("D:\\test123\\bak\\"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("1111");
    }
}
