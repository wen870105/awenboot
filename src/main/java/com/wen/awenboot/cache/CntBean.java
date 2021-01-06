package com.wen.awenboot.cache;

import lombok.Data;

import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author wen
 * @version 1.0
 * @date 2021/1/3 19:48
 */
@Data
public class CntBean {
    private String key;
    private AtomicLong val = new AtomicLong(0L);
    private Date updateDate;
}
