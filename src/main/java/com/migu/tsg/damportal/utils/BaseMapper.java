package com.migu.tsg.damportal.utils;

import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 这个接口放在dao目录下会被tkbatis扫描会报错
 *
 * @author wen
 * @version 1.0
 * @date 2020/10/26 16:16
 */
public interface BaseMapper<T> extends Mapper<T> {


    int selectPageCount(Object t);

    List<T> selectPageList(Object t);
}
