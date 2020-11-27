package com.wen.awenboot.biz.service;

import com.wen.awenboot.config.ZhuangkuConfig;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * @author wen
 * @version 1.0
 * @date 2020/9/2 17:20
 */

@Slf4j
public class ZhuangkuFileService {

    private ZhuangkuConfig cfg;

    private String fileName;

    private String prefix;

    // 记录当前撞库的状态
    private String tempFileName = "zhuangku";

    private String tempFilePpsKey = "current";

    private String tempFilePpsEndKey = "isEnd";
    // 导出的文件
    @Getter
    private File file;
    // 临时文件用来记录当前操作的第几行
    private File tempFile;

    private Properties tempFilePps;

    public ZhuangkuFileService(ZhuangkuConfig cfg, String fileName, String prefix) {
        this.cfg = cfg;
        this.fileName = fileName;
        this.prefix = prefix;
        init();
    }

    public ZhuangkuFileService(ZhuangkuConfig cfg, String fileName) {
        this(cfg, fileName, null);
    }

    private void init() {
        String tmp = StringUtils.isBlank(prefix) ? "" : prefix;
        String filePath = cfg.getExportDir() + fileName + tmp + ".txt";
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

        String tempFilePath = cfg.getExportDir() + tempFileName + "_" + file.getName();
        File newTempFile = new File(tempFilePath);
        if (!newTempFile.exists()) {
            try {
                newTempFile.createNewFile();
            } catch (IOException e) {
                log.error("创建文件失败,path={}", tempFilePath, e);
            }
        }
        tempFile = newTempFile;

        tempFilePps = new Properties();
        try {
            tempFilePps.load(new FileInputStream(tempFile));
        } catch (IOException e) {
            log.error("", e);
        }

        log.info("初始化文件服务:file={},tempFile={}", file.getPath(), tempFile.getPath());
    }

    public void wirteTempFileProperties(String fileName, int startRow) {
        // 记录操作日志
        // value : 表示 1数据源目录,2当前执行文件,3当前文件执行第几行
        tempFilePps.setProperty(tempFilePpsKey, cfg.getDataSourceDir() + "$" + fileName + "$" + startRow);
        updateProperties();
    }

    private void updateProperties() {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(tempFile);
            tempFilePps.store(fos, "zhuang ku");
        } catch (IOException e) {
            log.error("", e);
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getTempFileProperties() {
        return tempFilePps.getProperty(tempFilePpsKey);
    }

    public void endExport() {
        tempFilePps.setProperty(tempFilePpsEndKey, "true");
        updateProperties();
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
