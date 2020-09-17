package com.wen.awenboot.hutool.test;

import cn.hutool.core.lang.Console;
import cn.hutool.core.util.ReUtil;
import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author wen
 * @version 1.0
 * @date 2020/9/16 21:06
 */
@Slf4j
public class HttpMain {
    public static void main(String[] args) {
        test1();
    }

    private static void test1() {
        String listContent = HttpUtil.get("https://www.mi.com/");
        Console.log(listContent);
//使用正则获取所有标题
        List<String> titles = ReUtil.findAll("<h1 class=\"article-title\">(.*?)</h1>", listContent, 1);
        log.info("end,titles={}", titles.size());
        for (String title : titles) {
            log.info("===" + title);
        }
        log.info("end");
    }
}
