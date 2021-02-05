package com.wen.awenboot.cache;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.wen.awenboot.converter.CntBeanConverter;
import com.wen.awenboot.domain.MiguTagApiDetailCnt;
import com.wen.awenboot.service.MiguTagApiDetailCntServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author wen
 * @version 1.0
 * @date 2020/10/23 10:09
 */
@Service
@Slf4j
public class TagDetailCntCache {

    private LoadingCache<String, CntBean> cache;

    private static final int MAXIMUM_SIZE = 500;

    private Date cacheDate = new Date();
    @Resource
    private CntBeanConverter converter;

    @Autowired
    private MiguTagApiDetailCntServiceImpl service;

    public TagDetailCntCache() {
        cache = buildCache();
    }

    private static TagDetailCntCache instance;

    public static TagDetailCntCache getInstance() {
        return instance;
    }

    @PostConstruct
    public void init() {
        instance = this;
    }

    public long incrementAndGet(String key) {
        CntBean obj = null;
        try {
            obj = cache.get(key);
        } catch (ExecutionException e) {
            log.error("获取缓存异常", e);
        }
        obj.setUpdateDate(new Date());
        return obj.getVal().incrementAndGet();
    }

    public void refreshCache() {
        DateTime now = DateTime.now();
        log.info("每日切换新的缓存当前日期是{}", now.toString("yyyyMMdd"));
        ConcurrentMap<String, CntBean> oldMap = cache.asMap();
        cache = buildCache();
        upateToDb(oldMap);
    }

    public ConcurrentMap<String, CntBean> asMap() {
        return cache.asMap();
    }


    public int taskUpateCacheToDb() {
        Map<String, CntBean> map = cache.asMap();

        AtomicInteger counter = new AtomicInteger();
        Date date = new Date();
        map.forEach((k, val) -> {
            if (val.getVal().longValue() > 0L) {
                CntBean newObj = converter.clone(val);
                newObj.setVal(new AtomicLong(0L));
                cache.put(k, newObj);

                updateCnt(val);
                counter.getAndIncrement();
            }
        });
        return counter.intValue();
    }

    private void updateCnt(CntBean val) {
        MiguTagApiDetailCnt cnt = new MiguTagApiDetailCnt();
        cnt.setCnt(val.getVal().longValue());
        cnt.setTagKey(val.getKey());
        cnt.setCreateDate(new java.sql.Date(val.getUpdateDate().getTime()));
        service.updateIncrementCntById(cnt);
    }

    public int upateToDb(Map<String, CntBean> map) {
        AtomicInteger counter = new AtomicInteger();
        List<CntBean> list = new ArrayList<>();
        map.forEach((k, val) -> {
            if (val.getVal().longValue() > 0L) {
                list.add(val);
                counter.getAndIncrement();
            }
        });
        if (CollUtil.isNotEmpty(list)) {
            for (CntBean cnt : list) {
                updateCnt(cnt);
            }
        }
        return counter.intValue();
    }


    private LoadingCache<String, CntBean> buildCache() {
        LoadingCache<String, CntBean> cache = CacheBuilder.newBuilder()
                .maximumSize(MAXIMUM_SIZE)
                .build(new CacheLoader<String, CntBean>() {
                    @Override
                    public CntBean load(String key) throws Exception {
                        Date date = new Date();
                        CntBean obj = new CntBean();
                        obj.setKey(key);
                        obj.setVal(new AtomicLong(0L));
                        obj.setUpdateDate(date);
                        return obj;
                    }
                });
        return cache;
    }


}

