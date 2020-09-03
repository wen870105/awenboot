package com.wen.awenboot.biz.service;

import com.wen.awenboot.config.ZhuangkuConfig;
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

    // 记录当前撞库的状态
    private String tempFileName = "zhuangku.txt";

    private String tempFilePpsKey = "current";

    private String tempFilePpsEndKey = "isEnd";
    // 导出的文件
    private File file;
    // 临时文件用来记录当前操作的第几行
    private File tempFile;

    private Properties tempFilePps;


    public ZhuangkuFileService(ZhuangkuConfig cfg, String fileName) {
        this.cfg = cfg;
        this.fileName = fileName;
        init();
    }

    private void init() {
        String filePath = cfg.getExportDir() + fileName + ".txt";
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
            } catch (Exception e) {
                log.error("创建文件失败,path={}", filePath, e);
            }
        } else {
            try {
                FileUtils.write(file, "", "UTF-8", false);
                log.error("清空数据文件名称:{}", file.getPath());
            } catch (IOException e) {
                log.error("清空数据失败,文件名称:{}", file.getPath(), e);
            }
        }
        String tempFilePath = cfg.getExportDir() + tempFileName;
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
        String property = tempFilePps.getProperty(tempFilePpsKey);
        if (StringUtils.isNotBlank(property)) {
            // 当前操作的数据和历史操作数据目录不同,需要冲头开始撞库
            if (!cfg.getDataSourceDir().equalsIgnoreCase(property.split("\\$")[0])) {
                tempFilePps.clear();
                updateProperties();
                log.info("当前操作的数据和历史操作数据目录不同,需要冲头开始撞库,清空临时配置文件");
            }
        }
        this.file = file;
        this.tempFile = newTempFile;
        log.info("初始化文件服务:file={},tempFile={}", file.getPath(), tempFile.getPath());
    }

    public void wirteTempFileProperties(String fileName, int startRow) {
        // 记录操作日志
        // value : 表示 1数据源目录,2当前执行文件,3当前文件执行第几行
        tempFilePps.setProperty(tempFilePpsKey, cfg.getDataSourceDir() + "$" + fileName + "$" + startRow);
        updateProperties();
    }

    private void updateProperties() {
        try {
            FileOutputStream fos = new FileOutputStream(tempFile);
            tempFilePps.store(fos, "zhuang ku");
        } catch (IOException e) {
            log.error("", e);
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
