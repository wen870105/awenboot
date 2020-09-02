package com.wen.awenboot.singleton;

import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 单例容器,非spring环境下使用,用class获取
 * 只有简单的注入和初始化功能,只能根据class映射
 * 支持循环引用,支持属性和方法注入,只支持父类的方法注入
 * 部分逻辑参考和命名借鉴了springioc
 *
 * @author wen
 * @date 2020年3月26日
 */
public class SingletonContainer {

    private static final Logger LOGGER = LoggerFactory.getLogger(SingletonContainer.class);

    private Reflections reflections;

    private final Map<Class<?>, Object> singletonObjects = new ConcurrentHashMap<>(128);

    private final Map<Class<?>, Object> earlySingletonObjects = new HashMap<>(16);

    private final Set<Class<?>> singletonsCurrentlyInCreation = new HashSet<>(16);

    private List<EventListener> listeners = new ArrayList<>();

    public SingletonContainer(String path) {
        // String path = "com.migu.sgw.comm.toolkit.singleton";
        reflections = new Reflections(path);
        init();
    }

    private void init() {
        Set<Class<?>> ret = reflections.getTypesAnnotatedWith(Singleton.class);
        ret.forEach(clazz -> {
            if (!clazz.isInterface() && !Modifier.isAbstract(clazz.getModifiers())) {
                getBean2(clazz);
            }
        });
        registerEventListener();
        multicastEvent();
        LOGGER.info("初始化[单例容器]组件:数量={},keys={}", singletonObjects.size(), singletonObjects.keySet().toString());
    }

    /**
     * 注册监听
     */
    private void registerEventListener() {
        Map<Class<?>, EventListener> listeners = getBeansOfType(EventListener.class);
        this.listeners.addAll(listeners.values());
        LOGGER.info("注册消息监听:数量={},keys={}", this.listeners.size(), this.listeners.toString());
    }

    /**
     * 发送广播,目前发送的无消息体
     */
    private void multicastEvent() {
        for (EventListener listener : listeners) {
            listener.onEvent(this);
        }
    }

    private <T> T getBean2(Class<T> clazz) {

        if (!clazz.isAnnotationPresent(Singleton.class)) {
            throw new RuntimeException(clazz + ",未被Singleton给注解");
        }
        Object singleton = getSingleton(clazz);
        if (singleton == null) {
            Object obj = createInstance(clazz);
            singletonsCurrentlyInCreation.add(clazz);
            earlySingletonObjects.put(clazz, obj);
            for (Field field : obj.getClass().getDeclaredFields()) {
                if (field.isAnnotationPresent(Inject.class)) {
                    Class<?> fClazz = field.getType();
                    field.setAccessible(true);
                    try {
                        field.set(obj, getBean2(fClazz));
                    } catch (IllegalAccessException e) {
                        LOGGER.error("单例容器注入异常", e);
                        throw new RuntimeException("单例容器注入异常", e);
                    }
                }
            }
            for (Method method : obj.getClass().getDeclaredMethods()) {
                initMethod(clazz, obj, method);
                methodInject(clazz, obj, method);
            }

            for (Method method : obj.getClass().getMethods()) {
                methodInject(clazz, obj, method);
            }
            singletonsCurrentlyInCreation.remove(clazz);
            earlySingletonObjects.remove(clazz);
            singletonObjects.put(clazz, obj);
            singleton = obj;
        }
        return (T) singleton;
    }

    private <T> void methodInject(Class<T> clazz, Object obj, Method method) {
        if (method.isAnnotationPresent(Inject.class)) {
            Class<?>[] parameterTypes = method.getParameterTypes();
            if (parameterTypes.length == 0) {
                return;
            }
            Object[] params = new Object[parameterTypes.length];
            for (int i = 0; i < parameterTypes.length; i++) {
                params[i] = getBean2(parameterTypes[i]);
            }
            method.setAccessible(true);
            try {
                method.invoke(obj, params);
            } catch (Exception e) {
                LOGGER.error("单例容器执行初始化方法异常,method=" + clazz.getName() + "." + method.getName(), e);
                throw new RuntimeException("单例容器执行初始化方法异常,method=" + clazz.getName() + "." + method.getName(), e);
            }
        }
    }

    private <T> void initMethod(Class<T> clazz, Object obj, Method method) {
        if (method.isAnnotationPresent(Init.class)) {
            method.setAccessible(true);
            try {
                method.invoke(obj, new Object[]{});
            } catch (Exception e) {
                LOGGER.error("单例容器执行初始化方法异常,method=" + clazz.getName() + "." + method.getName(), e);
                throw new RuntimeException("单例容器执行初始化方法异常,method=" + clazz.getName() + "." + method.getName(), e);
            }
        }
    }

    public static void main(String[] args) {
        SingletonContainer ctx = new SingletonContainer("com.migu.sgw");
//        BizAnalysisService bean = ctx.getBean(BizAnalysisService.class);
//        bean.getClass();
//        BizAnalysisDao bean2 = ctx.getBean(BizAnalysisDao.class);
//        bean2.getClass();

        System.out.print(111);
    }

    private Object getSingleton(Class<?> beanName) {
        Object singletonObject = this.singletonObjects.get(beanName);
        if (singletonObject == null && this.singletonsCurrentlyInCreation.contains(beanName)) {
            synchronized (this.singletonObjects) {
                singletonObject = this.earlySingletonObjects.get(beanName);
            }
        }
        return singletonObject;
    }


    private <T> T createInstance(Class<T> clazz) {
        try {
            T ret = clazz.newInstance();
            return ret;
        } catch (Exception e) {
            LOGGER.error("创建异常" + clazz.getName(), e);
            throw new RuntimeException("创建异常" + clazz.getName(), e);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> clazz) {
        T obj = (T) singletonObjects.get(clazz);
        if (obj == null) {
            LOGGER.error("未注册类,{}", clazz.toString());
            throw new RuntimeException("未注册类," + clazz.toString());
        }
        return obj;
    }

    @SuppressWarnings("unchecked")
    public <T> Map<Class<?>, T> getBeansOfType(Class<T> clazz) {
        Map<Class<?>, T> result = new LinkedHashMap<>();
        singletonObjects.keySet().forEach(c -> {
            Object obj = singletonObjects.get(c);
            if (obj != null && clazz.isAssignableFrom(obj.getClass())) {
                result.put(c, (T) obj);
            }
        });
        return result;
    }
}
