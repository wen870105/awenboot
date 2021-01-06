package com.wen.awenboot.cache;

import cn.hutool.core.date.DateTime;
import cn.hutool.setting.dialect.Props;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * @author wen
 * @version 1.0
 * @date 2021/1/3 19:41
 */
@Slf4j
public class Test {
    public static void main(String[] args) throws Exception {

        for (int i = 0; i < 5; i++) {
            Thread thd = new Thread(() -> {
                try {
                    String key = "5g_" + DateTime.now().toString("yyyyMMdd");
                    test(key);
                } catch (Exception e) {
                    log.error("", e);
                }
            });
            thd.start();
        }

        TimeUnit.SECONDS.sleep(10);
    }

    public static void test(String key) throws Exception {
        File f = new File("D:\\wen_test\\total.properties");
        if (!f.exists()) {
            File parentFile = f.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            f.createNewFile();
        }
        Props props = new Props(f.getPath());
        int val = props.getInt(key, 0);
        for (int i = 0; i < 5; i++) {
            val += 5;
            props.setProperty(key, val + "");
            props.store(f.getPath());
            TimeUnit.SECONDS.sleep(1);
        }
        log.info("end");
    }
}
