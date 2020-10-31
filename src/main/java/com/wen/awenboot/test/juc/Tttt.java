package com.wen.awenboot.test.juc;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import com.google.common.collect.Lists;
import com.wen.awenboot.utils.DateUtils;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wen
 * @version 1.0
 * @date 2020/10/30 21:28
 */
public class Tttt {
    public static void main(String[] args) {
        List<String> dates = DateUtils.getBetweenDates(DateUtil.parse("2020-10-01"), DateUtil.parse("2020-10-31"));

        List<MonthHolder> list = new ArrayList<>();
        while (dates.size() > 0) {
            MonthHolder mh = new MonthHolder();
            list.add(mh);
            if (dates.size() / 9 > 0) {
                List<String> sub = CollUtil.sub(dates, 0, 7);
                ArrayList<String> strings = Lists.newArrayList(sub);
                dates.removeAll(sub);
                mh.start = strings.get(0);
                mh.end = strings.get(strings.size() - 1);
            } else {
                mh.start = dates.get(0);
                mh.end = dates.get(dates.size() - 1);
                break;
            }
        }
        System.out.println(list.size());
        System.out.println(list);
    }

    @ToString
    static class MonthHolder {
        public String start;
        public String end;
    }
}
