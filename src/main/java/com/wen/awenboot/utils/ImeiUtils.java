package com.wen.awenboot.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * @author wen
 * @version 1.0
 * @date 2021/3/3 11:29
 */
public class ImeiUtils {

    /**
     * 参考的老的代码
     * 通过imei的前14位获取完整的imei(15位)
     *
     * @param imeiString
     * @return
     */
    public static String getImeiBy14(String imeiString) {
        if (!StringUtils.isNumeric(imeiString) || imeiString.length() < 14) {
            return null;
        }
        String tempImei = imeiString;
        if (imeiString.length() > 14) {
            tempImei = tempImei.substring(0, 14);
        }
        String retVal = null;
        char[] chars = tempImei.toCharArray();
        int resultInt = 0;
        for (int i = 0; i < chars.length; i++) {
            int a = Integer.parseInt(String.valueOf(chars[i]));
            i++;
            int temp = Integer.parseInt(String.valueOf(chars[i])) * 2;
            int b = temp < 10 ? temp : temp - 9;
            resultInt += a + b;
        }
        resultInt %= 10;
        resultInt = resultInt == 0 ? 0 : 10 - resultInt;
        retVal = tempImei + resultInt;

        return retVal;
    }

    public static void main(String[] args) {
        String imei ="860192000079419";
        System.out.println(getImeiBy14("31847454511199"));
        System.out.println(getImeiBy14("86019200007"));
        System.out.println(getImeiBy14("86019200007941"));
        System.out.println(getImeiBy14("864230039369399999"));
    }
}
