package com.wen.awenboot.singleton;

/**
 * 消息监听,用来容器初始化完毕后执行的方法
 *
 * @author wen
 * @version 1.0
 * @date 2020/3/28 21:36
 */
public interface EventListener {
    /**
     * 消息监听
     * @param ctx
     */
    void onEvent(SingletonContainer ctx);
}
