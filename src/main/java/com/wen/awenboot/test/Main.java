package com.wen.awenboot.test;

import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;

import java.io.File;
import java.io.IOException;

/**
 * @author wen
 * @version 1.0
 * @date 2020/8/3 9:12
 */
public class Main {

    private static String str = "18513951051," +
            "18723086374," +
            "15087030430," +
            "13342510932," +
            "15573217385," +
            "13983622691," +
            "15974216075," +
            "18716883083," +
            "15105148270," +
            "13770501240," +
            "15850586971";

    public static void main(String[] args) throws IOException {
        byte[] bytes = {0x01};
        StringBuilder sb = new StringBuilder();

        String[] split = str.split(",");
        for (String s : split) {
            sb.append(s.trim()).append(new String(bytes)).append("1").append("\r\n");
        }
//        long ret = 18300000000L;
//        for (int i = 0; i < 20; i++) {
//            sb.append(++ret).append(new String(bytes)).append("1").append("\r\n");
//        }
        String day = DateTime.now().toString("yyyyMMdd");

        String file1 = "PRODUCT_0_DAY_" + day + "_01.DATA";
        FileUtils.write(new File("D:\\" + file1), sb.toString(), "UTF-8");

//        StringBuilder sb2 = new StringBuilder();
//        for (int i = 0; i < 15; i++) {
//            sb2.append(++ret).append(new String(bytes)).append("1").append("\r\n");
//        }
//        String file2 = "PRODUCT_0_DAY_" + day + "_02.DATA";
//        FileUtils.write(new File("D:\\" + file2), sb2.toString(), "UTF-8");

        StringBuilder sb3 = new StringBuilder();
        sb3.append(file1).append(new String(bytes)).append(split.length).append(new String(bytes)).append(day).append("\r\n");
//        sb3.append(file2).append(new String(bytes)).append("15").append(new String(bytes)).append(day).append("\r\n");

        FileUtils.write(new File("D:\\ALL_0_DAY_" + day + ".CHK"), sb3.toString(), "UTF-8");
    }

}
