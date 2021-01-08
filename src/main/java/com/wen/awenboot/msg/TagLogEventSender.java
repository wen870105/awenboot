package com.wen.awenboot.msg;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

/**
 * @author wen
 * @version 1.0
 * @date 2021/1/8 14:56
 */
public class TagLogEventSender implements ApplicationEventPublisherAware {
    private ApplicationEventPublisher publisher;


    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

    public void send(String address, String test) {
        TagLogEvent event = new TagLogEvent(this, address, test);
        publisher.publishEvent(event);
        return;
    }
}
