package com.wen.awenboot.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wen.awenboot.biz.service.ResolverFileService;
import com.wen.awenboot.config.ZhuangkuConfig;
import com.wen.awenboot.integration.zhuangku.ProductInfo;
import com.wen.awenboot.integration.zhuangku.Result;
import com.wen.awenboot.integration.zhuangku.ResultMusic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/test")
@Slf4j
public class TestController {

    @Autowired
    private ZhuangkuConfig cfg;

    private static int counter = 333;

    @RequestMapping(value = "/error")
    @ResponseBody
    public Object error() {

        log.error("errorDemo");
        log.error("errorDemo");
        log.error("errorDemo");
        log.error("errorDemo");
        log.error("errorDemo");
        log.error("errorDemo");
        log.error("errorDemo");
        log.error("errorDemo");
        log.error("errorDemo");
        log.error("errorDemo");
        return "errorDemo";
    }

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
//        log.info("testMusic,request={}", request);
        JSONObject resp = new JSONObject();

        JSONObject head = new JSONObject();
        head.put("responseCode", "0000");

        JSONObject response = new JSONObject();


        JSONArray arr = new JSONArray();
        JSONObject obj1 = new JSONObject();
        obj1.put("product_info", "1111:1111");
        arr.add(obj1);

        response.put("param", arr);
        resp.put("head", head);
        resp.put("response", response);

        ResultMusic resultMusic = JSON.parseObject(resp.toJSONString(), ResultMusic.class);

        return resultMusic;
    }


    @RequestMapping("/resolve/{file1}")
    @ResponseBody
    public Object resolve(@PathVariable("file1") String file1) {
        log.info("异步解析文件file1={}", file1);
        new ResolverFileService(cfg, file1).asyncExport();
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
        return ret;
    }

}
