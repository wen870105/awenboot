package com.wen.awenboot.singleton;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 无参方法初始化注解
 * @author wen
 * @version 1.0
 * @date 2020/3/28 21:36
 */
@Target({METHOD})
@Retention(RUNTIME)
@Documented
public @interface Init {
}
