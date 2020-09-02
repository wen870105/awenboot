package com.wen.awenboot.test.aop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author wen
 * @version 1.0
 * @date 2020/5/27 15:28
 */
@Service
@Slf4j
public class BizService {
    public void test() {
        log.info("test");
    }
}
