package com.wen.awenboot.hutool.date;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Calendar;
import java.util.Date;

/**
 * @author wen
 * @version 1.0
 * @date 2020/9/8 14:55
 */
@Slf4j
public class DateMain {
    public static void main(String[] args) {
        //当前时间
        Date date = DateUtil.date();
        //当前时间
        Date date2 = DateUtil.date(Calendar.getInstance());
        //当前时间
        Date date3 = DateUtil.date(System.currentTimeMillis());
        //当前时间字符串，格式：yyyy-MM-dd HH:mm:ss
        String now = DateUtil.now();
        //当前日期字符串，格式：yyyy-MM-dd
        String today = DateUtil.today();

        log.info(date + "," + date2 + "," + date3 + "," + now + "," + today);
        System.out.println();

        test1();
    }

    private static void test1() {
        String dateStr = "2017-03-01";
        Date date = DateUtil.parse(dateStr);
        log.info(date.toString());

        String dateStr2 = "2017-03-01";
        Date date2 = DateUtil.parse(dateStr2, "yyyy-MM-dd");
        log.info(date.toString());
    }
}
