package com.wen.awenboot.hutool.test.bean;

import lombok.Data;

/**
 * @author wen
 * @version 1.0
 * @date 2020/9/9 20:20
 */
@Data
public class Holder<T> {
    private T data;
}
