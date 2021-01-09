package com.wen.awenboot.controller;

import lombok.Data;

/**
 * @author wen
 * @version 1.0
 * @date 2021/1/8 16:59
 */
@Data
public class QueryRequest {
    private String name;

    private Integer offset=50;
    private Integer pageIndex=1;
}
