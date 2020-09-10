package com.wen.awenboot.test;

import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;

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
            "15850586971," +
            "13901379770," +
            "13813920842," +
            "18618345781," +
            "18001588091," +
            "15826005810," +
            "13401885574," +
            "15205171128," +
            "18651866202," +
            "18651866203," +
            "18513951051," +
            "15709181103," +
            "18661207706," +
            "13991216535," +
            "15101638759," +
            "18012330222," +
            "15195855829," +
            "18337148608," +
            "15951024276," +
            "13851743615," +
            "13584066654," +
            "15972932739," +
            "15651087138," +
            "17368466128," +
            "17625287577," +
            "13573777267," +
            "15066665723," +
            "17854125494," +
            "18364193691," +
            "18390992418," +
            "19853133300," +
            "15700721221," +
            "13814006982," +
            "18652558711," +
            "18851798004," +
            "17621931606," +
            "13810789603," +
            "18618377273," +
            "15851803542," +
            "18551835818," +
            "18390900951," +
            "13574107985," +
            "13786121951," +
            "13787268699," +
            "18473800603," +
            "15301580708," +
            "17626048268," +
            "13771040131," +
            "18151689992";


    public static void main(String[] args) throws IOException {
        byte[] bytes = {0x01};
        StringBuilder sb = new StringBuilder();

        String[] split = str.split(",");
        HashSet<String> set1 = new HashSet<>();

        for (String s : split) {
            set1.add(s);
        }
        for (String s : set1) {
            sb.append(s.trim()).append(new String(bytes)).append("1").append("\r\n");
        }


        String day = DateTime.now().toString("yyyyMMdd");

        String file1 = "TESTPHONE_0_DAY_" + day + "_01.DATA";
        FileUtils.write(new File("D:\\" + file1), sb.toString(), "UTF-8");


        StringBuilder sb3 = new StringBuilder();
        sb3.append(file1).append(new String(bytes)).append(set1.size()).append(new String(bytes)).append(day).append("\r\n");

        FileUtils.write(new File("D:\\TESTPHONE_0_DAY_" + day + ".CHK"), sb3.toString(), "UTF-8");
    }

}
