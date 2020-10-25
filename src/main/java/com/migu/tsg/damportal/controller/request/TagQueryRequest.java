package com.migu.tsg.damportal.controller.request;

import javax.validation.constraints.NotNull;

/**
 * @author wen
 * @version 1.0
 * @date 2020/10/21 16:27
 */
public class TagQueryRequest {
    /**
     * 标签名称,支持模糊搜索
     */
    private String tagName;
    /**
     * 创建人员名称
     */
    private String creator;
    /**
     * 创建开始时间yyyy-MM-dd(时间最大跨度为90天)
     */
    @NotNull
    private String createDateStart;
    /**
     * 创建结束时间yyyy-MM-dd(时间最大跨度为90天)
     */
    @NotNull
    private String createDateEnd;
    /**
     * 父节点id,如果为空返回所有数据
     */
    private String parentCode;
    /**
     * 分页页数,默认第一页
     */
    private String startIndex;

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreateDateStart() {
        return createDateStart;
    }

    public void setCreateDateStart(String createDateStart) {
        this.createDateStart = createDateStart;
    }

    public String getCreateDateEnd() {
        return createDateEnd;
    }

    public void setCreateDateEnd(String createDateEnd) {
        this.createDateEnd = createDateEnd;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(String startIndex) {
        this.startIndex = startIndex;
    }
}
