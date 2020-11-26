package com.wen.awenboot.config;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Set;

/**
 * @author wen
 * @version 1.0
 * @date 2020/5/28 15:40
 */
@Component
@ConfigurationProperties(prefix = "zhuangku")
@Slf4j
@Data
public class ZhuangkuConfig {

    private String exportDir;

    private String dataSourceDir;

    private int rateLimiterQps;

    private String targetUrl;

    private int readFileLimit;

    private int printFlag;

    private int readTimeout = 10000;

    private int writeTimeout = 10000;

    private int connectTimeout = 10000;

    private String taskName;
    /**
     * 读取的数据文件列表
     */
    private Set<String> includeDataFileList;

    private Map<Integer, Integer> rateLimiterQpsMap;

    @PostConstruct
    private void init() {
        log.info("ZhuangkuConfig={}", JSON.toJSONString(this));
    }


    public int getRateLimiterQpsByHH(int hour) {
        Integer ret = rateLimiterQpsMap.get(hour);

        if (ret != null) {
            return ret;
        }
        return 1;
    }

    /**
     * qps和hours的关系
     * 如果没有配置值就用默认0-8点qps为20
     * 其它情况返回配置的rateLimiterQps
     *
     * @return
     */
    public int getRateLimiterQpsByHHmm(int hour, int minute) {
        int rate = innerGetRateLimiterQpsByHHmm(hour);
        return rate + RandomUtil.randomInt(1, 6);
    }

    private int innerGetRateLimiterQpsByHHmm(int hour) {
        Integer ret = rateLimiterQpsMap.get(hour);

        if (ret != null) {
            return ret;
        }
        if (hour >= 0 && hour < 8) {
            return 5;
        }
        return rateLimiterQps;
    }
}
