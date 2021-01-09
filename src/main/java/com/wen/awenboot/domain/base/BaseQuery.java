/**
 * created by Wen.
 */
package com.wen.awenboot.domain.base;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


/**
 * 基础组件不建议修改
 *
 * @Author: wEn
 * @CreatDate: 2020-10-23
 * @Version:
 */
public class BaseQuery implements Serializable {
    private static final long serialVersionUID = 1L;

    // 分页起始属性
    @Transient
    private Integer startIndex;
    @Transient
    private Integer offset;
    @Transient
    private Integer pageIndex;
    // 排序字段
    @Transient
    private String orderField;
    // 排序字段类型
    @Transient
    private String orderFieldType;
    // 查询扩展
    @Transient
    private Map<String, Object> queryData;


    public void setStartIndex(Integer startIndex) {
        this.startIndex = startIndex;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public String getOrderField() {
        return orderField;
    }

    public void setOrderField(String orderField) {
        this.orderField = orderField;
    }

    public String getOrderFieldType() {
        if ("DESC".equalsIgnoreCase(orderFieldType) || "ASC".equalsIgnoreCase(orderFieldType)) {
            return orderFieldType.toUpperCase();
        }
        return null;
    }

    public void setOrderFieldType(String orderFieldType) {
        this.orderFieldType = orderFieldType;
    }

    public Map<String, Object> getQueryData() {
        if (queryData != null && queryData.size() > 0) {
            return queryData;
        }
        return null;
    }

    // 添加其它查询数据
    public void addQueryData(String key, Object value) {
        if (queryData == null) {
            queryData = new HashMap<String, Object>();
        }
        queryData.put(key, value);
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getStartIndex() {
        if (getPageIndex() == null || this.offset == null) {
            return null;
        }
        return (getPageIndex() - 1) * this.offset;
    }

}
