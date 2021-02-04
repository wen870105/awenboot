package com.wen.awenboot.controller.response;

import lombok.Data;

/**
 * @author wen
 * @version 1.0
 * @date 2021/1/23 21:08
 */
@Data
public class Openid {
    private String  session_key;
    private Integer expires_in;
    private String openid;
}
