package com.wen.awenboot.test;

/**
 * @author wen
 * @version 1.0
 * @date 2020/10/12 11:13
 */
public class MainTest {
    public static String hashMsisdn(String msisdn) {
        try {
            int sum = 0;
            for (int i = 0; i < msisdn.length(); i++) {
                sum = sum + Integer.valueOf(msisdn.charAt(i));
            }
            int hashKey = sum % 52;
            StringBuilder sb = new StringBuilder(msisdn);
            String telHash = null;
            if (hashKey < 26) {
                telHash = String.valueOf(Character.valueOf((char) (hashKey + 65)));
            } else {
                telHash = String.valueOf(Character.valueOf((char) (hashKey + 71)));
            }
            return sb.reverse().insert(0, telHash).toString();
        } catch (Exception e) {
            return null;
        }
    }

    public static void main(String[] args) {
        String s = "13688420801";
        System.out.println(hashMsisdn(s));
    }
}
