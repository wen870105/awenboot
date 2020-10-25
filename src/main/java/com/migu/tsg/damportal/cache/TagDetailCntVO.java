package com.migu.tsg.damportal.cache;

import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author wen
 * @version 1.0
 * @date 2020/10/23 13:59
 */
public class TagDetailCntVO {
    private String tagCode;
    private AtomicLong tagCnt = new AtomicLong(0L);
    // 写入数据库日期
    private Date toDbDate;
    // 更新日期
    private Date updateDate;
    // 缓存日期
    private Date cacheDate;

    public String getTagCode() {
        return tagCode;
    }

    public void setTagCode(String tagCode) {
        this.tagCode = tagCode;
    }

    public AtomicLong getTagCnt() {
        return tagCnt;
    }

    public void setTagCnt(AtomicLong tagCnt) {
        this.tagCnt = tagCnt;
    }

    public Date getToDbDate() {
        return toDbDate;
    }

    public void setToDbDate(Date toDbDate) {
        this.toDbDate = toDbDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getCacheDate() {
        return cacheDate;
    }

    public void setCacheDate(Date cacheDate) {
        this.cacheDate = cacheDate;
    }
}
