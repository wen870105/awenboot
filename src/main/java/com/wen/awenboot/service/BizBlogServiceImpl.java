/**
 * created by Wen.
 */
package com.wen.awenboot.service;

import com.wen.awenboot.dao.BizBlogMapper;
import com.wen.awenboot.domain.BizBlog;
import com.wen.awenboot.service.base.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import javax.annotation.Resource;

/**
 * 标签调用明细表,记录每个标签每天访问总量,记录维度为天
 *
 * @author Wen
 * @since 2020-10-23
 */
@Service
@Slf4j
public class BizBlogServiceImpl extends BaseServiceImpl<BizBlog> {

    @Resource
    private BizBlogMapper infoMapper;


    @Override
    public Mapper<BizBlog> getDao() {
        return infoMapper;
    }

}
