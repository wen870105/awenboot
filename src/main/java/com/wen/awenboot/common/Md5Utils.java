package com.wen.awenboot.common;

import org.springframework.util.DigestUtils;
import sun.misc.BASE64Encoder;

/**
 * @author wen
 * @version 1.0
 * @date 2020/6/30 9:55
 */
public class Md5Utils {

    /**
     * 生成md5
     *
     * @param str
     * @return
     */
    public static String getMD5(String str) {
        String md5 = getMD5(str, null);
        return md5;
    }

    public static void main(String[] args) {
        System.out.println(getMD5("migu@2017"));
    }

    public static String getMD5(String str, String slat) {
        String base = str;
        if (slat != null) {
            base = base + "/" + slat;
        }
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(DigestUtils.md5Digest(base.getBytes()));
    }
}
