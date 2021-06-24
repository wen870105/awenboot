package com.wen.awenboot.config;

import com.codahale.metrics.MetricRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedList;

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

    /**
     * 存正在解析的文件名称
     *
     * @return
     */
    @Bean
    public LinkedList<String> getExportFileList() {
        return new LinkedList<String>();
    }

    @Bean
    public MetricRegistry metrics() {
        return new MetricRegistry();
    }
}
