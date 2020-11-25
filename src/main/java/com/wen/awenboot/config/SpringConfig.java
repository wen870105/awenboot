package com.wen.awenboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;

/**
 * @author wen
 * @version 1.0
 * @date 2020/5/28 15:40
 */
@Configuration
@EnableAspectJAutoProxy
@Import(cn.hutool.extra.spring.SpringUtil.class)
public class SpringConfig {

    @Autowired
    RestTemplateBuilder builder;

    @Bean
    public RestTemplate restTemplate() {
        return builder.build();
    }
}
