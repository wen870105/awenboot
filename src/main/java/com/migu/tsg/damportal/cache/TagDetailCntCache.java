package com.migu.tsg.damportal.cache;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.migu.tsg.damportal.converter.MiguTagDetailCntConverter;
import com.migu.tsg.damportal.service.MiguTagDetailCntServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class TagDetailCntCache {
    private static final Logger LOGGER = LoggerFactory.getLogger(TagDetailCntCache.class);

    private LoadingCache<String, TagDetailCntVO> cache;

    private int maximumSize = 2000;

    // 通过switchCache每日更新
    private Date cacheDate = DateTime.now();

    @Autowired
    private MiguTagDetailCntServiceImpl detailCntService;

    @Autowired
    private MiguTagDetailCntConverter converter;

    public TagDetailCntCache() {
        cache = buildCache();
    }

    public long incrementAndGet(String key) {
        TagDetailCntVO obj = null;
        try {
            obj = cache.get(key);
        } catch (ExecutionException e) {
            LOGGER.error("获取缓存异常", e);
        }
        obj.setUpdateDate(new Date());
        return obj.getTagCnt().incrementAndGet();
    }

    public void refreshCache() {
        DateTime now = DateTime.now();
        LOGGER.info("每日切换新的缓存当前日期是{}", now.toString("yyyyMMdd"));
        ConcurrentMap<String, TagDetailCntVO> oldMap = cache.asMap();
        cacheDate = now;
        cache = buildCache();
        upateToDb(oldMap);
    }

    public ConcurrentMap<String, TagDetailCntVO> asMap() {
        return cache.asMap();
    }


    public int taskUpateCacheToDb() {
        Map<String, TagDetailCntVO> map = cache.asMap();

        AtomicInteger counter = new AtomicInteger();
        Date date = new Date();
        map.forEach((k, val) -> {
            // 未修改数据,减少数据库交互
            if (val.getUpdateDate() != null && val.getToDbDate().compareTo(val.getUpdateDate()) == 0) {
                return;
            }
            val.setToDbDate(date);
            val.setUpdateDate(date);

            // 并发考虑,为了不丢失数据切换新缓存, 老缓存增量更新到数据库
            TagDetailCntVO newObj = converter.clone(val);
            newObj.setTagCnt(new AtomicLong(0L));
            cache.put(k, newObj);

            detailCntService.updateIncrementCntById(val);
            counter.getAndIncrement();
        });
        return counter.intValue();
    }

    public int upateToDb(Map<String, TagDetailCntVO> map) {
        AtomicInteger counter = new AtomicInteger();
        List<TagDetailCntVO> list = new ArrayList<>();
        Date date = new Date();
        map.forEach((k, val) -> {
            // 未修改数据,减少数据库交互
            if (val.getUpdateDate() != null && val.getToDbDate().compareTo(val.getUpdateDate()) == 0) {
                return;
            }
            val.setToDbDate(date);
            val.setUpdateDate(date);
            list.add(val);
            counter.getAndIncrement();
        });
        if (CollUtil.isNotEmpty(list)) {
            detailCntService.updateDetailCnt(list);
        }
        return counter.intValue();
    }


    private LoadingCache<String, TagDetailCntVO> buildCache() {
        LoadingCache<String, TagDetailCntVO> cache = CacheBuilder.newBuilder()
                .maximumSize(maximumSize)
                .build(new CacheLoader<String, TagDetailCntVO>() {
                    @Override
                    public TagDetailCntVO load(String key) throws Exception {
                        Date date = new Date();
                        TagDetailCntVO obj = new TagDetailCntVO();
                        obj.setTagCode(key);
                        obj.setTagCnt(new AtomicLong(0L));
                        obj.setCacheDate(cacheDate);
                        obj.setUpdateDate(date);
                        obj.setToDbDate(date);
                        return obj;
                    }
                });
        return cache;
    }


}
