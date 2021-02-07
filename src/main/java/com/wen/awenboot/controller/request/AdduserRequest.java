package com.wen.awenboot.controller.request;

import lombok.Data;

/**
 * @author wen
 * @version 1.0
 * @date 2021/2/7 14:33
 */
@Data
public class AdduserRequest {
    // 手机号
    private String phoneNum;
    // 头像图片
    private String headerImg;
    //
    private String name;
    // 备注
    private String memo;
}
