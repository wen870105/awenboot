/**
 * created by Wen.
 */
package com.wen.awenboot.dao;

import com.wen.awenboot.domain.ColumnValueMapBak;
import com.wen.awenboot.utils.BaseMapper;

import java.util.List;


/**
 * @author Wen
 * @since 2020-10-23
 */
public interface ColumnValueMapBakMapper extends BaseMapper<ColumnValueMapBak> {
    List<ColumnValueMapBak> selectPageList(ColumnValueMapBak t);

    int selectPageCount(ColumnValueMapBak t);
}