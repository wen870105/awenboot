package com.wen.awenboot.integration.log;

import lombok.Data;

/**
 * @author wen
 * @version 1.0
 * @date 2021/1/3 2:02
 */
@Data
public class GotoneLogRequest {
    private String requestRefId;
    private String phone;
    private String secretId;
}
