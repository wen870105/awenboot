/**
 * created by Wen.
 */
package com.migu.tsg.damportal.domain;

import com.migu.tsg.damportal.domain.base.BaseDomain;

import javax.persistence.Id;
import java.util.Date;

/**
 * 标签调用明细表,记录每个标签每天访问总量,记录维度为天
 *
 * @author Wen
 * @since 2020-10-23
 */
public class MiguTagDetailCnt extends BaseDomain {
    //
    @Id
    private String id;
    // 标签id
    private String tagCode;
    // 调用次数
    private Long tagCnt;
    //
    private String createDate;
    //
    private Date updateTime;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setTagCode(String tagCode) {
        this.tagCode = tagCode;
    }

    public String getTagCode() {
        return tagCode;
    }

    public void setTagCnt(Long tagCnt) {
        this.tagCnt = tagCnt;
    }

    public Long getTagCnt() {
        return tagCnt;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }
}