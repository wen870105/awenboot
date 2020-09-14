package com.wen.awenboot.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import com.wen.awenboot.biz.service.ResolverFileService;
import com.wen.awenboot.config.ZhuangkuConfig;
import com.wen.awenboot.integration.zhuangku.ProductInfo;
import com.wen.awenboot.integration.zhuangku.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/test")
@Slf4j
public class TestController {

    @Autowired
    private ZhuangkuConfig cfg;

    private static int counter = 333;

    @RequestMapping("/resolve/{file1}")
    @ResponseBody
    public Object resolve(@PathVariable("file1") String file1) {
        log.info("异步解析文件file1={}", file1);
        new Thread(()->{
            log.info("异步解析文件file1={}", file1);
            TimeInterval timer = DateUtil.timer();
            ResolverFileService rfs = new ResolverFileService(cfg, file1);
            if(rfs.getDataSourceFile()!=null){
                rfs.export();
            }
            long interval = timer.interval();
            log.info("解析文件耗时{}ms", interval);
        }).start();

        return "success";
    }

    @RequestMapping("/{phone}")
    @ResponseBody
    public Object index(String phone) {
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
