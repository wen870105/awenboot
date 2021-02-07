package com.wen.awenboot.vo;

import com.wen.awenboot.domain.BizWechatPwd;
import lombok.Data;

/**
 * @author wen
 * @version 1.0
 * @date 2021/2/7 22:51
 */
@Data
public class UserInfoVo {
    // 手机号
    private String phoneNum;
    // 头像图片
    private String headerImg;
    // 创建人员账号
    private String name;
    // OPENID
    private String openid;
    // 备注
    private String memo;
    // session_key
    private String sessionKey;

    private Integer expiresIn;

    private BizWechatPwd bizWechatPwd;
}
