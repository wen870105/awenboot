package com.wen.awenboot.utils;

import cn.hutool.core.date.DateTime;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * @author wen
 * @version 1.0
 * @date 2020/12/8 10:14
 */
@Slf4j
public class TagFileUtils {
    // 把文件拷到指定目录
    public static boolean cpFile(File source, File dest) throws IOException {
        if (dest.exists()) {
            log.info("文件已经存在,{}", dest.getPath());
        } else {
            Files.copy(source.toPath(), dest.toPath());
        }
        return true;
    }

    public static void main(String[] args) {
        String f1 = "D:\\test";
        String f2 = "D:\\test4";
        try {
            cpDir(new File(f1), new File(f2));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean cpBakRetDir(String path) throws IOException {
        return cpDir(new File(path), new File(path + "/" + DateTime.now().toString("yyyyMMdd")));
    }

    public static boolean cpDir(File dir, File destDir) throws IOException {
        boolean flag = false;
        if (dir.exists()) {
            if (!destDir.exists()) {
                File[] files = dir.listFiles((dir1, name) -> name.endsWith(".txt"));
                if (files != null) {
                    destDir.mkdirs();
                    String nP = null;
                    for (File f : files) {
                        nP = destDir.getPath() + "/" + f.getName();
                        cpFile(f, new File(nP));
                    }
                    flag = true;
                }
            } else {
                log.info("dest文件目录已存在,不拷贝数据", destDir.getPath());
            }
        } else {
            log.info("dir文件目录存在,{}", dir.getPath());
        }
        log.info("拷贝目录完成flag={},src={},dest={}", flag, dir.getPath(), destDir.getPath());
        return true;
    }
}
