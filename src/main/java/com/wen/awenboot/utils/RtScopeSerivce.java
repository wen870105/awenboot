package com.wen.awenboot.utils;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.wen.awenboot.cache.CntBean;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author wen
 * @version 1.0
 * @date 2021/1/6 13:52
 */
@Slf4j
public class RtScopeSerivce {
    private LoadingCache<String, CntBean> cache;

    public RtScopeSerivce() {
        buildCache();
    }

    public long addVal(long val) {
        String key = RtScopeUtils.getRtScope(val);
        CntBean obj = null;
        try {
            obj = cache.get(key);
        } catch (ExecutionException e) {
            log.error("获取缓存异常", e);
        }
        return obj.getVal().incrementAndGet();
    }

    public List<CntBean> getRetList() {
        return new ArrayList(cache.asMap().values());
    }

    private LoadingCache<String, CntBean> buildCache() {
        LoadingCache<String, CntBean> cache = CacheBuilder.newBuilder()
                .maximumSize(100)
                .build(new CacheLoader<String, CntBean>() {
                    @Override
                    public CntBean load(String key) throws Exception {
                        Date date = new Date();
                        CntBean obj = new CntBean();
                        obj.setKey(key);
                        obj.setVal(new AtomicLong(0L));
                        return obj;
                    }

                });
        this.cache = cache;
        return cache;
    }

    @Override
    public String toString() {
        return "RtScopeSerivce{" +
                getRetList().toString() +
                '}';
    }
}
