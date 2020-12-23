package com.wen.awenboot.biz.service;

import com.wen.awenboot.config.ZhuangkuConfig;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author wen
 * @version 1.0
 * @date 2020/9/2 17:20
 */

@Slf4j
public class BrandFileService {

    private ZhuangkuConfig cfg;

    private String fileName;

    // 导出的文件
    @Getter
    private File file;

    public BrandFileService(ZhuangkuConfig cfg, String fileName) {
        this.cfg = cfg;
        this.fileName = fileName;
        init();
    }


    private void init() {
        String filePath = cfg.getExportDir() + fileName + ".txt";
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                file.createNewFile();
            } catch (Exception e) {
                log.error("创建文件失败,path={}", filePath, e);
            }
        }
        this.file = file;

        log.info("初始化文件服务:file={}", file.getPath());
    }


    public void wirte(String str) {
        try {
            FileUtils.write(file, str, "UTF-8", true);
        } catch (IOException e) {
            log.error("写入数据失败,文件名称:{}", file.getPath(), e);
        }
    }

    public static long lineCount(File file) {
        try {
            return Files.lines(Paths.get(file.getPath())).count();
        } catch (Exception e) {
            log.error("统计文件行数异常,file={}", file.getPath(), e);
            return 0;
        }
    }


}
