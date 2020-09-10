package com.wen.awenboot.controller;

import com.wen.awenboot.integration.zhuangku.ProductInfo;
import com.wen.awenboot.integration.zhuangku.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/test")
@Slf4j
public class TestController {


    private static int counter = 1;

    @RequestMapping("/{phone}")
    @ResponseBody
    public Object index(String phone) {
        Result ret = new Result();
        ret.setResultCode("0000");
        ProductInfo info = new ProductInfo();
        info.setImei("");
        info.setProducts("product1:1.76,p2:2,p3:4");
        info.setProvId("");
        ret.setProductInfo(info);
//        JSONObject obj = new JSONObject();
//        obj.put("resultCode", "0001");
//        obj.put("productInfo", null`);
//        log.info("test demo===============" + obj.toJSONString());
        try {
            TimeUnit.MILLISECONDS.sleep(800);
        } catch (InterruptedException e) {
            log.error("", e);
        }
        return ret;
    }

}
