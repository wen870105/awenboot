package com.wen.awenboot.singleton;

/**
 * 附带初始化功能,目前只支持无参数构造方法初始化
 *
 * @author wen
 * @date 2020年3月26日
 */

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, CONSTRUCTOR, FIELD})
@Retention(RUNTIME)
@Inherited
@Documented
public @interface Inject {
}
