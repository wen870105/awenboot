package com.migu.tsg.damportal.controller.response;

/**
 * @author wen
 * @version 1.0
 * @date 2020/10/21 16:39
 */
public class TagTreeResponse {
    /**
     * 标签编码
     */
    private String tagCode;

    /**
     * 标签名称
     */
    private String tagName;

    /**
     * (1枚举,0非枚举)
     */
    private Integer tagType;

    /**
     * 父节点ID
     */
    private String parentCode;

    public String getTagCode() {
        return tagCode;
    }

    public void setTagCode(String tagCode) {
        this.tagCode = tagCode;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Integer getTagType() {
        return tagType;
    }

    public void setTagType(Integer tagType) {
        this.tagType = tagType;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }
}
