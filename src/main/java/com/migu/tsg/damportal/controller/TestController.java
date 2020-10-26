package com.migu.tsg.damportal.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import com.alibaba.fastjson.JSON;
import com.migu.tsg.damportal.cache.TagDetailCntCache;
import com.migu.tsg.damportal.cache.TagDetailCntVO;
import com.migu.tsg.damportal.domain.MiguTagInfo;
import com.migu.tsg.damportal.enums.TagGroupEnum;
import com.migu.tsg.damportal.service.MiguTagAttributeServiceImpl;
import com.migu.tsg.damportal.service.MiguTagInfoServiceImpl;
import com.migu.tsg.damportal.utils.ExcelTagBean;
import com.migu.tsg.damportal.utils.ExcelUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

@RequestMapping("/test")
@RestController
@Slf4j
public class TestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private MiguTagInfoServiceImpl infoService;

    @Autowired
    private TagDetailCntCache cntCache;

    @Autowired
    private MiguTagAttributeServiceImpl attrService;


    @RequestMapping("viewCache")
    @ResponseBody
    public Object viewCache() {
        ConcurrentMap<String, TagDetailCntVO> ret = cntCache.asMap();
        return "caches=" + JSON.toJSONString(ret);
    }

    @RequestMapping("mockCache")
    @ResponseBody
    public Object mockCache(@RequestBody TestBean request) {
        List<String> tagCodes = request.getTagCodes();

        List<String> validateCodes = new ArrayList<>();
        tagCodes.forEach(code -> {
            if (infoService.validateTagCode(code)) {
                validateCodes.add(code);
                cntCache.incrementAndGet(code);
            }
        });
        ConcurrentMap<String, TagDetailCntVO> ret = cntCache.asMap();
        return "有效列表codes=" + validateCodes.toString()+"caches=" + JSON.toJSONString(ret);
    }

    @RequestMapping("importExcel")
    @ResponseBody
    public Object importExcel() {
        List<MiguTagInfo> ret = new ArrayList<>(200);
        ret.addAll(buildList(ExcelUtils.readFile(0)));
//        ret.addAll(buildList(ExcelUtils.readFile(1)));
//        ret.addAll(buildList(ExcelUtils.readFile(2)));
//        ret.addAll(buildList(ExcelUtils.readFile(3)));
//        ret.addAll(buildList(ExcelUtils.readFile(4)));
        String tag = infoService.insertTags(ret);
        if (tag != null) {
            return "fail:重复ID=" + tag;
        }
        return "success";
    }

    @RequestMapping("downAll")
    @ResponseBody
    public Object downAll() {
        List<MiguTagInfo> list = infoService.getAll();
        List<ExcelTagBean> excelTagBeans = infoService.buildExcelBean(list);
        Map<String, List<ExcelTagBean>> listMap = excelTagBeans.stream().collect(Collectors.groupingBy(ExcelTagBean::getCategory1_ori));

        List<ExcelTagBean> basic = listMap.get(TagGroupEnum.BASIC.getCode());
        List<ExcelTagBean> consume = listMap.get(TagGroupEnum.CONSUME.getCode());
        List<ExcelTagBean> offbehaviro = listMap.get(TagGroupEnum.OFFBEHAVIRO.getCode());
        List<ExcelTagBean> operate = listMap.get(TagGroupEnum.OPERATE.getCode());
        List<ExcelTagBean> special = listMap.get(TagGroupEnum.SPECIAL.getCode());

        String filePath = "d:/test_" + DateTime.now().toString("yyyyMMddHHmmss") + ".xlsx";
        if (CollUtil.isNotEmpty(basic)) {
            ExcelUtils.writeFile(filePath, basic, "基础属性");
        }
        if (CollUtil.isNotEmpty(consume)) {
            ExcelUtils.writeFile(filePath, consume, "消费属性");
        }
        if (CollUtil.isNotEmpty(offbehaviro)) {
            ExcelUtils.writeFile(filePath, offbehaviro, "行为属性");
        }
        if (CollUtil.isNotEmpty(operate)) {
            ExcelUtils.writeFile(filePath, operate, "运营属性");
        }
        if (CollUtil.isNotEmpty(special)) {
            ExcelUtils.writeFile(filePath, special, "专区属性");
        }
        return "success";
    }

    private List<MiguTagInfo> buildList(List<ExcelUtils.ObjEntry> list) {
        List<MiguTagInfo> ret = new ArrayList<>(list.size());

        List<MiguTagInfo> fathersList = new ArrayList<>();
        Map<String, MiguTagInfo> fathersMap = new HashMap<>();

        list.forEach(obj -> ret.add(ExcelUtils.buildMiguTagInfo(obj, fathersMap)));
        ret.addAll(fathersMap.values());
        return ret;
    }


}
