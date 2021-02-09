/**
 * created by Wen.
 */
package com.wen.awenboot.service;

import com.wen.awenboot.dao.BizBlogVistorCounterMapper;
import com.wen.awenboot.domain.BizBlogVistorCounter;
import com.wen.awenboot.service.base.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
public class BizBlogVistorCounterServiceImpl extends BaseServiceImpl<BizBlogVistorCounter> {

    @Resource
    private BizBlogVistorCounterMapper mapper;


    @Override
    public Mapper<BizBlogVistorCounter> getDao() {
        return mapper;
    }

    @Transactional
    public int incrementAndGet(int blogId) {
        BizBlogVistorCounter vc = new BizBlogVistorCounter();
        vc.setBlogId(blogId);
        BizBlogVistorCounter obj = this.selectOne(vc);
        if (obj == null) {
            vc.setCnt(1);
            add(vc);
        } else {
            mapper.incrementCnt(vc);
        }
        obj = this.selectOne(vc);
        return obj.getCnt();
    }

    public int getCnt(int blogId) {
        BizBlogVistorCounter vc = new BizBlogVistorCounter();
        vc.setBlogId(blogId);
        BizBlogVistorCounter obj = this.selectOne(vc);
        return obj == null ? 0 : obj.getCnt();
    }
}
