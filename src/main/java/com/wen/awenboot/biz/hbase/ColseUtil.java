package com.wen.awenboot.biz.hbase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;

/**
 * @author wen
 * @version 1.0
 * @date 2020/9/27 18:59
 */
public class ColseUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ColseUtil.class);

    public static void close(Closeable obj) {
        if (obj != null) {
            try {
                obj.close();
            } catch (Throwable e) {
                LOGGER.error("", e);
            }
        }

    }
}
