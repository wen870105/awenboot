package com.wen.awenboot.config;

import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;

/**
 * @author wen
 * @version 1.0
 * @date 2020/5/28 15:40
 */
//@Component
//@ConfigurationProperties(prefix = "es")
@Slf4j
//@Data
public class EsConfig {

    private String hosts;

    private String clusterName;

    private String index;

    private String type;

    @PostConstruct
    private void init() {
        log.info(toString());
    }

}
