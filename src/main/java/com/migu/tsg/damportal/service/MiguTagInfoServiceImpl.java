/**
 * created by Wen.
 */
package com.migu.tsg.damportal.service;

import cn.hutool.core.date.DateUtil;
import com.migu.tsg.damportal.cache.TagDataCache;
import com.migu.tsg.damportal.dao.MiguTagInfoMapper;
import com.migu.tsg.damportal.domain.MiguTagInfo;
import com.migu.tsg.damportal.enums.CategoryTypeEnum;
import com.migu.tsg.damportal.enums.TagTypeEnum;
import com.migu.tsg.damportal.enums.UpdatePeriodEnum;
import com.migu.tsg.damportal.service.base.BaseServiceImpl;
import com.migu.tsg.damportal.utils.ExcelTagBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import tk.mybatis.mapper.common.Mapper;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 标签调用明细表,记录每个标签每天访问总量,记录维度为天
 *
 * @author Wen
 * @since 2020-10-23
 */
@Service
public class MiguTagInfoServiceImpl extends BaseServiceImpl<MiguTagInfo> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MiguTagInfoServiceImpl.class);

    @Resource
    private MiguTagInfoMapper infoMapper;

    @Autowired
    private MiguTagAttributeServiceImpl attrService;

    @Autowired
    private MiguTagEnumServiceImpl enumService;

    @Autowired
    private TagDataCache cacheService;

    @Override
    public Mapper<MiguTagInfo> getDao() {
        return infoMapper;
    }

    public boolean validateTagCode(String code) {
        MiguTagInfo attr = infoMapper.selectByPrimaryKey(code);
        return attr == null ? false : true;
    }

    public List<MiguTagInfo> getAll() {
        return infoMapper.selectAll();
    }

    /**
     * 添加成功返回空,
     * 添加重复id,事务回滚提示错误id
     *
     * @param list
     * @return
     */
    @Transactional
    public String insertTags(List<MiguTagInfo> list) {
        for (MiguTagInfo obj : list) {
            if (infoMapper.selectByPrimaryKey(obj.getId()) != null) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return obj.getId();
            }
            infoMapper.insert(obj);
        }
        return null;
    }


    public List<ExcelTagBean> buildExcelBean(List<MiguTagInfo> list) {
        List<ExcelTagBean> ret = new ArrayList<>(list.size());
        for (MiguTagInfo obj : list) {
            MiguTagInfo info = cacheService.getById(obj.getId());

            ExcelTagBean bean = new ExcelTagBean();
            try {
                setCategorys(info, bean);
            } catch (Throwable e) {
                LOGGER.error("set属性异常id={}", obj.getId(), e);
            }


            bean.setTagVal(obj.getTagValue());
            bean.setDatasourceMemo(obj.getDatasourceMemo());
            bean.setGenerateRule(obj.getGenerateRule());
            bean.setUpdateTime(DateUtil.format(obj.getUpdateTime(), "yyyyMMdd HH:mm:ss"));
            bean.setCreator(obj.getCreator());
            bean.setWorkflowMemo(obj.getWorkflowMemo());

            UpdatePeriodEnum updatePeriodEnum = UpdatePeriodEnum.getValByCode(obj.getUpdatePeriod());
            bean.setUpdatePeriod(updatePeriodEnum == null ? "" : updatePeriodEnum.getVal());
            CategoryTypeEnum cateEnum = CategoryTypeEnum.getValByCode(obj.getCategoryType());
            bean.setCategoryType(cateEnum == null ? "" : cateEnum.getVal());
            TagTypeEnum tagTypeEnum = TagTypeEnum.getValByCode(obj.getTagType());
            bean.setTagType(tagTypeEnum == null ? "" : tagTypeEnum.getVal());
            ret.add(bean);
        }
        return ret;
    }


    private String getCategory(MiguTagInfo obj) {
        StringBuilder sb = new StringBuilder();
        sb.append(obj.getTagName()).append("(").append(obj.getId()).append(")");
        return sb.toString();
    }

    private void setCategorys(MiguTagInfo info, ExcelTagBean bean) {
        MiguTagInfo current = info;
        if (info.getTagClass() > 5) {
            bean.setCategory6(getCategory(current));
            bean.setCategory6_ori(current.getId());
            current = cacheService.getById(current.getTagFather());
        }
        if (info.getTagClass() > 4) {
            bean.setCategory5(getCategory(current));
            bean.setCategory5_ori(current.getId());
            current = cacheService.getById(current.getTagFather());
        }
        if (info.getTagClass() > 3) {
            bean.setCategory4(getCategory(current));
            bean.setCategory4_ori(current.getId());
            current = cacheService.getById(current.getTagFather());
        }
        if (info.getTagClass() > 2) {
            bean.setCategory3(getCategory(current));
            bean.setCategory3_ori(current.getId());
            current = cacheService.getById(current.getTagFather());
        }
        if (info.getTagClass() > 1) {
            bean.setCategory2(getCategory(current));
            bean.setCategory2_ori(current.getId());
            current = cacheService.getById(current.getTagFather());
        }
        bean.setCategory1(getCategory(current));
        bean.setCategory1_ori(current.getId());
    }

}
