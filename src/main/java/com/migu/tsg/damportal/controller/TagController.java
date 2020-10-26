package com.migu.tsg.damportal.controller;

import com.alibaba.fastjson.JSON;
import com.migu.tsg.damportal.cache.TagDataCache;
import com.migu.tsg.damportal.controller.request.TagCreateRequest;
import com.migu.tsg.damportal.controller.request.TagDetailRequest;
import com.migu.tsg.damportal.controller.request.TagOriginalQueryRequest;
import com.migu.tsg.damportal.controller.request.TagQueryRequest;
import com.migu.tsg.damportal.controller.request.TagUpdateRequest;
import com.migu.tsg.damportal.controller.request.TagUpdateStatusRequest;
import com.migu.tsg.damportal.controller.response.Result;
import com.migu.tsg.damportal.controller.response.TagDetailResponse;
import com.migu.tsg.damportal.controller.response.TagOriginalQueryResponse;
import com.migu.tsg.damportal.controller.response.TagQueryResponse;
import com.migu.tsg.damportal.controller.response.TagTreeResponse;
import com.migu.tsg.damportal.converter.MiguTagInfoConverter;
import com.migu.tsg.damportal.domain.MiguTagAttribute;
import com.migu.tsg.damportal.domain.MiguTagInfo;
import com.migu.tsg.damportal.domain.MiguTagInfoQuery;
import com.migu.tsg.damportal.domain.base.Page;
import com.migu.tsg.damportal.enums.TagStatusEnum;
import com.migu.tsg.damportal.enums.TagTypeEnum;
import com.migu.tsg.damportal.exception.SystemErrorType;
import com.migu.tsg.damportal.service.MiguTagAttributeServiceImpl;
import com.migu.tsg.damportal.service.MiguTagInfoServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

