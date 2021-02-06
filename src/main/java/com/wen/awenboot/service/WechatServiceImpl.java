package com.wen.awenboot.service;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.wen.awenboot.common.OkHttpUtil;
import com.wen.awenboot.config.MalaConfig;
import com.wen.awenboot.controller.request.CodeRequest;
import com.wen.awenboot.controller.response.CodeResponse;
import com.wen.awenboot.controller.response.Openid;
import com.wen.awenboot.controller.response.Result;
import com.wen.awenboot.domain.BizUserAccessLog;
import com.wen.awenboot.utils.RedisCli;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;

/**
 * @author wen
 * @version 1.0
 * @date 2021/2/5 22:54
 */
@Service
@Slf4j
public class WechatServiceImpl {
    @Autowired
    private MalaConfig cfg;
    @Autowired
    private OkHttpUtil client;
    @Autowired
    private BizUserAccessLogServiceImpl accessLogService;

    public Result getWechatCode(@RequestBody CodeRequest query) {
        Result codeRet;
        if (cfg.isMockWechatCode()) {
            CodeResponse cr = new CodeResponse();
            cr.setToken("token_123456");
            BizUserAccessLog alog = new BizUserAccessLog();
            alog.setCode(query.getCode());
            alog.setSessionKey("test_sessionKey_wen123456");
            alog.setOpenid("test_openid_wen123456");
            alog.setCreateDate(new Date());
            alog.setExpiresIn(7200);
            RedisCli.getInstance().set(cr.getToken(), alog);
            log.info("mockwechatcode 方法,不写入数据库,token={},alog={}", cr.getToken(), alog);
            codeRet = Result.success(cr);
            return codeRet;
        }
        String ret = getCodeRet(query);

        if (ret.contains("openid")) {
            Openid openid = JSON.parseObject(ret, Openid.class);
            CodeResponse cr = new CodeResponse();
            cr.setToken("token_" + IdUtil.simpleUUID());

            BizUserAccessLog alog = new BizUserAccessLog();
            alog.setCode(query.getCode());
            alog.setSessionKey(openid.getSession_key());
            alog.setOpenid(openid.getOpenid());
            alog.setCreateDate(new Date());
            alog.setExpiresIn(openid.getExpires_in());
            accessLogService.add(alog);
            RedisCli.getInstance().set(cr.getToken(), alog);
            log.info("set redis,token={},alog={}", cr.getToken(), alog);
            codeRet = Result.success(cr);
        } else {
            codeRet = Result.error(ret);
        }
        return codeRet;
    }

    private String getCodeRet(@RequestBody CodeRequest query) {
        //        "errcode":40163,"errmsg":"code been used, hints: [ req_id: qJlaJTNre-RfDw4 ]"}
//        {"session_key":"b9ew6daXEGhiO0PPnYIiMQ==","expires_in":7200,"openid":"o8SQK0V5IdyCoyetsEJp5bppxsqw"}
        StringBuilder sb = new StringBuilder();
//        sb.append("https://api.weixin.qq.com/sns/jscode2session?appid=wx9de528650a768c77&secret=db8368aa78a1945ff7bdfdbcb598489e");
        sb.append("https://api.weixin.qq.com/sns/jscode2session?");
        sb.append("appid=").append(cfg.getAppid());
        sb.append("&secret=").append(cfg.getSecret());
        sb.append("&js_code=").append(query.getCode());
        sb.append("&grant_type=authorization_code");

        log.info("url={}", sb.toString());

        Response resp = client.getData(sb.toString());
        String ret = "";
        try {
            ret = resp.body().string();
        } catch (Exception e) {
            log.error("请求异常,ret={}", ret, e);
        }
        return ret;
    }
}
