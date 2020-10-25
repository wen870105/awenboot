/**
 * created by Wen.
 */
package com.migu.tsg.damportal.domain;

import com.migu.tsg.damportal.domain.base.BaseDomain;

import javax.persistence.Id;
import java.util.Date;

/**
 * 标签信息表,比migu_tag_attribute更详细
 * @author Wen
 * @since 2020-10-23
 */
public class MiguTagInfo extends BaseDomain{
	//
	@Id
	private String id;
	// 名称
	private String tagName;
	// 级别1-6级
	private Integer tagClass;
	// 枚举,或者非枚举
	private Integer tagType;
	// 父节点
	private String tagFather;
	// 非枚举标签值
	private String tagValue;
	// 标签分类:1统计2规则3挖掘
	private Integer categoryType;
	// 标签说明
	private String memo;
	// 工作流备注
	private String workflowMemo;
	// 数据源备注
	private String datasourceMemo;
	// 计算规则
	private String generateRule;
	// 1天,2周,3月,4不定期
	private Integer updatePeriod;
	// 周更新选择周几;月更新会选择几号
	private Integer updatePeriodVal;
	// 
	private Date createTime;
	// 
	private Date updateTime;
	// 创建人员账号
	private String creatorAccount;
	// 创建人员名称
	private String creator;

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagClass(Integer tagClass) {
		this.tagClass = tagClass;
	}

	public Integer getTagClass() {
		return tagClass;
	}

	public void setTagType(Integer tagType) {
		this.tagType = tagType;
	}

	public Integer getTagType() {
		return tagType;
	}

	public void setTagFather(String tagFather) {
		this.tagFather = tagFather;
	}

	public String getTagFather() {
		return tagFather;
	}

	public void setTagValue(String tagValue) {
		this.tagValue = tagValue;
	}

	public String getTagValue() {
		return tagValue;
	}

	public void setCategoryType(Integer categoryType) {
		this.categoryType = categoryType;
	}

	public Integer getCategoryType() {
		return categoryType;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getMemo() {
		return memo;
	}

	public void setWorkflowMemo(String workflowMemo) {
		this.workflowMemo = workflowMemo;
	}

	public String getWorkflowMemo() {
		return workflowMemo;
	}

	public void setDatasourceMemo(String datasourceMemo) {
		this.datasourceMemo = datasourceMemo;
	}

	public String getDatasourceMemo() {
		return datasourceMemo;
	}

	public void setGenerateRule(String generateRule) {
		this.generateRule = generateRule;
	}

	public String getGenerateRule() {
		return generateRule;
	}

	public void setUpdatePeriod(Integer updatePeriod) {
		this.updatePeriod = updatePeriod;
	}

	public Integer getUpdatePeriod() {
		return updatePeriod;
	}

	public void setUpdatePeriodVal(Integer updatePeriodVal) {
		this.updatePeriodVal = updatePeriodVal;
	}

	public Integer getUpdatePeriodVal() {
		return updatePeriodVal;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setCreatorAccount(String creatorAccount) {
		this.creatorAccount = creatorAccount;
	}

	public String getCreatorAccount() {
		return creatorAccount;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreator() {
		return creator;
	}
}