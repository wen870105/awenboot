package com.wen.awenboot.service;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.wen.awenboot.common.OkHttpUtil;
import com.wen.awenboot.config.MalaConfig;
import com.wen.awenboot.controller.request.CodeRequest;
import com.wen.awenboot.controller.response.CodeResponse;
import com.wen.awenboot.controller.response.Openid;
import com.wen.awenboot.controller.response.Result;
import com.wen.awenboot.converter.BizUserConverter;
import com.wen.awenboot.domain.BizUser;
import com.wen.awenboot.domain.BizUserAccessLog;
import com.wen.awenboot.domain.BizWechatPwd;
import com.wen.awenboot.utils.RedisCli;
import com.wen.awenboot.vo.UserInfoVo;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    @Autowired
    private BizWechatPwdServiceImpl pwdService;
    @Autowired
    private BizUserConverter userConverter;
    @Autowired
    private BizUserServiceImpl userService;

    public Result getWechatCode(CodeRequest query) {
        Result codeRet;
        if (cfg.isMockWechatCode()) {
            Openid openid = new Openid();
            openid.setSession_key("test_sessionKey_wen123456");
            openid.setExpires_in(7200);
            openid.setOpenid("test_openid_wen123456");

            CodeResponse cr = new CodeResponse();
            cr.setToken("token_123456");

            UserInfoVo userInfoVo = updateUserInfo(query.getCode(), openid);

            RedisCli.getInstance().set(cr.getToken(), userInfoVo);
            log.info("mockwechatcode 方法 set redis,token={},userInfoVo={}", cr.getToken(), userInfoVo);
            codeRet = Result.success(cr);
            return codeRet;
        }
        String ret = getCodeRet(query);

        if (ret.contains("openid")) {
            Openid openid = JSON.parseObject(ret, Openid.class);
            CodeResponse cr = new CodeResponse();
            cr.setToken("token_" + IdUtil.simpleUUID());

            UserInfoVo userInfoVo = updateUserInfo(query.getCode(), openid);

            RedisCli.getInstance().set(cr.getToken(), userInfoVo);
            log.info("set redis,token={},userInfoVo={}", cr.getToken(), userInfoVo);
            codeRet = Result.success(cr);
        } else {
            codeRet = Result.error(ret);
        }
        return codeRet;
    }

    private UserInfoVo updateUserInfo(String code, Openid openid) {
        String openid1 = openid.getOpenid();
        BizUserAccessLog alog = new BizUserAccessLog();
        alog.setCode(code);
        alog.setSessionKey(openid.getSession_key());
        alog.setOpenid(openid1);
        alog.setCreateDate(new Date());
        alog.setExpiresIn(openid.getExpires_in());
        accessLogService.add(alog);

        BizUser bizUser = userService.getByOpenid(openid1);
        if (bizUser == null) {
            BizUser adder = new BizUser();
            adder.setOpenid(openid1);
            adder.setCreateDate(new Date());
            userService.add(adder);
            bizUser = userService.getByOpenid(openid1);
        }

        UserInfoVo userInfoVo = userConverter.ud2v(bizUser);
        BizWechatPwd pwdInfo = pwdService.getByOpenid(openid1);
        userInfoVo.setSessionKey(openid.getSession_key());
        userInfoVo.setBizWechatPwd(pwdInfo);
        return userInfoVo;
    }

    private String getCodeRet(CodeRequest query) {
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
