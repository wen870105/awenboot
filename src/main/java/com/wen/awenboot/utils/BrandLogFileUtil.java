package com.wen.awenboot.utils;

import cn.hutool.core.util.StrUtil;
import com.wen.awenboot.common.SpringUtil;
import com.wen.awenboot.config.ZhuangkuConfig;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wen
 * @version 1.0
 * @date 2020/12/22 20:42
 */
@Slf4j
public class BrandLogFileUtil {

    private static String getContentByKey(String s, String key) {
        int request = s.indexOf("\"" + key + "\"");
        if (request == -1) {
            return s;
        }
        String requestP1 = s.substring(request);
        int end = requestP1.indexOf("}");
        String substring = requestP1.substring(0, end);
        return substring;
    }

    private static String getContent2ByKey(String s, String key) {
        int request = s.indexOf("\"" + key + "\"");
        if (request == -1) {
            return s;
        }
        String requestP1 = s.substring(request);
        int end = StrUtil.ordinalIndexOf(requestP1, "\"", 3);
        String substring = requestP1.substring(0, end);

        return substring;
    }

    private static String getCodeByKey(String s, String key) {
        int request = s.indexOf("\"" + key + "\"");
        if (request == -1) {
            return s;
        }
        String requestP1 = s.substring(request);
        int end = StrUtil.ordinalIndexOf(requestP1, "\"", 4);
        String substring = requestP1.substring(0, end);

        return substring;
    }

    public static String trimParam(String str) {
        if (showLog()) {
            log.info("trimParam之前:{}", str);
        }
        String replace = StrUtil.replace(str, "\\\\", "");
        replace = StrUtil.replace(str, "\\", "");
        if (showLog()) {
            log.info("trimParam之后:{}", replace);
        }
        return replace;
    }

    private static boolean showLog() {
        try {
            ZhuangkuConfig cfg = SpringUtil.getBean(ZhuangkuConfig.class);
            return cfg.isShowLog();
        } catch (Throwable e) {
            return true;
        }


    }

    private static String trimRet(String str) {
        if (showLog()) {
            log.info("trimRet之前:{}", str);
        }
        String replace = StrUtil.replace(str, "\"", "").trim();

        if (showLog()) {
            log.info("trimRet之后:{}", replace);
        }
        return replace;
    }

    public static String getVal(String content) {
        String requestV = content.trim().split(":")[1];
        return trimRet(requestV);
    }

    public static String getVal(String str, String key) {
        String content = getContentByKey(str, key);
        return getVal(content);
    }

    public static String getVal2(String str, String key) {
        String content = getContent2ByKey(str, key);
        return getVal(content);
    }

    public static String getCodeVal(String str, String key) {
        String content = getCodeByKey(str, key);
        return getVal(content);
    }

    public static void main(String[] args) {
        brandTest();
    }


    private static void brandTest() {
        String p = "{\"timestamp\": 12/Nov \"remote_addr\": 10.191.1.11 \"secretId\" : \"10118\" \"request_body\":\"\\n{\\\"\\head\\\":{\\\"requestRefid\\\":\\\"estetsetsetsetset\\\"},\\\"request\\\":\\\"qqqq22222\\\"} , \\\"responseCode\\\":\\\"0000\\\" \"response_status\": 200 \\\"response_body\\\":\"{\\\"head\\\":{},\\\"response\\\":\\\"rrr2222\" }}";
//        String p = "{\"timestamp\": 12/Nov \"remote_addr\": 10.191.1.11 \"request_body\":\"\\n{\\\"\\head\\\":{\\\"requestRefid\\\":\\\"estetsetsetsetset\\\"},\\\"request\\\":\\\"seatsaetsett\\\"} \" \"response_status\": 200 \\\"response_body\\\":\"{\\\"head\\\":{},\\\"response\\\":\\\"testset\n" +
//                "\\\"}\"}";
        String s = trimParam(p);
        String val = getCodeVal(s, "responseCode");
        String secretId = getCodeVal(s, "secretId");
        System.out.println(secretId);
//        System.out.println("reuqest=" + request);
        System.out.println("reuqestVal=" + val);
    }
}
