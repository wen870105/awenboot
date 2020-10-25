package com.migu.tsg.damportal.controller.request;

import javax.validation.constraints.NotNull;

/**
 * @author wen
 * @version 1.0
 * @date 2020/10/21 16:15
 */
public class TagOriginalQueryRequest {
    /**
     * 标签ID,此ID必须是hbase标签维表已定义的id(hbase标签维表由标签开发人员提供)
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
