package com.wen.awenboot.service;

import com.wen.awenboot.utils.RedisCli;
import com.wen.awenboot.vo.UserInfoVo;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wen
 * @version 1.0
 * @date 2021/2/6 23:35
 */

@Slf4j
public class OpenidServiceImpl {
    @Setter
    private String token;
    private UserInfoVo userInfo;

    @Autowired
    private RedisCli redisCli;


    public UserInfoVo getUserInfoVo() {
        if (token == null) {
            return null;
        }
        userInfo = redisCli.get(token, UserInfoVo.class);
        return userInfo;
    }

    public String getOpenid() {
        if (userInfo == null) {
            this.userInfo = getUserInfoVo();
        }
        if (userInfo != null) {
            return userInfo.getOpenid();
        }
        return null;
    }
}
