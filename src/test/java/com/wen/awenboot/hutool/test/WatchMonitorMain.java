package com.wen.awenboot.hutool.test;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.watch.WatchMonitor;
import cn.hutool.core.io.watch.Watcher;
import cn.hutool.core.io.watch.watchers.DelayWatcher;
import cn.hutool.core.lang.Console;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.WatchEvent;

/**
 * @author wen
 * @version 1.0
 * @date 2020/9/9 21:57
 */
@Slf4j
public class WatchMonitorMain {
    public static void main(String[] args) {
        testStr();
    }

    private static void testStr() {
        File file = FileUtil.file("D:\\checkout\\awenboot\\src\\test\\resources\\");

        Watcher watcher = new Watcher() {
            @Override
            public void onCreate(WatchEvent<?> event, Path currentPath) {
                Object obj = event.context();
                Console.log("创建：{}-> {}", currentPath, obj);
            }

            @Override
            public void onModify(WatchEvent<?> event, Path currentPath) {
                Object obj = event.context();
                Console.log("修改：{}-> {}", currentPath, obj);
            }

            @Override
            public void onDelete(WatchEvent<?> event, Path currentPath) {
                Object obj = event.context();
                Console.log("删除：{}-> {}", currentPath, obj);
            }

            @Override
            public void onOverflow(WatchEvent<?> event, Path currentPath) {
                Object obj = event.context();
                Console.log("Overflow：{}-> {}", currentPath, obj);
            }
        };
        WatchMonitor monitor = WatchMonitor.createAll(file, new DelayWatcher(watcher, 5000));
        monitor.start();
    }
}
