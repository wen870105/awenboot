/**
 * created by Wen.
 */
package com.migu.tsg.damportal.service;

import com.migu.tsg.damportal.dao.MiguTagEnumMapper;
import com.migu.tsg.damportal.domain.MiguTagEnum;
import com.migu.tsg.damportal.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import javax.annotation.Resource;
import java.util.List;

/**
 * 标签调用明细表,记录每个标签每天访问总量,记录维度为天
 *
 * @author Wen
 * @since 2020-10-23
 */
@Service
public class MiguTagEnumServiceImpl extends BaseServiceImpl<MiguTagEnum> {

    @Resource
    private MiguTagEnumMapper attrMapper;

    @Override
    public Mapper<MiguTagEnum> getDao() {
        return attrMapper;
    }


    public List<MiguTagEnum> getAll() {
        return attrMapper.selectAll();
    }

}
