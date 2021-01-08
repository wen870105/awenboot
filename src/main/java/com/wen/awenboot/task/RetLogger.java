package com.wen.awenboot.task;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

/**
 * @author wen
 * @version 1.0
 * @date 2021/1/8 11:03
 */
@Slf4j
@Service
public class RetLogger {
    public Logger getLogger() {
        return log;
    }
}
