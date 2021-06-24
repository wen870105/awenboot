package com.wen.awenboot.controller;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wen.awenboot.biz.service.ResolverFileService;
import com.wen.awenboot.cache.TagDetailCntCache;
import com.wen.awenboot.config.ZhuangkuConfig;
import com.wen.awenboot.dao.MiguTagApiDetailCntMapper;
import com.wen.awenboot.enums.ApiDetailCntEnum;
import com.wen.awenboot.integration.imei.BaseResponseDTO;
import com.wen.awenboot.integration.imei.ImeiRequestDTO;
import com.wen.awenboot.integration.imei.ReqParamDTO;
import com.wen.awenboot.integration.imei.ResponseBodyDTO;
import com.wen.awenboot.integration.imei.ResponseHeadDTO;
import com.wen.awenboot.integration.zhuangku.ProductInfo;
import com.wen.awenboot.integration.zhuangku.Result;
import com.wen.awenboot.integration.zhuangku.ResultMusic;
import com.wen.awenboot.task.BrandLogTask;
import com.wen.awenboot.task.GotoneTask;
import com.wen.awenboot.task.InitIMEITask;
import com.wen.awenboot.task.InitVideoDay;
import com.wen.awenboot.utils.DateUtils;
import com.wen.awenboot.utils.ImeiUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

@RestController
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

    @Autowired
    private InitIMEITask mInitIMEITask;

    @Resource
    private DataSource dataSource;

    @Resource
    private MiguTagApiDetailCntMapper infoMapper;

    @RequestMapping(value = "/imei")
    @ResponseBody
    public BaseResponseDTO imei(@RequestBody ImeiRequestDTO req) {
        log.info("ImeiRequestDTO ={}", JSON.toJSONString(req));
        String imei = JSON.parseObject(ImeiUtils.decodeParam(req.getRequest()), ReqParamDTO.class).getParam().getImei();
        ResponseHeadDTO head = new ResponseHeadDTO();
        head.setRequestRefId("requestId-" + IdUtil.simpleUUID());
        head.setResult("Y");
        head.setResponseCode("0000");
        head.setResponseMsg("查询成功-" + imei);

        ResponseBodyDTO body = new ResponseBodyDTO();
        body.setNumberLists("18382479394");
        String s = JSON.toJSONString(body);
        String response = ImeiUtils.getParam(s);

        BaseResponseDTO ret = new BaseResponseDTO();
        ret.setHead(head);
        ret.setResponse(response);
        log.info("ret ={}", JSON.toJSONString(ret));
        return ret;
    }

    @GetMapping("/query")
    public Object query() {
        System.out.println("查询到的数据源连接池信息是:" + dataSource);
        System.out.println("查询到的数据源连接池类型是:" + dataSource.getClass());
        return JSON.toJSONString(infoMapper.selectAll());
    }

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

    @RequestMapping(value = "/task/imei/{date}")
    @ResponseBody
    public Object taskImei(@PathVariable("date") String date) {
        mInitIMEITask.execute1(date);
        return "success";
    }


    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            sb.append("860").append(RandomUtil.randomNumbers(11)).append("\n");
        }
        try {
            FileUtils.write(new File("D:\\wen_test\\data\\imei-" + DateUtils.asString(new Date(), "yyyyMMdd") + ".txt"), sb.toString(), "UTF-8");
        } catch (IOException e) {
            log.error("", e);
        }
    }
}
