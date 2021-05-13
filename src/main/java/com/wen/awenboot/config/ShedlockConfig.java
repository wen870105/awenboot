package com.wen.awenboot.config;

import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.provider.jdbctemplate.JdbcTemplateLockProvider;
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.TimeZone;

/**
 * @author wen
 * @version 1.0
 * @date 2021/5/12 14:09
 */
@Configuration
@EnableSchedulerLock(defaultLockAtMostFor = "10m")
public class ShedlockConfig {
    @Resource
    private DataSource dataSource;

    /**
     * @description
     * CREATE TABLE shedlock (
     * NAME VARCHAR ( 64 ) NOT NULL,
     * lock_until TIMESTAMP ( 3 ) NOT NULL,
     * locked_at TIMESTAMP ( 3 ) NOT NULL DEFAULT CURRENT_TIMESTAMP ( 3 ),
     * locked_by VARCHAR ( 255 ) NOT NULL,
     * PRIMARY KEY ( NAME )
     * );
     * @date 2020/6/1 15:19
     */
    @Bean
    public LockProvider lockProvider() {
        return new JdbcTemplateLockProvider(
                JdbcTemplateLockProvider.Configuration.builder()
                        .withJdbcTemplate(new JdbcTemplate(dataSource))
                        .withTimeZone(TimeZone.getTimeZone("GMT+8"))
                        .build()
        );
    }
}
