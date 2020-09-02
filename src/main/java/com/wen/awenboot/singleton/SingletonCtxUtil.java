package com.wen.awenboot.singleton;

import java.util.Map;

/**
 * 简单的单例ioc容器
 *
 * @author wen
 * @version 1.0
 * @date 2020/3/27 10:05
 */
public class SingletonCtxUtil {
    private static SingletonContainer container = new SingletonContainer("com.wen.awenboot");

    public static <T> T getBean(Class<T> clazz) {
        return container.getBean(clazz);
    }

    public static <T> Map<Class<?>, T> getBeansOfType(Class<T> clazz) {
        return container.getBeansOfType(clazz);
    }
}
