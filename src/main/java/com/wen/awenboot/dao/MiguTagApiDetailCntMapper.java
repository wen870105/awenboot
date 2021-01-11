/**
 * created by Wen.
 */
package com.wen.awenboot.dao;

import com.wen.awenboot.domain.MiguTagApiDetailCnt;
import com.wen.awenboot.utils.BaseMapper;


/**
 * @author Wen
 * @since 2020-10-23
 */
public interface MiguTagApiDetailCntMapper extends BaseMapper<MiguTagApiDetailCnt> {
    boolean updateIncrementCntById(MiguTagApiDetailCnt obj);
}