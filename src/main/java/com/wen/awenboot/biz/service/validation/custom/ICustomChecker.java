package com.wen.awenboot.biz.service.validation.custom;

/**
 * 通用自定义校验接口
 *
 * @author wen
 * @version 1.0
 * @date 2020/4/3 19:04
 */
public interface ICustomChecker {
    /**
     * 验证失败返回错误信息
     *
     * @param str
     * @return
     */
    String check(String str);
}
