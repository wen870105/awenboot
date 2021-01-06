package com.wen.awenboot.task;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.wen.awenboot.biz.service.BrandFileService;
import com.wen.awenboot.biz.service.ZhuangkuFileService;
import com.wen.awenboot.common.ReadFilePageUtil;
import com.wen.awenboot.config.ZhuangkuConfig;
import com.wen.awenboot.integration.log.GotoneLogRequest;
import com.wen.awenboot.integration.log.PhoneTagKv;
import com.wen.awenboot.utils.GotoneLogFileUtil;
import com.wen.awenboot.utils.ThreeDES;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 全球通日志接口采集
 *
 * @author wen
 * @version 1.0
 * @date 2020/9/1 19:57
 */
@Service
@Slf4j
public class GotoneTask {
    @Autowired
    private ZhuangkuConfig cfg;

    // 当前时间的分钟数,用来调整流控速率
    private int currentMinute;


    @Scheduled(cron = "0 0 7 * * ? ")
    private void init() {
        if ("gotone".equalsIgnoreCase(cfg.getTaskName())) {
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
        Map<String, GotoneLogRequest> ctx = new HashMap<>();
        long count = ZhuangkuFileService.lineCount(file);

        int limit = 100;
        int start = 0;
        while (start < count) {
            List<String> strings = null;
            try {
                log.info("读取文件,start={},limit={},count={},name={}", start, limit, count, file.getPath());
                strings = ReadFilePageUtil.readListPage(file.getPath(), start, limit);
            } catch (Exception e) {
                log.error("读取数据文件异常,path={}", file.getPath(), e);
            }

            if (strings != null && strings.size() > 0) {
                for (String logStr : strings) {
                    try {
                        PhoneTagKv resp = getResult(logStr, ctx);
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
    }


    private void wirteData(PhoneTagKv resp, BrandFileService zkfs) {
        StringBuilder sb = new StringBuilder(50);
        sb.append(resp.getPhone()).append("\t").append(resp.getTag()).append("\t").append(resp.getTagText()).append("\r\n");
        zkfs.wirte(sb.toString());
    }


    private PhoneTagKv getResult(String logStr, Map<String, GotoneLogRequest> ctx) {
        String request = null;
        String response = null;
        try {
            String trimLog = GotoneLogFileUtil.trimParam(logStr);
            boolean gotoneLevel = GotoneLogFileUtil.isGotoneLevel(trimLog);
            if (!gotoneLevel) {
                return null;
            }

            boolean requestType = GotoneLogFileUtil.isRequestType(trimLog);
            if (requestType) {
                request = GotoneLogFileUtil.getGotoneVal(trimLog, "request");
                String decrypt = ThreeDES.decrypt(request);
                GotoneLogRequest bean = JSON.parseObject(decrypt, GotoneLogRequest.class);

                GotoneLogRequest req = new GotoneLogRequest();
                req.setRequestRefId(GotoneLogFileUtil.getGotoneVal(trimLog, "requestRefId"));
                req.setPhone(bean.getPhone());
                req.setSecretId(GotoneLogFileUtil.getGotoneVal(trimLog, "secretId"));
                ctx.put(req.getRequestRefId(), req);
                return null;
            }

            boolean responseType = GotoneLogFileUtil.isResponseType(trimLog);
            if (responseType) {
                response = GotoneLogFileUtil.getGotoneVal(trimLog, "response");
                String decrypt = ThreeDES.decrypt(response);
                String responseCode = GotoneLogFileUtil.getGotoneVal(trimLog, "responseCode");
                if (StrUtil.isBlank(response)
                        || StrUtil.isBlank(responseCode)
                        || !"0000".equalsIgnoreCase(responseCode)) {
                    return null;
                }
                GotoneLevelBean bean = JSON.parseObject(decrypt, GotoneLevelBean.class);
                String requestRefId = GotoneLogFileUtil.getGotoneVal(trimLog, "requestRefId");
                GotoneLogRequest gotoneLogRequest = ctx.get(requestRefId);
                if (gotoneLogRequest == null) {
                    log.info("无法找到对应的request,refId={}", requestRefId);
                    return null;
                }
                PhoneTagKv kv = new PhoneTagKv();
                kv.setPhone(gotoneLogRequest.getPhone());
                kv.setTag(bean.getGotoneLevel());
                kv.setTagText(getDesc(kv.getTag()));
                return kv;
            }
        } catch (Throwable e) {
            log.error("request={}, response={}", request, response, e);
            return null;
        }

        return null;
    }

    private String getDesc(String key) {

        switch (key) {
            case "1":
                return "全球通银卡";
            case "2":
                return "全球通金卡";
            case "3":
                return "全球通白金卡";
            case "4":
                return "全球通钻石卡（非终身全球通用户）";
            case "5":
                return "终身全球通用户";
            case "6":
                return "全球通体验用户";
            case "7":
                return "其他非全球通用户";
            case "-2":
                return "数据异常";
            case "-1":
                return "非移动用户";
            case "-99":
                return "暂无用户数据";
            default:
                return "未知";
        }
    }

    @Data
    private static class GotoneLevelBean {
        private String gotoneLevel;
    }
}

