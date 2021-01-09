package com.wen.awenboot.task;

import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.util.RandomUtil;
import com.wen.awenboot.cache.EsCntSerivce;
import com.wen.awenboot.utils.RtScopeSerivce;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wen
 * @version 1.0
 * @date 2021/1/8 16:25
 */
@Slf4j
public class TTT {

    public static void main(String[] args) {
        TimeInterval ret = new TimeInterval();
        RetLogger logger = new RetLogger();
        EsCntSerivce cntService = new EsCntSerivce();
        Map<String, String> rpcMap = new HashMap<>();
        RtScopeSerivce rtService = new RtScopeSerivce();
        int length = 90000000;
//        int length = 60000;
        for (int i = 0; i < length; i++) {
            String s = Integer.toString(RandomUtil.randomInt(0, 10000000));
            String val = rpcMap.get(s);
//            cntService.addEnumCounter();
//            String s = Integer.toString(RandomUtil.randomInt(0, 10000000));
//            String val = rpcMap.get(s);
//            String val = "";
//            if (val == null) {
//            TimeInterval ti = new TimeInterval();
//                rpcMap.put(s, s);
//            long interval = ti.interval();
            log.info("es耗时{}ms,count={},esKey={},key={} ,phone={}", 0);
//            rtService.addVal(interval);
//            cntService.addRpcCounter();

//            }
        }
        logger.getLogger().info("耗时分布={}", rtService.toString());
        logger.getLogger().info("各种调用总次数={}", cntService.toString());
        logger.getLogger().info("耗时={}ms", ret.interval());
    }
}
