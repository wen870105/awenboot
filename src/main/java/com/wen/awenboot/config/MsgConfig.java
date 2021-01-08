package com.wen.awenboot.config;

import com.wen.awenboot.msg.TagLogEventSender;
import com.wen.awenboot.msg.TagLogNotifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wen
 * @version 1.0
 * @date 2021/1/8 14:57
 */
@Configuration
public class MsgConfig {
    @Bean
    public TagLogNotifier getTagLogNotifier() {
        return new TagLogNotifier();
    }

    @Bean
    public TagLogEventSender getTagLogEventSender() {
        return new TagLogEventSender();
    }

}
