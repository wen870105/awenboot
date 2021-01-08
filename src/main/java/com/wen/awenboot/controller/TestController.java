package com.wen.awenboot.controller;

import cn.hutool.core.date.TimeInterval;
import com.wen.awenboot.service.ColumnValueMapBakServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/test")
@RestController
@Slf4j
public class TestController {

    @Autowired
    private ColumnValueMapBakServiceImpl service;

    @RequestMapping("/delete")
    @ResponseBody
    public Object delete() {
        service.deleteAll();
        return "success";
    }


    @RequestMapping("/query")
    @ResponseBody
    public Object query(@RequestBody QueryRequest query) {
        TimeInterval ti = new TimeInterval();

        log.info("耗时=" + ti.interval() + "ms");
        return "耗时=" + ti.interval() + "ms";
    }


}
