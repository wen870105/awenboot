package com.wen.awenboot.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wen.awenboot.biz.service.ResolverFileService;
import com.wen.awenboot.cache.TagDetailCntCache;
import com.wen.awenboot.config.ZhuangkuConfig;
import com.wen.awenboot.enums.ApiDetailCntEnum;
import com.wen.awenboot.integration.zhuangku.ProductInfo;
import com.wen.awenboot.integration.zhuangku.Result;
import com.wen.awenboot.integration.zhuangku.ResultMusic;
import com.wen.awenboot.task.BrandLogTask;
import com.wen.awenboot.task.GotoneTask;
import com.wen.awenboot.task.InitVideoDay;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.util.Arrays;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/test")
@Slf4j
public class TestController {

    @Autowired
    private TagDetailCntCache cntCache;
    @Autowired
    private ZhuangkuConfig cfg;

    private static int counter = 333;

    @Autowired
    private InitVideoDay initVideoDay;

    @Autowired
    private BrandLogTask brandLogTask;

    @Autowired
    private GotoneTask gotoneTask;

    @Autowired
    private InitVideoDay mInitVideoDay;

    @RequestMapping("/addCache/{key}")
    @ResponseBody
    public Object addCache(@PathVariable("key") String key) {
        final ApiDetailCntEnum enumByCode = ApiDetailCntEnum.getEnumByCode(key);
        if (enumByCode == null) {
            return "fault";
        }
        return cntCache.incrementAndGet(enumByCode.getCode());
    }

    @RequestMapping("/gotoneList/{date}")
    @ResponseBody
    public Object gotoneList2(@PathVariable("date") String date) {
        File file = new File(cfg.getDataSourceDir() + cfg.getDtpFilePrefix() + date);
        if (!file.exists()) {
            log.error("================{}文件不存在", file.getPath());
            log.error("================{}文件不存在", file.getPath());
            log.error("================{}文件不存在", file.getPath());
            return "error";
        }
        gotoneTask.execute1(file);
        return "success";
    }

    @RequestMapping("/gotone/list")
    @ResponseBody
    public Object gotoneList() {
        log.info("gotoneList start");
        File dir = new File(cfg.getDataSourceDir());
        File[] files = dir.listFiles(f -> {
            return !f.getName().contains("12-13")
                    && !f.getName().contains("12-14")
                    && !f.getName().contains("12-15")
                    && !f.getName().contains("12-16")
                    && f.getName().endsWith(".log");
        });
        log.info("所有的文件list={}", Arrays.stream(files).map(File::getName).collect(Collectors.toList()));

        for (File f : files) {
            gotoneTask.execute1(f);
        }
        log.info("finish gotoneList 1111111111");
        log.info("finish gotoneList 1111111111");
        log.info("finish gotoneList 1111111111");
        log.info("finish gotoneList 1111111111");
        log.info("finish gotoneList 1111111111");
        return "success";
    }

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

    @RequestMapping("/video/{phone}")
    @ResponseBody
    public Object index(String phone) {
//        log.info("phone={}", phone);
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


    @RequestMapping("/taskDay")
    @ResponseBody
    public Object taskDay() {
        return initVideoDay.task();
    }

    @RequestMapping("/taskBrand/{date}")
    @ResponseBody
    public Object taskBrand(@PathVariable("date") String date) {
        File file = new File(cfg.getDataSourceDir() + cfg.getDtpFilePrefix() + date);
        if (!file.exists()) {
            log.error("================{}文件不存在", file.getPath());
            log.error("================{}文件不存在", file.getPath());
            log.error("================{}文件不存在", file.getPath());
            return "error";
        }
        brandLogTask.execute1(file);
        return "success";
    }

    @RequestMapping("/task/videoDay")
    @ResponseBody
    public Object mInitVideoDayTask() {
        mInitVideoDay.task();
        return "success";
    }


}
