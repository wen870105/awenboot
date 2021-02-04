package com.wen.awenboot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.concurrent.atomic.AtomicLong;

/**
 *
 */
@Slf4j
@Controller
public class IndexController {

    private AtomicLong id = new AtomicLong(0L);

    @RequestMapping(value = {"/index", ""})
    public String index() {
        log.info("首页访问次数={}", id.incrementAndGet());
        return "index";
    }

}
