package com.wen.awenboot.controller;

import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.wen.awenboot.config.MalaConfig;
import com.wen.awenboot.controller.request.AddpwdRequest;
import com.wen.awenboot.controller.request.CodeRequest;
import com.wen.awenboot.controller.request.DetailRequest;
import com.wen.awenboot.controller.request.ListRequest;
import com.wen.awenboot.controller.response.DetailResponse;
import com.wen.awenboot.controller.response.Result;
import com.wen.awenboot.converter.BizBlogConverter;
import com.wen.awenboot.domain.BizBlog;
import com.wen.awenboot.domain.BizUserAccessLog;
import com.wen.awenboot.domain.BizWechatPwd;
import com.wen.awenboot.domain.base.Page;
import com.wen.awenboot.service.BizBlogServiceImpl;
import com.wen.awenboot.service.BizUserAccessLogServiceImpl;
import com.wen.awenboot.service.BizUserServiceImpl;
import com.wen.awenboot.service.BizWechatPwdServiceImpl;
import com.wen.awenboot.service.OpenidServiceImpl;
import com.wen.awenboot.service.WechatServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 */
@RequestMapping("/wechat")
@RestController
@Slf4j
public class WechatController {
    @Autowired
    private BizUserAccessLogServiceImpl logService;
    @Autowired
    private BizUserServiceImpl userService;
    @Autowired
    private BizWechatPwdServiceImpl pwdService;
    @Autowired
    private BizBlogServiceImpl blogService;
    @Autowired
    private BizBlogConverter blogConverter;
    @Autowired
    private WechatServiceImpl wechatService;
    @Autowired
    private OpenidServiceImpl openidService;
    @Autowired
    private MalaConfig cfg;

    private AtomicLong id = new AtomicLong(0L);

    @RequestMapping("/createpwd")
    @ResponseBody
    public Object createpwd() {
        BizWechatPwd pwd = new BizWechatPwd();
        pwd.setCode(generateCode());
        pwd.setCreator("system");
        pwd.setCreateTime(new Date());
        pwd.setStatus(0);
        pwdService.add(pwd);
        log.info("resp={}", JSON.toJSONString(pwd));
        return pwd;
    }

    private String generateCode() {
        while (true) {
            String code = RandomUtil.randomNumbers(6);
            BizWechatPwd byCode = pwdService.getByCode(code);
            if (byCode == null) {
                return code;
            }
            log.info("重复code,重新生成密码,code={}", code);
        }
    }


    @RequestMapping("/addpwd")
    @ResponseBody
    public Object addpwd(@RequestBody AddpwdRequest query) {
        log.info("request={}", JSON.toJSONString(query));
        BizWechatPwd byPwd = pwdService.getByCode(query.getPwd());
        Result codeRet = null;
        if (byPwd == null) {
            log.info("pwd={}无效", query.getPwd());
            codeRet = Result.error("pwd={}无效" + query.getPwd());
        } else {
            if (byPwd.getStatus() == 0) {
                BizUserAccessLog accessLog = openidService.getAccessLog();
                if (accessLog != null) {
                    boolean b = pwdService.updateValidPwd(query.getPwd(), accessLog.getOpenid());
                    codeRet = Result.msg("修改状态b=" + b);
                } else {
                    codeRet = Result.error("token无效");
                }
            } else {
                log.info("pwd={}已经使用", query.getPwd());
                codeRet = Result.error("pwd={}已经使用" + query.getPwd());
            }
        }
        log.info("resp={}", JSON.toJSONString(codeRet));
        return codeRet;
    }

    @RequestMapping("/detail")
    @ResponseBody
    public Object detail(@RequestBody DetailRequest query) {
        log.info("request={}", JSON.toJSONString(query));
        final BizBlog bizBlog = blogService.selectById(query.getId());
        Result codeRet = null;
        if (bizBlog == null) {
            log.info("数据为空id={}", query.getId());
            codeRet = Result.error("数据为空id=" + query.getId());
        } else {
            final DetailResponse detailResponse = blogConverter.blog2Detail(bizBlog);
            codeRet = Result.success(detailResponse);
        }
        log.info("resp={}", JSON.toJSONString(codeRet));
        return codeRet;
    }

    @RequestMapping("/list")
    @ResponseBody
    public Object list(@RequestBody ListRequest request) {
        log.info("request={}", request);
        BizBlog query = new BizBlog();
        query.setPageIndex(request.getPageIndex());
        query.setOffset(request.getPageSize());
        Page<BizBlog> pager = blogService.selectPage(query);
        Page<DetailResponse> pageDetail = blogConverter.p2p(pager);
        Result codeRet = Result.success(pageDetail);
        log.info("resp={}", JSON.toJSONString(codeRet));
        return codeRet;
    }


    @RequestMapping("/code")
    @ResponseBody
    public Result code(@RequestBody CodeRequest query) {
        log.info("request={}", query);
        TimeInterval ti = new TimeInterval();
        Result codeRet = wechatService.getWechatCode(query);
        log.info("耗时{}ms,resp={}", ti.interval(), JSON.toJSONString(codeRet));
        return codeRet;
    }


    public static void main(String[] args) {
        for (int i = 0; i < 6; i++) {
            System.out.println(RandomUtil.randomNumbers(6));
        }

        System.out.println(IdUtil.simpleUUID());
//        System.out.println(uuid.get);
    }

}
