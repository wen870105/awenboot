package com.wen.awenboot.utils;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wen
 * @version 1.0
 * @date 2020/12/22 20:42
 */
@Slf4j
public class GotoneLogFileUtil extends BrandLogFileUtil{

    public static void main(String[] args) {
        gotoneTest();
    }

    public static boolean isRequestType(String str) {
        return str.contains("request body:");
    }

    public static boolean isResponseType(String str) {
        return str.contains("response:");
    }

    public static boolean isGotoneLevel(String str) {
        return str.contains("/gotoneLevel");
    }

    public static String getCtxByKey(String s, String key) {
        int request = s.indexOf("\"" + key + "\"");
        if (request == -1) {
            return s;
        }
        String requestP1 = s.substring(request);
        int end = StrUtil.ordinalIndexOf(requestP1, "\"", 4);
        String substring = requestP1.substring(0, end);

        return substring;
    }

    public static String getGotoneVal(String str, String key) {
        String content = getCtxByKey(str, key);
        return getVal(content);
    }
    private static void gotoneTest() {
        String p = "2020-12-14 14:14:31,347 [fdsaetst- 10002 - /dtpServer/services/gotoneLevel] " +
                "INFO request body: {\"timestamp\": 12/Nov \"remote_addr\": 10.191.1.11 \"request\":\"B4Sb4b8F/E9qwVhIx4D7uS9jlsWRAyzQLaBwZkzQviltdjw32Lgfsju3CMPKGYpJV0MAW/Lnx3Mp\\nmLxpdr98bw==\" , \\\"responseCode\\\":\\\"0000\\\" \"response_status\": 200 \\\"response_body\\\":\"{\\\"head\\\":{},\\\"response\\\":\\\"rrr2222\" }}";
        String s = trimParam(p);
        String val = getCodeVal(s, "request");
        System.out.println("requestTYpe=" + isRequestType(s));
        System.out.println("gotone=" + isGotoneLevel(s));
//        System.out.println("reuqest=" + request);
        System.out.println("reuqestVal=" + val);
    }
}
