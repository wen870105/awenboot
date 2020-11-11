package com.wen.awenboot.utils;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.google.common.util.concurrent.RateLimiter;
import com.wen.awenboot.config.ZhuangkuConfig;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wen
 * @version 1.0
 * @date 2020/11/11 10:53
 */
@Slf4j
public class RateLimiterUtils {
    /**
     * @param limiter
     * @param currentMinute
     * @return
     */
    public static Integer refreshLimitRateIfNeed(RateLimiter limiter, ZhuangkuConfig cfg, int currentMinute) {
        DateTime date = DateUtil.date();
        int hour = date.hour(true);
        int minute = date.minute();
        if (minute != currentMinute) {
            int offset = cfg.getRateLimiterQpsByHH(hour);
            int start = 1;
            int end = 1;
            ////        0-8点 （0-50tps）
            ////        8-24点 （50-300tps）
            if (hour >= 0 && hour < 8) {
                start = 1 + offset;
                end = 50 + offset;
            } else {
                start = 50 + offset;
                end = 300 + offset;
            }
            int rate = RandomUtil.randomInt(start, end);


            limiter.setRate(rate);
            return minute;
        }
        return null;
    }
}
