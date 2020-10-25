/**
 * created by Wen.
 */
package com.migu.tsg.damportal.service.base;

import com.migu.tsg.damportal.domain.base.Page;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


/**
 * 基础方法不建议修改,如需修改请修改对应的子类
 *
 * @author wEn
 * @CreatDate: 2020-10-23
 */
public interface BaseService<T> {

    public void add(T t);

    public int deleteByCondtion(T t);

    public int updateById(T t);

    public T selectById(long id);

    public T selectOne(T t);

    public List<T> selectList(T t);

    /**
     * 子类重写
     */
    public Mapper<T> getDao();

    public Page<T> selectPage(T condtion, Page<T> page);
}
