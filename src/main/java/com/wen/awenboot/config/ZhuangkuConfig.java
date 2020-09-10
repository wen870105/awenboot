package com.wen.awenboot.config;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author wen
 * @version 1.0
 * @date 2020/5/28 15:40
 */
@Component
@ConfigurationProperties(prefix = "zhuangku")
@Slf4j
@Data
public class ZhuangkuConfig {

    private String exportDir;

    private String dataSourceDir;

    private int rateLimiterQps;

    private String targetUrl;

    private int readFileLimit;

    private int printFlag;

    @PostConstruct
    private void init() {
        log.info("ZhuangkuConfig={}", JSON.toJSONString(this));
    }
}
