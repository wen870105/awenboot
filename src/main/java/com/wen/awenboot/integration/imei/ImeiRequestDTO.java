package com.wen.awenboot.integration.imei;

import lombok.Data;

/**
 * @author wen
 * @version 1.0
 * @date 2021/6/23 14:04
 */
@Data
public class ImeiRequestDTO {
    private ReqHeadDTO head;
    private String request;
}
