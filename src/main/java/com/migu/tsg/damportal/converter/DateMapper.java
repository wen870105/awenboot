package com.migu.tsg.damportal.converter;

import cn.hutool.core.date.DateUtil;
import org.mapstruct.Mapper;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wen
 * @version 1.0
 * @date 2020/10/26 1:06
 */
@Mapper(componentModel = "spring")
public class DateMapper {
    public String asString(Date date) {
        return date != null ? new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .format(date) : null;
    }

    public Date asDate(String date) {
        if (date == null) {
            return null;
        }
        return DateUtil.parse(date);

    }

    public static void main(String[] args) {
        String s = "2020-10-26 10:10:10";
        DateMapper dateMapper = new DateMapper();
        System.out.println(dateMapper.asDate(s));
    }
}
