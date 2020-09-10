package com.wen.awenboot.biz.service.validation.constraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author wen
 * @version 1.0
 * @date 2020/4/3 19:05
 */
@Constraint(validatedBy = DatetimeCheckerImpl.class)
@Target(value = {ElementType.PARAMETER, ElementType.FIELD})
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public @interface DatetimeChecker {

    /**
     * 验证格式,date=yyyy-MM-dd,hm=HH:mm
     *
     * @return
     */
    String type() default "date";

    /**
     * 验证不通过时的错误提示信息
     *
     * @return
     */
    String message() default "格式问题";

    Class<?>[] groups() default {};

    /**
     * 变量名称 payload不可变
     * 否则会抛出异常`javax.validation.ConstraintDefinitionException: HV000074`
     *
     * @return
     */
    Class<? extends Payload>[] payload() default {};
}