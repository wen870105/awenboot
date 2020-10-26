package com.migu.tsg.damportal.controller.request;

/**
 * @author wen
 * @version 1.0
 * @date 2020/10/26 18:28
 */
public class TagUpdateStatusRequest {
    /**
     * 标签ID
     */
    private String tagCode;

    /**
     * 修改后状态
     */
    private Integer status;

    public String getTagCode() {
        return tagCode;
    }

    public void setTagCode(String tagCode) {
        this.tagCode = tagCode;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
