/**
 * created by Wen.
 */
package com.wen.awenboot.service;

import com.wen.awenboot.controller.QueryRequest;
import com.wen.awenboot.dao.ColumnValueMapBakMapper;
import com.wen.awenboot.domain.ColumnValueMapBak;
import com.wen.awenboot.domain.base.Page;
import com.wen.awenboot.service.base.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 标签调用明细表,记录每个标签每天访问总量,记录维度为天
 *
 * @author Wen
 * @since 2020-10-23
 */
@Service
@Slf4j
public class ColumnValueMapBakServiceImpl extends BaseServiceImpl<ColumnValueMapBak> {

    @Resource
    private ColumnValueMapBakMapper infoMapper;


    @Override
    public Mapper<ColumnValueMapBak> getDao() {
        return infoMapper;
    }

    public void deleteAll() {
        ColumnValueMapBak query = new ColumnValueMapBak();
        infoMapper.delete(query);
    }

    public Page<ColumnValueMapBak> query(QueryRequest param) {
        ColumnValueMapBak query = new ColumnValueMapBak();
        query.setColumnNum(param.getName());
        query.setPageIndex(param.getPageIndex());
        query.setOffset(param.getOffset());
        return this.selectPage(query);
    }

    public void saveOrUpdateById(ColumnValueMapBak t) {
        ColumnValueMapBak query = new ColumnValueMapBak();
        query.setStringVal(t.getStringVal());
        int i = infoMapper.selectCount(query);
        Date date = new Date();
        if (i == 0) {
            t.setCreateTime(date);
            t.setUpdateTime(date);
            add(t);
        } else {
//            ColumnValueMapBak updateObj = new ColumnValueMapBak();
//            updateObj.setCreateTime(date);
        }

    }
}
