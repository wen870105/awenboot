package com.wen.awenboot.test;

import java.io.File;
import java.io.IOException;

/**
 * @author wen
 * @version 1.0
 * @date 2020/8/3 9:12
 */
public class Main2 {

    private static String str = "18780589229,18723086374,15123849675,        13594624754,        18375883463,        18716883083,        15297166648";

    public static void main(String[] args) throws IOException {
        File file = new File("D:\\wen_test\\");
        File[] files = file.listFiles();
        for (File f : files) {
            System.out.println(f.getName());
        }
    }

}
