/**
 * created by Wen.
 */
package com.wen.awenboot.dao;

import com.wen.awenboot.domain.BizBlogVistorCounter;
import com.wen.awenboot.utils.BaseMapper;


/**
 * 课程内容
 *
 * @author Wen
 * @since 2021-02-05
 */
public interface BizBlogVistorCounterMapper extends BaseMapper<BizBlogVistorCounter> {
    int incrementCnt(BizBlogVistorCounter obj);
}