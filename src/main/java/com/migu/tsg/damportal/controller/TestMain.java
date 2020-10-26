package com.migu.tsg.damportal.controller;

import com.alibaba.fastjson.JSON;
import com.migu.tsg.damportal.controller.request.TagCreateRequest;
import com.migu.tsg.damportal.controller.request.TagQueryRequest;
import com.migu.tsg.damportal.controller.request.TagUpdateRequest;

/**
 * @author wen
 * @version 1.0
 * @date 2020/10/26 0:07
 */
public class TestMain {
    public static void main(String[] args) {
        testTagQueryRequest();
        testUpdate();
        testCreate();
    }

    private static void testTagQueryRequest() {
        TagQueryRequest req = new TagQueryRequest();
        req.setCreateTimeStart("");
        req.setCreateTimeEnd("");
        req.setCreator("");
        req.setParentCode("");
        req.setPageIndex(1);
        req.setTagName("");
        System.out.println(JSON.toJSONString(req));
    }

    private static void testUpdate() {
        TagUpdateRequest request = new TagUpdateRequest();
        request.setTagCode("basic_videoring_40011");
        request.setMemo("memo123");
        request.setWorkflowMemo("setWorkflowMemo1111");
        request.setDatasourceMemo("setDatasourceMemo2222");
        request.setGenerateRule("setGenerateRule333");
        request.setUpdatePeriod(1);

        System.out.println(JSON.toJSONString(request));
    }

    private static void testCreate() {
        TagCreateRequest request = new TagCreateRequest();
        request.setTagCode("3101");
        request.setParentCode("basic_2001");
        request.setCategoryType(1);
        request.setMemo("Memo");
        request.setWorkflowMemo("WorkflowMemo");
        request.setDatasourceMemo("DatasourceMemo");
        request.setGenerateRule("GenerateRule");
        request.setUpdatePeriod(1);
        request.setUpdatePeriodVal(1);
        request.setCreatorAccount("zhangsanAccount");
        request.setCreator("张三");

        System.out.println(JSON.toJSONString(request));
    }
}
