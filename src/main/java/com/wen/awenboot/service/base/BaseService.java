/**
 * created by Wen.
 */
package com.wen.awenboot.service.base;

import com.wen.awenboot.domain.base.Page;
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

    public T selectById(Object id);

    public T selectOne(T t);

    public List<T> selectByExample(Object t);

    /**
     * 子类重写
     */
    public Mapper<T> getDao();

    public Page<T> selectPage(Object condition);
}
