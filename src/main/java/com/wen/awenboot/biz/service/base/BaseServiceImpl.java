/**
 * LY.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.wen.awenboot.biz.service.base;


import com.wen.awenboot.domain.base.BaseDomain;
import com.wen.awenboot.domain.base.BaseQuery;
import com.wen.awenboot.domain.base.Page;
import com.wen.awenboot.utils.BaseMapper;

import java.util.List;

/**
 * 基础方法不建议修改,如需修改请修改对应的子类
 *
 * @author wEn
 * @CreatDate: 2020-05-25
 */
public class BaseServiceImpl<T extends BaseDomain> implements BaseService<T> {


    @Override
    public void add(T t) {
        getDao().insert(t);
    }


    @Override
    public int deleteByCondtion(T t) {
        return getDao().delete(t);
    }

    @Override
    public int updateById(T t) {
        return getDao().updateByPrimaryKey(t);
    }

    @Override
    public T selectById(long id) {
        return getDao().selectByPrimaryKey(id);
    }

    @Override
    public T selectOne(T t) {
        t.setStartIndex(0);
        t.setOffset(1);
        List<T> l = selectList(t);
        return l != null && l.size() > 0 ? l.get(0) : null;
    }

    @Override
    public List<T> selectList(T t) {
        return getDao().selectPageList(t);
    }

    @Override
    public int selectListCount(T t) {
        return getDao().selectPageCount(t);
    }

    /**
     * 子类重写
     */
    @Override
    public BaseMapper<T> getDao() {
        throw new RuntimeException("当前Service的BaseDao为空");
    }

    @Override
    public Page<T> selectPage(T condtion, Page<T> page) {
        if (condtion instanceof BaseQuery) {
            BaseQuery bq = condtion;
            bq.setStartIndex(page.getStartIndex());
            bq.setOffset(page.getPageSize());
        } else {
            throw new IllegalArgumentException("设置分页参数失败,参数不是BaseQuery的子类");
        }

        int size = selectListCount(condtion);
        if (size <= 0) {
            return page;
        }
        page.setTotalCount(size);
        page.setResult(selectList(condtion));
        return page;
    }
}