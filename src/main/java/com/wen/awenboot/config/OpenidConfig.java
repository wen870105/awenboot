package com.wen.awenboot.config;

import com.wen.awenboot.interceptor.OpenidInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.annotation.Resource;

/**
 * @author wen
 * @version 1.0
 * @date 2021/2/6 23:33
 */
@Configuration
public class OpenidConfig extends WebMvcConfigurationSupport {
    @Resource
    OpenidInterceptor interceptor;

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor).addPathPatterns("/wechat/addpwd","/wechat/detail","/wechat/list");

    }
}
