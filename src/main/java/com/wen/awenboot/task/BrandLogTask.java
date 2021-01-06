package com.wen.awenboot.task;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.wen.awenboot.biz.service.BrandFileService;
import com.wen.awenboot.biz.service.ZhuangkuFileService;
import com.wen.awenboot.common.ReadFilePageUtil;
import com.wen.awenboot.config.ZhuangkuConfig;
import com.wen.awenboot.integration.log.BrandRequest;
import com.wen.awenboot.integration.log.BrandResponse;
import com.wen.awenboot.integration.log.PhoneTagKv;
import com.wen.awenboot.utils.BrandLogFileUtil;
import com.wen.awenboot.utils.SM4Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

/**
 * brand日志接口采集
 *
 * @author wen
 * @version 1.0
 * @date 2020/9/1 19:57
 */
@Service
@Slf4j
public class BrandLogTask {
    @Autowired
    private ZhuangkuConfig cfg;

    // 当前时间的分钟数,用来调整流控速率
    private int currentMinute;

    private static String secretKey = "MFZxY29UNjkxQ204OVgycXh1dDE3Nll1";

    @Scheduled(cron = "0 0 7 * * ? ")
    private void init() {
        if ("brand".equalsIgnoreCase(cfg.getTaskName())) {
            log.info("启动,{}", cfg.getTaskName());
        } else {
            return;
        }
        File file = new File(cfg.getDataSourceDir() + cfg.getDtpFilePrefix() + DateTime.now().toString("yyyyMMdd"));
        if (!file.exists()) {
            log.error("================{}文件不存在", file.getPath());
            log.error("================{}文件不存在", file.getPath());
            log.error("================{}文件不存在", file.getPath());
            return;
        }
        execute1(file);
    }

    public void execute1(File file) {
        long start = System.currentTimeMillis();
        exportFile(file);
        long end = System.currentTimeMillis();
        log.info("导出完毕,耗时{}ms", end - start);
    }


    private void exportFile(File file) {

        BrandFileService zkfs = new BrandFileService(cfg, file.getName());
        if (zkfs.getFile().exists()) {
            log.info("{}导出文件已经存在跳过", zkfs.getFile().getPath());
            return;
        }
        long count = ZhuangkuFileService.lineCount(file);

        int limit = 100;
        int start = 0;
        while (start < count) {
            List<String> strings = null;
            try {
                log.info("读取文件,start={},limit={},name={}", start, limit, file.getPath());
                strings = ReadFilePageUtil.readListPage(file.getPath(), start, limit);
            } catch (Exception e) {
                log.error("读取数据文件异常,path={}", file.getPath(), e);
            }

            if (strings != null && strings.size() > 0) {
                for (String logStr : strings) {
                    try {
                        PhoneTagKv resp = getResult(logStr);
                        if (resp == null) {
                            continue;
                        }
                        wirteData(resp, zkfs);
                    } catch (Throwable e) {
                        log.error("logStr={}", logStr, e);
                    }
                }
            }
            start += limit;
        }
        log.info("导出文件{}", zkfs.getFile().getPath());

//        if (isUpdate) {
//            new ResolverFileService(cfg, file.getName()).asyncExport();
//        }
//        zkfs.endExport();
    }


    private void wirteData(PhoneTagKv resp, BrandFileService zkfs) {
        StringBuilder sb = new StringBuilder(50);
        sb.append(resp.getPhone()).append("\t").append(resp.getTag()).append("\t").append(resp.getTagText()).append("\r\n");
        zkfs.wirte(sb.toString());
    }

    private String getBrand(String key) {
        switch (key) {
            case "1":
                return "全球通";
            case "2":
                return "神州行";
            case "3":
                return "动感地带";
            case "-1":
                return "非移动用户";
            default:
                return "未知";
        }
    }

    private PhoneTagKv getResult(String logStr) {
        String trimLog = BrandLogFileUtil.trimParam(logStr);
        String request = BrandLogFileUtil.getVal(trimLog, "request");

//        1：全球通
//        2：神州行
//        3：动感地带
//        -1：非移动用户
//        -2：未知
        String response = BrandLogFileUtil.getVal(trimLog, "response");
        String responseCode = BrandLogFileUtil.getCodeVal(trimLog, "responseCode");
        if (StrUtil.isBlank(request)
                || StrUtil.isBlank(response)
                || StrUtil.isBlank(responseCode)
                || !"0000".equalsIgnoreCase(responseCode)) {
            return null;
        }

        try {
            String requestDec = SM4Util.decode(request, secretKey);
            String responseDec = SM4Util.decode(response, secretKey);
            log.info("解密过后request={},resp={}", requestDec, responseDec);
            BrandRequest brandRequest = JSON.parseObject(requestDec, BrandRequest.class);
            BrandResponse brandResponse = JSON.parseObject(responseDec, BrandResponse.class);
            PhoneTagKv kv = new PhoneTagKv();
            kv.setPhone(brandRequest.getParam().getMobile());
            kv.setTag(brandResponse.getBrandIdentity());
            kv.setTagText(getBrand(kv.getTag()));
            return kv;
        } catch (Exception e) {
            log.error("", e);
            return null;
        }
    }
}

