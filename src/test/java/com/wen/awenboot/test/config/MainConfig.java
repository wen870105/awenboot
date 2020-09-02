package com.wen.awenboot.test.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author wen
 * @version 1.0
 * @date 2020/5/26 20:11
 */
@EnableAspectJAutoProxy
@Configuration
@ComponentScan(value = "com.wen.awenboot.test")
public class MainConfig {
}
