package com.wen.awenboot.config;

import com.wen.awenboot.interceptor.OpenidInterceptor;
import com.wen.awenboot.interceptor.SetTokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @author wen
 * @version 1.0
 * @date 2021/2/6 23:33
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Resource
    OpenidInterceptor openidInterceptor;
    @Resource
    SetTokenInterceptor tokenInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor).addPathPatterns("/wechat/**");
        registry.addInterceptor(openidInterceptor).addPathPatterns("/wechat/addpwd", "/wechat/adduser");
    }
}
