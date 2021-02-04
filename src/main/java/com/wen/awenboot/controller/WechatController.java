package com.wen.awenboot.controller;

import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.wen.awenboot.common.OkHttpUtil;
import com.wen.awenboot.controller.request.CodeRequest;
import com.wen.awenboot.controller.request.DetailRequest;
import com.wen.awenboot.controller.response.CodeResponse;
import com.wen.awenboot.controller.response.DetailResponse;
import com.wen.awenboot.controller.response.Openid;
import com.wen.awenboot.controller.response.Result;
import com.wen.awenboot.service.BizUserAccessLogServiceImpl;
import com.wen.awenboot.service.BizUserServiceImpl;
import com.wen.awenboot.service.BizWechatPwdServiceImpl;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 */
@RequestMapping("/wechat")
@RestController
@Slf4j
public class WechatController {
    @Autowired
    private OkHttpUtil client;
    @Autowired
    private BizUserAccessLogServiceImpl logService;
    @Autowired
    private BizUserServiceImpl userService;
    @Autowired
    private BizWechatPwdServiceImpl pwdService;

    private AtomicLong id = new AtomicLong(0L);

    @RequestMapping("/detail")
    @ResponseBody
    public Object detail(@RequestBody DetailRequest query) {
        log.info("request={}", JSON.toJSONString(query));
        DetailResponse detail = new DetailResponse();
        detail.setId(query.getId() + "");
        detail.setTitle("课程" + query.getId());
        if (query.getId() % 2 == 0) {
            detail.setPaid(false);
        } else {
            detail.setThumbnail("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3053987886,3653353064&fm=26&gp=0.jpg");
            detail.setVideo("https://wen-school.oss-cn-beijing.aliyuncs.com/xxy/%E5%89%8D%E5%AF%BC%E8%AF%BE%E8%AF%BE1.mov");
            detail.setPpt("https://wen-school.oss-cn-beijing.aliyuncs.com/xxy/%E5%93%91%E5%B7%B4%E5%8F%A3%E8%AF%AD%E5%89%8D%E5%AF%BC.%E4%B8%80%E8%AE%B2.pptx");
            detail.setPaid(true);
        }
        Result codeRet = Result.success(detail);
        log.info("resp={}", JSON.toJSONString(codeRet));
        return codeRet;
    }

    @RequestMapping("/list")
    @ResponseBody
    public Object list(DetailRequest query) {


        DetailResponse detail = new DetailResponse();
        detail.setId(id.incrementAndGet() + "");
        detail.setThumbnail("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3053987886,3653353064&fm=26&gp=0.jpg");
        detail.setTitle("课程" + detail.getId());

        DetailResponse d2 = new DetailResponse();
        d2.setId(id.incrementAndGet() + "");
        d2.setThumbnail("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3053987886,3653353064&fm=26&gp=0.jpg");
        d2.setTitle("课程" + d2.getId());

        DetailResponse d3 = new DetailResponse();
        d3.setId(id.incrementAndGet() + "");
        d3.setThumbnail("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3053987886,3653353064&fm=26&gp=0.jpg");
        d3.setTitle("课程" + d3.getId());


        DetailResponse d4 = new DetailResponse();
        d4.setId(id.incrementAndGet() + "");
        d4.setThumbnail("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3053987886,3653353064&fm=26&gp=0.jpg");
        d4.setTitle("课程" + d4.getId());


        DetailResponse d5 = new DetailResponse();
        d5.setId(id.incrementAndGet() + "");
        d5.setThumbnail("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3053987886,3653353064&fm=26&gp=0.jpg");
        d5.setTitle("课程" + d5.getId());


        List<DetailResponse> list = Arrays.asList(detail, d2, d3, d4, d5);
        Result codeRet = Result.success(list);
        log.info("resp={}", JSON.toJSONString(codeRet));
        return codeRet;
    }


    @RequestMapping("/code")
    @ResponseBody
    public Result code(@RequestBody CodeRequest query) {
        log.info("request={}", query);
//        "errcode":40163,"errmsg":"code been used, hints: [ req_id: qJlaJTNre-RfDw4 ]"}
//        {"session_key":"b9ew6daXEGhiO0PPnYIiMQ==","expires_in":7200,"openid":"o8SQK0V5IdyCoyetsEJp5bppxsqw"}
        TimeInterval ti = new TimeInterval();
        StringBuilder sb = new StringBuilder();
        sb.append("https://api.weixin.qq.com/sns/jscode2session?appid=wx9de528650a768c77&secret=db8368aa78a1945ff7bdfdbcb598489e");
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
        Result codeRet;
        if (ret.contains("openid")) {
            Openid openid = JSON.parseObject(ret, Openid.class);
            CodeResponse cr = new CodeResponse();
            cr.setToken(IdUtil.simpleUUID());
            codeRet = Result.success(cr);
        } else {
            codeRet = Result.error(ret);
        }
        log.info("resp={}", JSON.toJSONString(codeRet));
        return codeRet;
    }

    public static void main(String[] args) {
        System.out.println(IdUtil.simpleUUID());
//        System.out.println(uuid.get);
    }

}
