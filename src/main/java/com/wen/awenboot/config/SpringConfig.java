package com.wen.awenboot.config;

import com.wen.awenboot.service.OpenidServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author wen
 * @version 1.0
 * @date 2020/5/28 15:40
 */
@Configuration
@EnableAspectJAutoProxy
@Import(cn.hutool.extra.spring.SpringUtil.class)
public class SpringConfig {

    @Bean
    @Lazy
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public OpenidServiceImpl openidService() {
        return new OpenidServiceImpl();
    }
}
