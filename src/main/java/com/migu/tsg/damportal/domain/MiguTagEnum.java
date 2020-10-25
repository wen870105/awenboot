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
public class MiguTagEnum extends BaseDomain{
	//
	@Id
	private Integer id;
	// 
	private String enumId;
	// 
	private String enumName;
	// 
	private String enumAlias;
	// 
	private String enumFather;
	// 
	private String creator;
	// 
	private Date createTime;
	// 
	private String modifier;
	// 
	private Date modifyTime;

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setEnumId(String enumId) {
		this.enumId = enumId;
	}

	public String getEnumId() {
		return enumId;
	}

	public void setEnumName(String enumName) {
		this.enumName = enumName;
	}

	public String getEnumName() {
		return enumName;
	}

	public void setEnumAlias(String enumAlias) {
		this.enumAlias = enumAlias;
	}

	public String getEnumAlias() {
		return enumAlias;
	}

	public void setEnumFather(String enumFather) {
		this.enumFather = enumFather;
	}

	public String getEnumFather() {
		return enumFather;
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
}