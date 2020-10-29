package com.wen.awenboot.utils;

import lombok.Data;

/**
 * @author wen
 * @version 1.0
 * @date 2020/10/28 17:45
 */
@Data
public class PaasBean {
    private String requestRefId;
    private String secretId;
    private String x_date;
    private String signature;

}
