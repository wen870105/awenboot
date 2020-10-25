package com.migu.tsg.damportal.controller.request;

import javax.validation.constraints.NotNull;

/**
 * @author wen
 * @version 1.0
 * @date 2020/10/21 16:15
 */
public class TagCreateRequest {
    /**
     * 标签ID,此ID必须是hbase标签维表已定义的id(hbase标签维表由标签开发人员提供)
     */
    @NotNull
    private String tagCode;

    /**
     * 父节点标签ID,此ID必须是hbase标签维表已定义的id(hbase标签维表由标签开发人员提供)
     */
    @NotNull
    private String parentCode;

    /**
     * 标签说明
     */
    @NotNull
    private String memo;

    /**
     * 工作流
     */
    @NotNull
    private String workflowMemo;

    /**
     * 数据源
     */
    @NotNull
    private String datasourceMemo;

    /**
     * 计算规则
     */
    @NotNull
    private String generateRule;
    /**
     * 更新周期(1天,2周,3月)
     */
    @NotNull
    private Integer updatePeriod;

    /**
     * 周更新选择周几;月更新会选择几号
     */
    private Integer updatePeriodVal;
    /**
     * 创建人员账号
     */
    @NotNull
    private String creatorAccount;
    /**
     * 创建人员名称
     */
    @NotNull
    private String creator;

    public String getTagCode() {
        return tagCode;
    }

    public void setTagCode(String tagCode) {
        this.tagCode = tagCode;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getWorkflowMemo() {
        return workflowMemo;
    }

    public void setWorkflowMemo(String workflowMemo) {
        this.workflowMemo = workflowMemo;
    }

    public String getDatasourceMemo() {
        return datasourceMemo;
    }

    public void setDatasourceMemo(String datasourceMemo) {
        this.datasourceMemo = datasourceMemo;
    }

    public String getGenerateRule() {
        return generateRule;
    }

    public void setGenerateRule(String generateRule) {
        this.generateRule = generateRule;
    }

    public Integer getUpdatePeriod() {
        return updatePeriod;
    }

    public void setUpdatePeriod(Integer updatePeriod) {
        this.updatePeriod = updatePeriod;
    }

    public Integer getUpdatePeriodVal() {
        return updatePeriodVal;
    }

    public void setUpdatePeriodVal(Integer updatePeriodVal) {
        this.updatePeriodVal = updatePeriodVal;
    }

    public String getCreatorAccount() {
        return creatorAccount;
    }

    public void setCreatorAccount(String creatorAccount) {
        this.creatorAccount = creatorAccount;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }
}
