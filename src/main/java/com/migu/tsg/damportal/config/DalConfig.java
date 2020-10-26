package com.migu.tsg.damportal.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author wen
 * @version 1.0
 * @date 2020/10/23 18:47
 */
@Configuration
@tk.mybatis.spring.annotation.MapperScan("com.migu.tsg.damportal.dao")
@EnableTransactionManagement
public class DalConfig {
}
