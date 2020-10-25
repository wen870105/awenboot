package com.migu.tsg.damportal.controller.request;

/**
 * @author wen
 * @version 1.0
 * @date 2020/10/21 16:25
 */
public class TagUpdateRequest {
    /**
     * 标签ID
     */
    private String tagCode;

    /**
     * 标签说明
     */
    private String memo;

    /**
     * 工作流
     */
    private String workflowMemo;

    /**
     * 数据源
     */
    private String datasourceMemo;

    /**
     * 计算规则
     */
    private String generateRule;

    /**
     * 更新周期(1天,2周,3月)
     */
    private Integer updatePeriod;

    public String getTagCode() {
        return tagCode;
    }

    public void setTagCode(String tagCode) {
        this.tagCode = tagCode;
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
}
