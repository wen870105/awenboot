package com.wen.awenboot.config;

/**
 * @author wen
 * @version 1.0
 * @date 2021/1/8 14:06
 */

import org.springframework.context.annotation.Configuration;

@Configuration
@tk.mybatis.spring.annotation.MapperScan(value = "com.wen.awenboot.dao")
public class DalConfig {
}
