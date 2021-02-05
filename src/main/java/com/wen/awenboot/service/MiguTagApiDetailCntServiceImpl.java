/**
 * created by Wen.
 */
package com.wen.awenboot.service;

import com.alibaba.fastjson.JSON;
import com.wen.awenboot.dao.MiguTagApiDetailCntMapper;
import com.wen.awenboot.domain.MiguTagApiDetailCnt;
import com.wen.awenboot.service.base.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import javax.annotation.Resource;

/**
 * 标签调用明细表,记录每个标签每天访问总量,记录维度为天
 *
 * @author Wen
 * @since 2020-10-23
 */
@Service
@Slf4j
public class MiguTagApiDetailCntServiceImpl extends BaseServiceImpl<MiguTagApiDetailCnt> {

    @Resource
    private MiguTagApiDetailCntMapper infoMapper;


    @Override
    public Mapper<MiguTagApiDetailCnt> getDao() {
        return infoMapper;
    }

    public void updateIncrementCntById(MiguTagApiDetailCnt obj) {
        MiguTagApiDetailCnt query = new MiguTagApiDetailCnt();
        query.setTagKey(obj.getTagKey());
        query.setCreateDate(obj.getCreateDate());
        MiguTagApiDetailCnt db = this.selectOne(query);
        if (db == null) {
            log.info("{}", JSON.toJSONString(obj));
            add(obj);
        } else {
            MiguTagApiDetailCnt updater = new MiguTagApiDetailCnt();
            updater.setId(db.getId());
            updater.setCnt(obj.getCnt());
            infoMapper.updateIncrementCntById(updater);
        }
    }

}
