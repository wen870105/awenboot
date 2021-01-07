package com.wen.awenboot.es;


import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * @author wen
 * @version 1.0
 * @date 2021/1/6 15:15
 */
public class T {
    private static final byte[] SPLIT = {0x01};

    public static void main(String[] args) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("13980564387").append(new String(SPLIT)).append("ya").append("\r\n");
        sb.append("13980564386").append(new String(SPLIT)).append("yagao4").append("\r\n");
        sb.append("13980564385").append(new String(SPLIT)).append("yagao1").append("\r\n");
        FileUtils.write(new File("D:\\test1234\\name_0_test.DATA"), sb.toString());
    }
}
