/**
 * created by Wen.
 */
package com.migu.tsg.damportal.service;

import com.migu.tsg.damportal.dao.MiguTagAttributeMapper;
import com.migu.tsg.damportal.domain.MiguTagAttribute;
import com.migu.tsg.damportal.service.base.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class MiguTagAttributeServiceImpl extends BaseServiceImpl<MiguTagAttribute> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MiguTagAttributeServiceImpl.class);

    @Resource
    private MiguTagAttributeMapper attrMapper;

    @Override
    public Mapper<MiguTagAttribute> getDao() {
        return attrMapper;
    }


    public MiguTagAttribute getAttrByCode(String code) {
        return attrMapper.selectByPrimaryKey(code);
    }

    public List<MiguTagAttribute> getAll() {
        return attrMapper.selectAll();
    }

}
