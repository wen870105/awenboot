package com.wen.awenboot.hutool.test;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author wen
 * @version 1.0
 * @date 2020/9/8 14:55
 */
@Slf4j
public class DateMain {
    public static void main(String[] args) {

//        test1();
//        test2();
//        test3();
//        test4();
    }

    private static void test1() {
        Date date = DateUtil.date();
        log.info("当前日期date=" + date);

        long time = DateUtil.parse("2020-09-09 21:21:21").getTime();
        Date date3 = DateUtil.date(time);
        log.info("毫秒转日期date3=" + date3);

        String now = DateUtil.now();
        String today = DateUtil.today();
        log.info("字符串日期now={},today={}", now, today);

        String dateStr = "2020-09-09";
        Date date4 = DateUtil.parse(dateStr);
        log.info("字符串转日期date4=" + date4);

        Date date4_1 = DateUtil.parse("2020-09-09", "yyyy-MM-dd");
        log.info("yyyy-MM-dd格式化日期date4_1=" + date4_1);

        String dateStr2 = "2020-09-09 21:21:21";
        Date date5 = DateUtil.parse(dateStr2);
        log.info("date5=" + date5);
        log.info("某天的开始时间begin={},end={}", DateUtil.beginOfDay(date5), DateUtil.endOfDay(date5));
        log.info("某月的开始时间begin={},end={}", DateUtil.beginOfMonth(date5), DateUtil.endOfMonth(date5));

    }

    private static void test2() {

        String dateStr1 = "2020-09-01 22:33:23";
        Date date1 = DateUtil.parse(dateStr1);

        String dateStr2 = "2020-09-09 23:33:23";
        Date date2 = DateUtil.parse(dateStr2);

        long betweenDay = DateUtil.between(date1, date2, DateUnit.DAY);
        log.info("两个日期之间的天数={}", betweenDay);

        log.info("计算年龄={},是否闰年={}", DateUtil.ageOfNow("1990-01-30"), DateUtil.isLeapYear(2020));
    }

    private static void test3(){
        log.error("统计耗时功能,类是StopWatch功能:");

        TimeInterval timer = DateUtil.timer();
        try {
            TimeUnit.MILLISECONDS.sleep(1100L);
        } catch (InterruptedException e) {
            log.error("",e);
        }
        log.error("花费毫秒数"+timer.interval());
//        log.error("花费分钟数"+timer.intervalMinute());
    }

    private static void test4() {
        Date date = new Date();
        log.info("当前日期:date={}", date);
        DateTime dt = DateTime.of(date);
        log.info("格式化时间:str={}", dt.toString());


        DateTime d1 = dt.offset(DateField.DAY_OF_YEAR, 1);
        log.info("当前时间加一天:d1={}", d1);
    }
}
