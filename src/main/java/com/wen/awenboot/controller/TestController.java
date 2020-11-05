package com.wen.awenboot.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wen.awenboot.biz.service.ResolverFileService;
import com.wen.awenboot.common.OkHttpUtil;
import com.wen.awenboot.config.ZhuangkuConfig;
import com.wen.awenboot.integration.zhuangku.ProductInfo;
import com.wen.awenboot.integration.zhuangku.Result;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/test")
@Slf4j
public class TestController {

    @Autowired
    private ZhuangkuConfig cfg;

    private static int counter = 333;

    private static OkHttpUtil client = OkHttpUtil.getInstance();

    //    @RequestMapping(value = "/header", produces = "application/json;charset=UTF-8")
    @RequestMapping(value = "/header")
    @ResponseBody
    public Object testHeader(@RequestHeader HttpHeaders headers) {
        Result ret = new Result();
        ret.setResultCode("0000");
//            ret.setResultCode("0001");
        ProductInfo info = new ProductInfo();
        info.setImei("");
        info.setProducts("product1:1.76,p2:2,p3:4");
        info.setProvId("");
        ret.setProductInfo(info);
        return ret;
    }

    @RequestMapping(value = "/music")
    @ResponseBody
    public Object testHusic(@RequestBody String request) {
        log.info("testMusic,request={}", request);
        JSONObject ret = new JSONObject();

        JSONObject head = new JSONObject();
        head.put("serviceCode", "0000");
        JSONObject resp = new JSONObject();
        ret.put("head", head);
        ret.put("response", resp);

        JSONArray arr = new JSONArray();
        JSONObject user = new JSONObject();
        user.put("product_info", "123:11");
        arr.add(user);
        resp.put("param", arr);

        return ret;
    }

    @RequestMapping(value = "/header/{phone}")
    @ResponseBody
    public Object testHeader123(@PathVariable("phone") String phone) {
        log.info("testHader phone={},request={}", phone);
        Result result = getResult(phone);
        log.info("result={}", JSON.toJSONString(result));

        return "success";
    }

    private Result getResult(String phone) {
        long start = System.currentTimeMillis();
        String url = "http://127.0.0.1:8080/test/header";
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        header.put("secretId", "9155CBA");
        header.put("requestRefId", "20200710616161AdfA335605");
        header.put("signature", "KUHcExujn2Q1n7F2Wc0xPUhQXo9wwiiVfJjz/vmIXmE=");

        Map<String, String> body = new HashMap<>();
        body.put("userId", phone);

        Response resp = client.postData(url, header, body);
        Result result = null;
        String ret = null;

        try {
            ret = resp.body().string();
            result = JSON.parseObject(ret, Result.class);
        } catch (Exception e) {
            log.error("请求异常,ret={}", ret, e);
        }

        long end = System.currentTimeMillis();
//        if (printCount % cfg.getPrintFlag() == 0) {
        log.info("耗时{}ms,间隔{}次请求打印一次日志,ret={},phone={}", (end - start), cfg.getPrintFlag(), ret, phone);
//        }

        return result;
    }

    @RequestMapping("/resolve/{file1}")
    @ResponseBody
    public Object resolve(@PathVariable("file1") String file1) {
        log.info("异步解析文件file1={}", file1);
        new Thread(() -> {
            log.info("异步解析文件file1={}", file1);
            TimeInterval timer = DateUtil.timer();
            ResolverFileService rfs = new ResolverFileService(cfg, file1);
            if (rfs.getDataSourceFile() != null) {
                rfs.export();
            }
            long interval = timer.interval();
            log.info("解析文件耗时{}ms", interval);
        }).start();

        return "success";
    }


    @RequestMapping("/phone/{phone}")
    @ResponseBody
    public Object index(String phone) {
        log.info("phone={}", phone);
        Result ret = new Result();
//        if (++counter % 1000 == 0) {
        ret.setResultCode("0000");
////        } else {
//            ret.setResultCode("0001");
////        }
        ProductInfo info = new ProductInfo();
        info.setImei("");
        info.setProducts("product1:1.76,p2:2,p3:4");
        info.setProvId("");
        ret.setProductInfo(info);
//        JSONObject obj = new JSONObject();
//        obj.put("resultCode", "0001");
//        obj.put("productInfo", null`);
//        log.info("test demo===============" + obj.toJSONString());
//        try {
//            TimeUnit.MILLISECONDS.sleep(800);
//        } catch (InterruptedException e) {
//            log.error("", e);
//        }
        return ret;
    }

}
