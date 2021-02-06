package com.wen.awenboot.service;

import com.wen.awenboot.domain.BizUserAccessLog;
import com.wen.awenboot.utils.RedisCli;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wen
 * @version 1.0
 * @date 2021/2/6 23:35
 */

@Data
@Slf4j
public class OpenidServiceImpl {
    private String token;
    private BizUserAccessLog accessLog;

    @Autowired
    private RedisCli redisCli;


    public BizUserAccessLog getAccessLog() {
        if (token == null) {
            log.info("token为空");
            return null;
        }
        accessLog = redisCli.get(token, BizUserAccessLog.class);
        return accessLog;
    }
}
