package com.wen.awenboot.cfg;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 使用properties配置中文需要转ascii码
 *
 * @author wen
 * @version 1.0
 * @date 2020/5/21 14:22
 */
//@PropertySource(value = {"classpath:person.properties"})
//@Component
@ConfigurationProperties(prefix = "person")
@Slf4j
@Data
public class Person {
    private String lastName;

    private int age;

    //    @Value("${person.birth}")
    private String birth;

    @PostConstruct
    private void init() {
        log.info("=======================" + JSON.toJSONString(this));
    }


}
