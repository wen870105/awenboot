package com.wen.awenboot.biz.service;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.util.StrUtil;
import com.wen.awenboot.common.ReadFilePageUtil;
import com.wen.awenboot.common.SpringUtil;
import com.wen.awenboot.config.SpringConfig;
import com.wen.awenboot.config.ZhuangkuConfig;
import com.wen.awenboot.utils.ShellUtils;
import com.wen.awenboot.utils.TagFileUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author wen
 * @version 1.0
 * @date 2020/9/2 17:20
 */
@Slf4j
@Getter
public class ResolverFileService {

    private ZhuangkuConfig cfg;

    private File dataSourceFile;

    private File exportFile;

    public ResolverFileService(ZhuangkuConfig cfg, String dsFileName) {
        this.cfg = cfg;
        String fName = dsFileName + ".txt";
        File dsFile = new File(cfg.getExportDir() + fName);
        if (!dsFile.exists()) {
            log.info("dsFile为空={}", dsFile.getPath());
            return;
        }
        dataSourceFile = dsFile;
        exportFile = new File(cfg.getExportDir() + "ret_" + fName);
        if (!exportFile.exists()) {
            try {
                exportFile.createNewFile();
            } catch (IOException e) {
                log.error("", e);
            }
        } else {
            wirte("", false);
        }

        log.info("初始化导出文件服务:dataSourceFile={},exportFile={}", dataSourceFile.getPath(), exportFile.getPath());
    }

    public void asyncExport() {
        new Thread(() -> {
            log.info("[异步解析]文件file1={}", this.dataSourceFile);

            SpringUtil.getBean(SpringConfig.class).getExportFileList().offer(this.dataSourceFile.getName());

            try {
                TimeInterval timer = DateUtil.timer();
                if (getDataSourceFile() != null) {
                    export();
                }
                long interval = timer.interval();
                log.info("[异步解析]文件name={},耗时{}ms", dataSourceFile.getName(), interval);

                if (exportFile.exists()) {
                    cpRetFileToDir();
                    executeShellToHive();
                }
            } catch (Throwable e) {
                log.error("[异步解析后异常]", e);
            }finally {
                SpringUtil.getBean(SpringConfig.class).getExportFileList().poll();
            }
        }).start();
    }

    // 把文件拷到指定目录
    private boolean cpRetFileToDir() throws IOException {
        File source = exportFile;
        File dest = new File(cfg.getCpRetDir() + exportFile.getName());
        return TagFileUtils.cpFile(source, dest);
    }

    private String buildCmd() {
        String path = cfg.getShellFilePath();
        String p1 = cfg.getCpRetDir() + exportFile.getName();
        String p2 = DateTime.now().toString("yyyyMMdd");

        return StrUtil.format("sh {} {} {}", path, p1, p2);
    }

    private boolean executeShellToHive() {
        if (!cfg.isExecuteShellFlag()) {
            log.info("不执行shell命令");
            return false;
        }
        TimeInterval timer = DateUtil.timer();

        String cmd = buildCmd();
        log.info("[executeShell]file1={},cmd={}", this.exportFile.getPath(), cmd);

        ShellUtils.invokeShell(cmd);

        long interval = timer.interval();
        log.info("[executeShell]name={},耗时{}ms", exportFile.getName(), interval);
        return true;
    }

    public void export() {
        File file = dataSourceFile;
        long count = ZhuangkuFileService.lineCount(file);

        int limit = 10000;
        int start = 0;
        while (start < count) {
            log.info("[resolver]开始读取文件,name={},start={},limit={}", file.getPath(), start, limit);
            List<String> strings = null;
            try {
                strings = ReadFilePageUtil.readListPage(file.getPath(), start, limit);
            } catch (Exception e) {
                log.error("读取数据文件异常,path={}", file.getPath(), e);
            }

            if (strings != null && strings.size() > 0) {
                for (String str : strings) {
                    try {
                        resolve(str);
                    } catch (Throwable e) {
                        log.error("访问接口写入日志文件异常,str={}", str, e);
                    }
                }
            }
            start += limit;
        }
    }

    private void resolve(String str) {
        if (str.indexOf("=") == -1) {
            return;
        }
        int firstMark = str.indexOf("=");
        String phone = StrUtil.sub(str, 0, firstMark);
        String products = StrUtil.sub(str, firstMark + 1, str.length());

        if (StringUtils.isBlank(products)) {
            return;
        }

        try {

            MapService map = SpringUtil.getBean(MapService.class);
            if (map.exist(phone)) {
                return;
            }
            map.put(phone, "");
            if (products.indexOf(",") == -1) {
                writeProductStr(phone, products);
            } else {
                String[] split1 = products.split(",");
                for (String product : split1) {
                    writeProductStr(phone, product);
                }
            }
        } catch (Throwable e) {
            log.error("", e);
            log.info("str={},phone={},products={}", str, phone, products);
        }
    }

    private void writeProductStr(String phone, String products) {
        StringBuilder sb = new StringBuilder(50);
        String[] product = products.split(":");
        if (product.length == 1) {
            log.info("脏数据phone={},str={}", phone, products);
            return;
        }
        sb.append(phone).append("\t").append(product[0]).append("\t").append(product[1]).append("\t").append(DateUtil.offsetDay(DateUtil.date(), -2).toString("yyyyMMdd")).append("\n");
        wirte(sb.toString());
    }

    private void wirte(String str) {
        wirte(str, true);
    }

    private void wirte(String str, boolean append) {
        try {
            FileUtils.write(exportFile, str, "UTF-8", append);
        } catch (IOException e) {
            log.error("写入数据失败,文件名称:{}", exportFile.getPath(), e);
        }
    }
}
