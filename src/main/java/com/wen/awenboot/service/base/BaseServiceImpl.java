/**
 * created by Wen.
 */
package com.wen.awenboot.service.base;

import com.wen.awenboot.domain.base.BaseDomain;
import com.wen.awenboot.domain.base.BaseQuery;
import com.wen.awenboot.domain.base.Page;
import com.wen.awenboot.utils.BaseMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

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
        Example example = new Example(t.getClass());
        return getDao().updateByPrimaryKeySelective(t);
    }

    @Override
    public T selectById(Object id) {
        return getDao().selectByPrimaryKey(id);
    }

    @Override
    public T selectOne(T t) {
        List<T> l = getDao().select(t);
        return l != null && l.size() > 0 ? l.get(0) : null;
    }

    @Override
    public List<T> selectByExample(Object t) {
//        getDao.se
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
    public Page<T> selectPage(Object condition) {
        Page<T> pager = new Page<>();
        if (condition instanceof BaseQuery) {
            BaseQuery bq = (BaseQuery) condition;
            pager.setPageSize(bq.getOffset());
            pager.setCurrentPage(bq.getPageIndex());
        } else {
            throw new IllegalArgumentException("设置分页参数失败,参数不是BaseQuery的子类");
        }
        if (getDao() instanceof BaseMapper) {
            BaseMapper baseMapper = (BaseMapper) getDao();
            int size = baseMapper.selectPageCount(condition);
            if (size <= 0) {
                return pager;
            }
            pager.setTotalCount(size);
            pager.setResult(baseMapper.selectPageList(condition));
        }
        return pager;
    }
}