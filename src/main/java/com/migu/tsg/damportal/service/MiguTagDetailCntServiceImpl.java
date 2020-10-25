/**
 * created by Wen.
 */
package com.migu.tsg.damportal.service;

import cn.hutool.core.date.DateUtil;
import com.migu.tsg.damportal.cache.TagDetailCntVO;
import com.migu.tsg.damportal.dao.MiguTagDetailCntMapper;
import com.migu.tsg.damportal.domain.MiguTagDetailCnt;
import com.migu.tsg.damportal.service.base.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.common.Mapper;

import javax.annotation.Resource;
import java.util.List;

/**
 * 标签调用明细表,记录每个标签每天访问总量,记录维度为天
 *
 * @author Wen
 * @since 2020-10-23
 */
@Service
public class MiguTagDetailCntServiceImpl extends BaseServiceImpl<MiguTagDetailCnt> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MiguTagDetailCntServiceImpl.class);
    @Resource
    private MiguTagDetailCntMapper cntMapper;

    @Override
    public Mapper<MiguTagDetailCnt> getDao() {
        return cntMapper;
    }

    @Transactional
    public void updateDetailCnt(List<TagDetailCntVO> list) {
        if (list != null) {
            list.forEach(obj -> {
                updateIncrementCntById(obj);
            });
        }
    }

    /**
     * 增量增加调用次数
     * @param obj
     */
    @Transactional
    public void updateIncrementCntById(TagDetailCntVO obj) {
        MiguTagDetailCnt dbReq = v2d(obj);

        MiguTagDetailCnt tem = new MiguTagDetailCnt();
        tem.setTagCode(dbReq.getTagCode());
        tem.setCreateDate(dbReq.getCreateDate());
        MiguTagDetailCnt old = selectOne(tem);

        // 没有并发量未使用乐观锁
        // TODO 以后修改为分布式队列更新
        if (old == null) {
            cntMapper.insert(dbReq);
        } else {
            dbReq.setId(old.getId());
            cntMapper.updateIncrementCntById(dbReq);
        }
    }

    public MiguTagDetailCnt v2d(TagDetailCntVO obj) {
        if (obj == null) {
            return null;
        }

        MiguTagDetailCnt ret = new MiguTagDetailCnt();
        ret.setCreateDate(DateUtil.format(obj.getCacheDate(), "yyyyMMdd"));
        ret.setUpdateTime(obj.getUpdateDate());
        ret.setTagCnt(obj.getTagCnt().longValue());
        ret.setTagCode(obj.getTagCode());
        return ret;
    }
}