@RequestMapping("/tag")
@RestController
@Slf4j
public class TagController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TagController.class);

    @Autowired
    private MiguTagInfoServiceImpl infoService;

    @Autowired
    private MiguTagAttributeServiceImpl attrService;

    @Autowired
    private TagDataCache tagDataCache;

    @Resource
    private MiguTagInfoConverter infoConverter;

    @RequestMapping("updateStatus")
    @ResponseBody
    public Result<Page<TagQueryResponse>> updateStatus(@RequestBody TagUpdateStatusRequest request) {
        LOGGER.info("request={}", JSON.toJSONString(request));
        MiguTagInfo attr = infoService.selectById(request.getTagCode());
        Result ret;
        if (attr == null) {
            ret = Result.fail(SystemErrorType.NOT_FOUND);
        } else {
            MiguTagInfo updater = new MiguTagInfo();
            TagStatusEnum statusEnum = TagStatusEnum.getValByCode(request.getStatus());
            if (statusEnum == null) {
                ret = Result.fail("状态参数错误");
            } else {
                updater.setStatus(statusEnum.getCode());
                updater.setId(request.getTagCode());
                infoService.updateById(updater);
                ret = Result.success();
            }
        }

        LOGGER.info("response={}", JSON.toJSONString(ret));
        return ret;
    }

    @RequestMapping("query")
    @ResponseBody
    public Result<Page<TagQueryResponse>> query(@RequestBody TagQueryRequest request) {
        LOGGER.info("request={}", JSON.toJSONString(request));
        MiguTagInfoQuery query = infoConverter.d2d(request);

        int pageIndex = request.getPageIndex();
        int pageSize = 20;
        pageIndex = pageIndex <= 1 ? 1 : pageIndex;
        query.setStartIndex((pageIndex - 1) * pageSize);
        query.setOffset(pageSize);
        query.setPageIndex(pageIndex);
        Page<MiguTagInfo> pager = infoService.selectPage(query);

        Result ret;
        if (pager.getTotalCount() > 0) {
            ret = Result.success(infoConverter.p2p(pager));
        } else {
            ret = Result.success();
        }
        LOGGER.info("response={}", JSON.toJSONString(ret));
        return ret;
    }

    @RequestMapping("tree")
    @ResponseBody
    public Result<List<TagTreeResponse>> tree() {
        ConcurrentMap<String, MiguTagInfo> map = tagDataCache.getAllCache();
        List<TagTreeResponse> list = map.values().stream().map(e -> {
            TagTreeResponse tree = new TagTreeResponse();
            tree.setParentCode(e.getTagFather());
            tree.setTagCode(e.getId());
            tree.setTagType(e.getTagType());
            tree.setTagName(e.getTagName());
            return tree;
        }).collect(Collectors.toList());
        Result ret = Result.success(list);
        LOGGER.info("response={}", JSON.toJSONString(ret));
        return ret;
    }

    @RequestMapping("update")
    @ResponseBody
    public Result<Object> update(@RequestBody TagUpdateRequest request) {
        LOGGER.info("request={}", JSON.toJSONString(request));
        MiguTagInfo attr = infoService.selectById(request.getTagCode());
        Result ret = null;
        if (attr != null) {
            MiguTagInfo update = infoConverter.d2v(request);
            infoService.updateById(update);
            ret = Result.success();
        } else {
            ret = Result.fail(SystemErrorType.NOT_FOUND);
        }
        LOGGER.info("response={}", JSON.toJSONString(ret));
        return ret;
    }


    @RequestMapping("detail")
    @ResponseBody
    public Result<TagDetailResponse> detail(@RequestBody TagDetailRequest request) {
        LOGGER.info("request={}", JSON.toJSONString(request));
        MiguTagInfo attr = infoService.selectById(request.getTagCode());
        Result ret = null;
        if (attr != null) {
            ret = Result.success(infoConverter.v2d(attr));
        } else {
            ret = Result.fail(SystemErrorType.NOT_FOUND);
        }
        LOGGER.info("response={}", JSON.toJSONString(ret));
        return ret;
    }

    @RequestMapping("originalQuery")
    @ResponseBody
    public Result<TagOriginalQueryResponse> originalQuery(@RequestBody TagOriginalQueryRequest request) {
        LOGGER.info("request={}", JSON.toJSONString(request));
        MiguTagAttribute attr = attrService.selectById(request.getTagCode());

        Result ret = null;
        if (attr != null) {
            TagOriginalQueryResponse resp = build(attr);
            if (resp != null) {
                ret = Result.success(resp);
            } else {
                ret = Result.fail(SystemErrorType.NOT_FOUND);
                ret.setMsg("数据ID错误,可能是标签父节点ID为空");
            }
        } else {
            ret = Result.fail(SystemErrorType.NOT_FOUND);
        }
        LOGGER.info("response={}", JSON.toJSONString(ret));
        return ret;
    }

    @RequestMapping("create")
    @ResponseBody
    public Result<Object> create(@RequestBody TagCreateRequest request) {
        LOGGER.info("request={}", JSON.toJSONString(request));
        Result ret = null;
        MiguTagInfo oldInfo = infoService.selectById(request.getTagCode());
        if (oldInfo != null) {
            ret = Result.fail("标签ID已经存在");
        } else {
            MiguTagInfo newInfo = build(request);
            if (newInfo == null) {
                ret = Result.fail("标签ID或者父标签ID不存在并且父节点不能是标签类型,可联系管理员确认");
            } else {
                infoService.add(newInfo);
                ret = Result.success();
            }
        }
        LOGGER.info("response={}", JSON.toJSONString(ret));
        return ret;
    }

    private MiguTagInfo build(TagCreateRequest request) {
        Date date = new Date();
        // 父节点必须是存在info表
        MiguTagInfo fatherTag = tagDataCache.getById(request.getParentCode());
        MiguTagAttribute attr = attrService.selectById(request.getTagCode());
        if (fatherTag == null || attr == null || fatherTag.getTagType() != null) {
            return null;
        }

        MiguTagInfo miguTagInfo = infoConverter.d2v(request);
        miguTagInfo.setId(request.getTagCode());
        miguTagInfo.setTagName(attr.getTagName());
        miguTagInfo.setTagClass(attr.getTagClass());
        miguTagInfo.setTagType(TagTypeEnum.getValByStr(attr.getTagType()).getCode());

        miguTagInfo.setTagFather(fatherTag.getId());
//        miguTagInfo.setTagValue();
        miguTagInfo.setCreateTime(date);
        miguTagInfo.setUpdateTime(date);
        return miguTagInfo;

    }

    private TagOriginalQueryResponse build(MiguTagAttribute attr) {
        MiguTagInfo info = tagDataCache.getById(attr.getTagFather());
        if (info == null) {
            return null;
        }
        TagOriginalQueryResponse resp = new TagOriginalQueryResponse();
        resp.setTagCode(attr.getId());
        resp.setTagName(attr.getTagName());
        resp.setTagType(TagTypeEnum.getValByStr(attr.getTagType()).getCode());
        resp.setParentCode(info.getId());
        resp.setParentName(info.getTagName());
        resp.setMemo(attr.getTagComment());
        return resp;
    }


}
