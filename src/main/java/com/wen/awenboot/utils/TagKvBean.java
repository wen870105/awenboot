package com.wen.awenboot.utils;

import lombok.Data;

/**
 * @author wen
 * @version 1.0
 * @date 2020/12/1 17:21
 */
@Data
public class TagKvBean {
    // 标签名称
    private String name;
    // 标签运算符,大于 等于 小于
    private String op;
    // 标签值
    private String val;

    @Override
    public String toString() {
        return "{" + name + " " + op + " " + val + "}";
    }
}
