package com.wen.awenboot.controller.request;

import lombok.Data;

/**
 * @author wen
 * @version 1.0
 * @date 2021/2/5 17:04
 */
@Data
public class ListRequest {
    private int pageIndex = 1;

    private int pageSize = 10;
}
