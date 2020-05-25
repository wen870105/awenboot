package com.wen.awenboot.domain;

/**
 * @author wen
 * @version 1.0
 * @date 2020/5/20 10:55
 */
public class Greeting {
    private final long id;
    private final String content;

    public Greeting(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
