package com.wen.awenboot.msg;

import lombok.Getter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

/**
 * @author wen
 * @version 1.0
 * @date 2021/1/8 14:54
 */
@ToString
public class TagLogEvent extends ApplicationEvent {
    @Getter
    private String address;
    @Getter
    private String test;

    public TagLogEvent(Object source, String address, String test) {
        super(source);
        this.address = address;
        this.test = test;
    }

}
