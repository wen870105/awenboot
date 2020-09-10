/**
 * LY.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.wen.awenboot.biz.service.validation.validator;

import java.util.Map;

/**
 * 普通属性使用hibernate-validator特性验证
 * 嵌套的需要自己取出相关属性
 *
 * @author wsy48420
 * @version $Id: IValidator.java, v 0.1 2017年8月23日 下午5:54:28 wsy48420 Exp $
 */
public interface IValidator<T> {

    /**
     * @param obj
     * @return
     */
    Map<String, String> validate(T obj);

    Class<?> getValidatorKey();
}
