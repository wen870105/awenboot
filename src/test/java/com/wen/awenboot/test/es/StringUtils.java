package com.wen.awenboot.test.es;

import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

import java.util.UUID;

/**
 * @author wen.
 * @version v 0.1 2018/5/3 20:51 wen Exp $
 */
public class StringUtils {

    /**
     * 扩展字符串拼接
     * @param format {}占位
     * @param argArray
     * @return
     */
    public static String formatString(String format, Object... argArray){
        FormattingTuple ft = MessageFormatter.arrayFormat(format, argArray);
        return ft.getMessage();
    }

    public static String generatorUUID() {
        String id = UUID.randomUUID().toString().toLowerCase();
        id = id.substring(0, 8) + id.substring(9, 13) + id.substring(14, 18) + id.substring(19, 23) + id.substring(24);
        return id;
    }
}
