package com.wen.awenboot.controller;

import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/test")
@RestController
@Slf4j
public class TestController {

    public static void main(String[] args) {
        System.out.println(RandomUtil.randomString(26));
    }

//    @Autowired
//    private ColumnValueMapBakServiceImpl service;
//
//    @RequestMapping("/delete")
//    @ResponseBody
//    public Object delete() {
//        service.deleteAll();
//        return "success";
//    }
//
//
//    @RequestMapping("/query")
//    @ResponseBody
//    public Object query(@RequestBody QueryRequest query) {
//        TimeInterval ti = new TimeInterval();
//        Page<ColumnValueMapBak> ret = service.query(query);
//        log.info("耗时=" + ti.interval() + "ms");
//        return JSON.toJSONString(ret);
//    }


}
