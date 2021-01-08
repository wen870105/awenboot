package com.wen.awenboot.msg;

import org.springframework.context.ApplicationListener;

/**
 * @author wen
 * @version 1.0
 * @date 2021/1/8 14:56
 */
public class TagLogNotifier implements ApplicationListener<TagLogEvent> {

    @Override
    public void onApplicationEvent(TagLogEvent event) {
        System.out.println("应用事件:" + event.toString());
    }
}
