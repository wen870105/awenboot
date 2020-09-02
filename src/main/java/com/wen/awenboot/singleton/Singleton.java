package com.wen.awenboot.singleton;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 附带初始化功能,目前只支持无参数构造方法初始化
 *
 * @author wen
 * @date 2020年3月26日
 */
@Target({ElementType.TYPE})
@Retention(RUNTIME)
@Inherited
@Documented
public @interface Singleton {
}
