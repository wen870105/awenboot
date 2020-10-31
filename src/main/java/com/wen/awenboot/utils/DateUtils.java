package com.wen.awenboot.utils;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.Minutes;
import org.joda.time.Seconds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author wen
 * @version 1.0
 * @date 2020/10/30 21:20
 */
public class DateUtils {
    public static final Logger LOGGER = LoggerFactory.getLogger(DateUtils.class);

    private static final String YYYY_MM_DD = "yyyy-MM-dd";

    /**
     * yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     */
    public static String asString(Date date) {
        return asString(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static String asString(Date date, String pattern) {
        return date != null ? new SimpleDateFormat(pattern).format(date) : null;
    }

    public static String asStringWithoutTime(Date date) {
        return date != null ? new SimpleDateFormat(YYYY_MM_DD).format(date) : null;
    }

    public static String convertDateStrFromTo(String date, String fromPattern, String toPattern) {
        Date d = asDate(date, fromPattern);
        return asString(d, toPattern);
    }

    /**
     * 两个日期间隔天数
     * 注意第二参数不能小鱼第一参数
     *
     * @param d1 day one
     * @param d2 day two
     * @return 注意，结果是day two减day one，所以可能为负数
     */
    public static long daysBetween(Date d1, Date d2) {
        Interval interval = new Interval(d1.getTime(), d2.getTime());
        return interval.toDuration().getStandardDays();
    }

    /**
     * 注意第二参数不能小鱼第一参数
     *
     * @param d1
     * @param d2
     * @return
     */
    public static long hoursBetween(Date d1, Date d2) {
        Interval interval = new Interval(d1.getTime(), d2.getTime());
        return interval.toDuration().getStandardHours();
    }

    /**
     * 两个日期之间相差的秒数
     *
     * @param d1 day one
     * @param d2 day two
     * @return 注意，结果是day two减day one，所以可能为负数
     */
    public static int secondsBetween(Date d1, Date d2) {
        return Seconds.secondsBetween(new DateTime(d1), new DateTime(d2)).getSeconds();
    }

    /**
     * 两个日期之间相差的分钟数
     *
     * @param d1 day one
     * @param d2 day two
     * @return 注意，结果是day two减day one，所以可能为负数
     */
    public static int minutesBetween(Date d1, Date d2) {
        return Minutes.minutesBetween(new DateTime(d1), new DateTime(d2)).getMinutes();
    }

    /**
     * As date date.
     */
    public static Date asDate(String date) {
        return asDate(date, null);
    }

    /**
     * As date date.
     */
    public static Date asDate(String date, String pattern) {
        try {
            if (StringUtils.isBlank(date)) {
                return null;
            }
            if (StringUtils.isNotBlank(pattern)) {
                return new SimpleDateFormat(pattern).parse(date);
            } else {
                if (date.length() == 8) {
                    return new SimpleDateFormat("yyyyMMdd").parse(date);
                } else if (date.length() == 10) {
                    return new SimpleDateFormat(YYYY_MM_DD).parse(date);
                } else if (date.length() == 12) {
                    return new SimpleDateFormat("yyyyMMddHHmm").parse(date);
                } else if (date.length() == 14) {
                    return new SimpleDateFormat("yyyyMMddHHmmss").parse(date);
                } else if (date.length() == 19) {
                    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
                } else {
                    return DateTime.parse(date).toDate();
                }
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("error date format, date format must be 'yyyy-MM-dd' or 'yyyy-MM-dd HH:mm:ss'" + e.getMessage());
        }
    }

    /**
     * add days
     */
    public static Date addDays(Date date, int days) {
        if (0 == days) {
            return date;
        }
        DateTime dateTime = new DateTime(date);
        dateTime = dateTime.plusDays(days);
        return dateTime.toDate();
    }

    /**
     * 获取当前日期的第一天和最后一天,如果是当前年月获得当前日期
     *
     * @param date
     * @return
     */
    public static String[] getBeginMonthAndEndMonthString(Date date) {
        String[] arr = new String[2];
        DateTime dateTime = new DateTime(date);
        arr[0] = dateTime.dayOfMonth().withMinimumValue().toString(YYYY_MM_DD);
        //是否是本月
        if (dateTime.dayOfMonth().withMaximumValue().toString("yyyy-MM").equalsIgnoreCase(dateTime.toString("yyyy-MM"))) {
            arr[1] = dateTime.toString(YYYY_MM_DD);
        } else {
            arr[1] = dateTime.dayOfMonth().withMaximumValue().toString(YYYY_MM_DD);
        }

        return arr;
    }

    /**
     * 两个日期的之间的所有日期
     *
     * @param fromDate
     * @param endDate
     * @return
     */
    public static List<String> getBetweenDates(Date fromDate, Date endDate) {
        List<String> list = new ArrayList<>();

        DateTime start = new DateTime(asStringWithoutTime(fromDate));
        DateTime end = new DateTime(asStringWithoutTime(endDate));

        if (start.isAfter(end)) {
            return list;
        }
        int i = 0;
        while (end.isAfter(start)) {
            list.add(start.toString(YYYY_MM_DD));
            start = start.plusDays(1);
            i++;
            if (i == 100) {
                LOGGER.info("可能有错误数据这里只提供100个结果");
                break;
            }
        }
        return list;
    }


    /**
     * 获取24小时的小时维度,24个
     * 返回格式 HH
     *
     * @return
     */
    public static List<String> get24Hours() {
        List<String> list = new ArrayList<>();
        String ret = null;
        for (int i = 0; i < 24; i++) {
            ret = "";
            if (i < 10) {
                ret += "0";
            }
            ret += (i + ":00");
            list.add(ret);
        }
        return list;
    }

    /**
     * 获取24小时的分钟维度,1440个
     * 返回格式 HH:mm
     *
     * @return
     */
    public static List<String> get24HoursMinutes() {
        List<String> list = new ArrayList<>();
        String ret = null;
        for (int i = 0; i < 24; i++) {
            for (int j = 0; j < 60; j++) {
                ret = "";
                if (i < 10) {
                    ret += "0";
                }
                ret += (i + ":");
                if (j < 10) {
                    ret += "0";
                }
                ret += (j);
                list.add(ret);
            }
        }
        return list;
    }

    /**
     * 是否是有效日期
     *
     * @param sDate
     * @return
     */
    public static boolean isLegalDate(String sDate, String pattern) {
        if (sDate.length() != 10) {
            return false;
        }

        DateFormat formatter = new SimpleDateFormat(pattern);
        try {
            Date date = formatter.parse(sDate);
            return sDate.equals(formatter.format(date));
        } catch (Exception e) {
            return false;
        }
    }

    public static void main(String[] args) {
        List<String> list = get24Hours();
//        List<String> list = get24HoursMinutes();
        System.out.println(list.size());
        System.out.println(JSON.toJSONString(list));
        System.out.println(JSON.toJSONString(getBeginMonthAndEndMonthString(new Date())));

        System.out.println(JSON.toJSONString(getBeginMonthAndEndMonthString(new DateTime().plusMonths(1).toDate())));

//        System.out.println(JSON.toJSONString(getBetweenDates(new Date(), new DateTime().plusMonths(1).toDate())));

        System.out.println(JSON.toJSONString(getBetweenDates(asDate("2020-03-01"), asDate("2020-03-15"))));
    }
}

