/**
 * created by Wen.
 */
package com.migu.tsg.damportal.domain;

import com.migu.tsg.damportal.domain.base.BaseDomain;

import java.util.Date;

/**
 * 标签信息表,比migu_tag_attribute更详细
 *
 * @author Wen
 * @since 2020-10-23
 */
public class MiguTagInfoQuery extends BaseDomain {
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
    private Date createTimeStart;
    /**
     * 创建结束时间yyyy-MM-dd(时间最大跨度为90天)
     */
    private Date createTimeEnd;
    /**
     * 父节点id,如果为空返回所有数据
     */
    private String parentCode;

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

    public Date getCreateTimeStart() {
        return createTimeStart;
    }

    public void setCreateTimeStart(Date createTimeStart) {
        this.createTimeStart = createTimeStart;
    }

    public Date getCreateTimeEnd() {
        return createTimeEnd;
    }

    public void setCreateTimeEnd(Date createTimeEnd) {
        this.createTimeEnd = createTimeEnd;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }
}