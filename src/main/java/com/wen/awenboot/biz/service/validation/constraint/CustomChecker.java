package com.wen.awenboot.biz.service.validation.constraint;

import com.wen.awenboot.biz.service.validation.custom.ICustomChecker;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义
 *
 * @author wen
 * @version 1.0
 * @date 2020/4/3 19:05
 */
@Constraint(validatedBy = CustomCheckerImpl.class)
@Target(value = {ElementType.PARAMETER, ElementType.FIELD})
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public @interface CustomChecker {

    /**
     * 自定义生效的校验
     *
     * @return
     */
    Class<? extends ICustomChecker>[] classes() default {};

    /**
     * 验证不通过时的错误提示信息
     *
     * @return
     */
    String message() default "";

    Class<?>[] groups() default {};

    /**
     * 变量名称 payload不可变
     * 否则会抛出异常`javax.validation.ConstraintDefinitionException: HV000074`
     *
     * @return
     */
    Class<? extends Payload>[] payload() default {};
}