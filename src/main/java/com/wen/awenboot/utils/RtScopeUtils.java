package com.wen.awenboot.utils;

/**
 * @author wen
 * @version 1.0
 * @date 2021/1/6 13:46
 */
public class RtScopeUtils {

    public static String getRtScope(long time) {
        if (time >= 0 && time <= 100) {
            return "0-100ms";
        }
        if (time > 100 && time <= 200) {
            return "100-200ms";
        }
        if (time > 200 && time <= 500) {
            return "200-500ms";
        }
        if (time > 500 && time <= 1000) {
            return "500-1000ms";
        }
        if (time > 1000 && time <= 5000) {
            return "1-5s";
        }
        if (time > 5000 && time <= 10000) {
            return "5-10s";
        }
        if (time > 10000 && time <= 15000) {
            return "10-15s";
        }
        if (time > 15000 && time <= 20000) {
            return "15-20s";
        }
        if (time > 20000 && time <= 50000) {
            return "20-50s";
        }
        return "50+s";
    }
}
