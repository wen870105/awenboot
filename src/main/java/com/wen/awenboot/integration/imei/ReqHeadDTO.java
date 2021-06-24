package com.wen.awenboot.integration.imei;

import lombok.Data;

/**
 * @author wen
 * @version 1.0
 * @date 2021/6/23 14:06
 */
@Data
public class ReqHeadDTO {
    private String requestRefId;
    private String secretId;
    private String signature;
}
