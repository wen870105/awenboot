package com.wen.awenboot.config;

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
@ConfigurationProperties(prefix = "mala")
@Slf4j
@Data
public class MalaConfig {
    private String appid;
    private String secret;

    @PostConstruct
    private void init() {
        log.info(toString());
    }

}
