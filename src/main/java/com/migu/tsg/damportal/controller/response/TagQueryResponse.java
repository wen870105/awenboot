package com.migu.tsg.damportal.controller.response;

/**
 * @author wen
 * @version 1.0
 * @date 2020/10/21 16:39
 */
public class TagQueryResponse {
    /**
     * 标签编码
     */
    private String tagCode;

    /**
     * 标签名称
     */
    private String tagName;
    /**
     * (1枚举,0非枚举)
     */
    private Integer tagType;

    /**
     * 标签值, 枚举类型标签才有此值
     */
    private String tagValue;

    /**
     * 父节点标签编码
     */
    private String parentCode;

    /**
     * 父节点标签名称
     */
    private String parentName;

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

    /**
     * 创建时间yyyy-MM-dd HH:mm:ss
     */
    private String createTime;

    /**
     * 更新时间yyyy-MM-dd HH:mm:ss
     */
    private String updateTime;

    /**
     * (0停用,1启用)
     */
    private Integer status;

    /**
     * 创建人员账号
     */
    private String creatorAccount;

    /**
     * 建人员名称
     */
    private String creator;

    /**
     * 调用次数
     */
    private Long callCounter;

    /**
     * 标签覆盖人次
     */
    private Long coverPeople;

    /**
     * 标签覆盖率,百分比
     */
    private String coverRate;

    public String getTagCode() {
        return tagCode;
    }

    public void setTagCode(String tagCode) {
        this.tagCode = tagCode;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Integer getTagType() {
        return tagType;
    }

    public void setTagType(Integer tagType) {
        this.tagType = tagType;
    }

    public String getTagValue() {
        return tagValue;
    }

    public void setTagValue(String tagValue) {
        this.tagValue = tagValue;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Long getCallCounter() {
        return callCounter;
    }

    public void setCallCounter(Long callCounter) {
        this.callCounter = callCounter;
    }

    public Long getCoverPeople() {
        return coverPeople;
    }

    public void setCoverPeople(Long coverPeople) {
        this.coverPeople = coverPeople;
    }

    public String getCoverRate() {
        return coverRate;
    }

    public void setCoverRate(String coverRate) {
        this.coverRate = coverRate;
    }
}
