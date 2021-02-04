/**
 * created by Wen.
 */
package com.wen.awenboot.dao;

import com.wen.awenboot.domain.BizWechatPwd;
import tk.mybatis.mapper.common.Mapper;


/**
 * 小程序访问密码表,每个授权码只能使用一次
 * @author Wen
 * @since 2021-01-23
 */
public interface BizWechatPwdMapper extends Mapper<BizWechatPwd> {

}