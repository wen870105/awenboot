package com.migu.tsg.damportal.cache;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.migu.tsg.damportal.domain.MiguTagInfo;
import com.migu.tsg.damportal.service.MiguTagInfoServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

/**
 * 标签的数据服务,缓存数据定期刷新
 *
 * @author wen
 * @version 1.0
 * @date 2020/9/25 17:53
 */
@Service
public class TagDataCache {
    private static final Logger LOGGER = LoggerFactory.getLogger(TagDataCache.class);

    public static final int MAXIMUM_SIZE = 2000;

    private Date lastUpdateDate = null;

    private Cache<String, MiguTagInfo> cache;

    @Resource
    private MiguTagInfoServiceImpl infoService;

    @PostConstruct
    private void init() {
        TimeInterval timer = DateUtil.timer();
        cache = buildCache();
        List<MiguTagInfo> list = infoService.getAll();
        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(obj -> cache.put(obj.getId(), obj));
        }
        lastUpdateDate = new Date();
        LOGGER.info("AttributeDataServiceImpl初始化完成耗时{}ms,总数量为{},lastUpdateDate={}", timer.interval(), list.size(), lastUpdateDate);
    }


    /**
     * task任务入口
     *
     * @return
     */
    public boolean refresh() {
        Example exp = new Example(MiguTagInfo.class);
        Example.Criteria criteria = exp.createCriteria();
        criteria.andGreaterThan("updateTime", lastUpdateDate);
        List<MiguTagInfo> updateList = infoService.selectByExample(exp);

        boolean flag = false;
        if (!CollectionUtils.isEmpty(updateList)) {
            flag = true;
            cache.invalidateAll();
            init();
            lastUpdateDate = updateList.stream().max((o1, o2) -> o1.getUpdateTime().compareTo(o2.getUpdateTime())).get().getUpdateTime();
        }
        return flag;
    }

    public boolean refreshAll() {
        init();
        return true;
    }

    public MiguTagInfo getById(String tagId) {
        return cache.getIfPresent(tagId);
    }

    public ConcurrentMap<String, MiguTagInfo> getAllCache() {
        return cache.asMap();
    }

    public List<MiguTagInfo> getByTagFather(String tagFather) {
        return cache.asMap().values().stream().filter(p -> p.getTagFather().equalsIgnoreCase(tagFather)).collect(Collectors.toList());
    }

    private Cache<String, MiguTagInfo> buildCache() {
        Cache<String, MiguTagInfo> cache = CacheBuilder.newBuilder().maximumSize(MAXIMUM_SIZE).build();
        return cache;
    }
}
