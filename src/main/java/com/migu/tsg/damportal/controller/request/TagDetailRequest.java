package com.migu.tsg.damportal.controller.request;

import javax.validation.constraints.NotNull;

/**
 * @author wen
 * @version 1.0
 * @date 2020/10/21 16:15
 */
public class TagDetailRequest {
    /**
     * 标签ID
     */
    @NotNull
    private String tagCode;

    public String getTagCode() {
        return tagCode;
    }

    public void setTagCode(String tagCode) {
        this.tagCode = tagCode;
    }
}
