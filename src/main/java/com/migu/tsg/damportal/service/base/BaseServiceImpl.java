/**
 * created by Wen.
 */
package com.migu.tsg.damportal.service.base;

import com.migu.tsg.damportal.domain.base.BaseDomain;
import com.migu.tsg.damportal.domain.base.BaseQuery;
import com.migu.tsg.damportal.domain.base.Page;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 基础方法不建议修改,如需修改请修改对应的子类
 *
 * @author wEn
 * @CreatDate: 2020-10-23
 */
public class BaseServiceImpl<T extends BaseDomain> implements BaseService<T> {

    @Override
    public void add(T t) {
        getDao().insert(t);
    }

    @Override
    public int deleteByCondtion(T t) {
        return getDao().deleteByExample(t);
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
        List<T> l = getDao().select(t);
        return l != null && l.size() > 0 ? l.get(0) : null;
    }

    @Override
    public List<T> selectList(T t) {
        return getDao().selectByExample(t);
    }


    /**
     * 子类重写
     */
    @Override
    public Mapper<T> getDao() {
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

//        int size = getDao().selectListCount(condtion);
//        if (size <= 0) {
//            return page;
//        }
//        page.setTotalCount(size);
//        page.setResult(getDao().selectList(condtion));
        return page;
    }
}