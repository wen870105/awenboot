package com.wen.awenboot.integration.imei;

import lombok.Data;

/**
 * @author wen
 * @version 1.0
 * @date 2021/4/6 16:03
 */
@Data
public class ResponseHeadDTO {
    /**
     * requestRefId : SJSREQ_201601010809108632A
     * responseCode : 0000
     * responseMsg : 查询成功
     */

    private String requestRefId;
    private String result;
    private String responseCode;
    private String responseMsg;

}
