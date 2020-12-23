package com.wen.awenboot.utils;

import cn.hutool.core.util.StrUtil;
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
        log.info("trimParam之前:{}", str);
        String replace = StrUtil.replace(str, "\\\\", "");
        replace = StrUtil.replace(str, "\\", "");
        log.info("trimParam之后:{}", replace);
        return replace;
    }

    private static String trimRet(String str) {
//        log.info("trimRet之前:{}", str);
        String replace = StrUtil.replace(str, "\"", "");
//        log.info("trimRet之后:{}", replace);
        return replace;
    }

    private static String getVal(String content) {
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
        String p = "{\"timestamp\": 12/Nov \"remote_addr\": 10.191.1.11 \"request_body\":\"\\n{\\\"\\head\\\":{\\\"requestRefid\\\":\\\"estetsetsetsetset\\\"},\\\"request\\\":\\\"qqqq22222\\\"} , \\\"responseCode\\\":\\\"0000\\\" \"response_status\": 200 \\\"response_body\\\":\"{\\\"head\\\":{},\\\"response\\\":\\\"rrr2222\" }}";
//        String p = "{\"timestamp\": 12/Nov \"remote_addr\": 10.191.1.11 \"request_body\":\"\\n{\\\"\\head\\\":{\\\"requestRefid\\\":\\\"estetsetsetsetset\\\"},\\\"request\\\":\\\"seatsaetsett\\\"} \" \"response_status\": 200 \\\"response_body\\\":\"{\\\"head\\\":{},\\\"response\\\":\\\"testset\n" +
//                "\\\"}\"}";
        String s = trimParam(p);
        String val = getCodeVal(s, "responseCode");
//        System.out.println("reuqest=" + request);
        System.out.println("reuqestVal=" + val);

//        String response = getContentByKey(s, "response");
//        String responseV = response.trim().split(":")[1];
//        System.out.println(response);
//        System.out.println(responseV);
//
//        String response_status = getContent2ByKey(s, "response_status");
//        String response_statusV = response.trim().split(":")[1];
//
//        System.out.println(response_status);


//
//        String inputLine = "alter user mydip identified by \"HqLabcdyX\" account unlock\"test\";";
//        Pattern r = Pattern.compile(".*identified\\s+by\\s+(\\S+?)($|;|\\s+.+)", Pattern.CASE_INSENSITIVE);
//        Matcher m = r.matcher(inputLine);
//        if (!m.matches()) {
//            throw new IllegalArgumentException("Bad Input");
//        }
//
//        System.out.println(m.groupCount());
//        // 分组0得到的是整个原字符串
//        for (int i = 0; i < m.groupCount() + 1; i++) {
//            System.out.println("分组" + i + ":" + m.group(i));
//        }
    }
}
