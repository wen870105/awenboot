/**
 * LY.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.wen.awenboot.dal.dao.base;

import java.util.List;

/**
 * 基础方法不建议修改,如需修改请修改对应的子类
 * @author wEn
 * @CreatDate: 2020-05-25 
 */
public interface BaseDao<T> {

	public void add(T t);
	
	public int deleteByIds(long[] ids);
	
	public int deleteByCondtion(T t);
	
	public int updateById(T t);
	
	public T selectById(long id);

	public List<T> selectList(T t);

	public int selectListCount(T t) ;
	
	public List<T> selectByIds(long[] ids) ;
}
