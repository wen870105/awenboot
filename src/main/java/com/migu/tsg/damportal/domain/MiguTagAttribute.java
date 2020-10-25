/**
 * created by Wen.
 */
package com.migu.tsg.damportal.domain;

import com.migu.tsg.damportal.domain.base.BaseDomain;

import javax.persistence.Id;
import java.util.Date;

/**
 * 
 * @author Wen
 * @since 2020-10-23
 */
public class MiguTagAttribute extends BaseDomain{
	//
	@Id
	private String id;
	// 
	private String tagName;
	// 
	private String tagAlias;
	// 
	private Integer tagClass;
	// 
	private String tagType;
	// 
	private String tagFather;
	// 
	private String tagComment;
	// 
	private String tagRule;
	// 
	private String creator;
	// 
	private Date createTime;
	// 
	private String modifier;
	// 
	private Date modifyTime;
	// 标签来源（1:标签;2:天眼;3:差运）
	private Integer source;
	// 更新频次
	private String frequencyUpdate;

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

	public void setTagAlias(String tagAlias) {
		this.tagAlias = tagAlias;
	}

	public String getTagAlias() {
		return tagAlias;
	}

	public void setTagClass(Integer tagClass) {
		this.tagClass = tagClass;
	}

	public Integer getTagClass() {
		return tagClass;
	}

	public void setTagType(String tagType) {
		this.tagType = tagType;
	}

	public String getTagType() {
		return tagType;
	}

	public void setTagFather(String tagFather) {
		this.tagFather = tagFather;
	}

	public String getTagFather() {
		return tagFather;
	}

	public void setTagComment(String tagComment) {
		this.tagComment = tagComment;
	}

	public String getTagComment() {
		return tagComment;
	}

	public void setTagRule(String tagRule) {
		this.tagRule = tagRule;
	}

	public String getTagRule() {
		return tagRule;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public Integer getSource() {
		return source;
	}

	public void setFrequencyUpdate(String frequencyUpdate) {
		this.frequencyUpdate = frequencyUpdate;
	}

	public String getFrequencyUpdate() {
		return frequencyUpdate;
	}
}