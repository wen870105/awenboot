package com.migu.tsg.damportal.controller.response;

/**
 * @author wen
 * @version 1.0
 * @date 2020/10/25 23:27
 */
public class TagOriginalQueryResponse {
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
     * 标签值, 枚举类型标签才有此值
     */
    private String tagValue;

    /**
     * 父节点标签编码
     */
    private String parentCode;

    /**
     * 父节点标签名称
     */
    private String parentName;

    /**
     * 标签说明
     */
    private String memo;

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

    public String getTagValue() {
        return tagValue;
    }

    public void setTagValue(String tagValue) {
        this.tagValue = tagValue;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }


}
