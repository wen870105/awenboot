/**
 * created by Wen.
 */
package com.wen.awenboot.domain;

import com.wen.awenboot.domain.base.BaseDomain;

import java.util.Date;

/**
 * api接口调用次数表
 * @author Wen
 * @since 2021-01-08
 */
public class MiguTagApiDetailCnt extends BaseDomain {
	// 
	private Long id;
	// api名称
	private String tagKey;
	// 调用次数
	private Long cnt;
	// 日期
	private Date createDate;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setTagKey(String tagKey) {
		this.tagKey = tagKey;
	}

	public String getTagKey() {
		return tagKey;
	}

	public void setCnt(Long cnt) {
		this.cnt = cnt;
	}

	public Long getCnt() {
		return cnt;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getCreateDate() {
		return createDate;
	}
}