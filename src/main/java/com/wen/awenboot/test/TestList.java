package com.wen.awenboot.test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author wen
 * @version 1.0
 * @date 2020/6/18 17:04
 */
public class TestList {
    public static void main(String[] args) {
//        test22();
        test2();
    }

    private static void test22() {
        List<String> platformList = new ArrayList<>();
        platformList.add("博客园");
        platformList.add("CSDN");
        platformList.add("掘金");

        for (String platform : platformList) {
            if (platform.equals("博客园")) {
                platformList.remove(platform);
            }
        }

        System.out.println(platformList);
    }

    public static void test2() {
        List<String> platformList = new ArrayList<>();
        platformList.add("博客园");
        platformList.add("CSDN");
        platformList.add("掘金");

        Iterator<String> iterator = platformList.iterator();
        while (iterator.hasNext()) {
            String platform = iterator.next();
            if (platform.equals("博客园")) {
                iterator.remove();
            }
        }

        System.out.println(platformList);
    }
}
