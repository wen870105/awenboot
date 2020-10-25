/**
 * created by Wen.
 */
package com.migu.tsg.damportal.dao;

import com.migu.tsg.damportal.domain.MiguTagDetailCnt;
import tk.mybatis.mapper.common.Mapper;


/**
 * 标签调用明细表,记录每个标签每天访问总量,记录维度为天
 *
 * @author Wen
 * @since 2020-10-23
 */
public interface MiguTagDetailCntMapper extends Mapper<MiguTagDetailCnt> {
    int updateIncrementCntById(MiguTagDetailCnt obj);
}