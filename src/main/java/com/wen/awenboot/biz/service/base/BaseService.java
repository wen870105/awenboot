/**
 * LY.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.wen.awenboot.biz.service.base;

import com.wen.awenboot.dal.dao.base.BaseDao;
import com.wen.awenboot.dal.dataobject.base.Page;

import java.util.List;


/**
 * 基础方法不建议修改,如需修改请修改对应的子类
 *
 * @author wEn
 * @CreatDate: 2020-05-25
 */
public interface BaseService<T> {

    public void add(T t);

    public int deleteByIds(long[] ids);

    public int deleteByCondtion(T t);

    public int updateById(T t);

    public T selectById(long id);

    public T selectOne(T t);

    public List<T> selectList(T t);

    public int selectListCount(T t);

    public List<T> selectByIds(long[] ids);

    public BaseDao<T> getDao();

    public Page<T> selectPage(T condition, Page<T> page);
}
