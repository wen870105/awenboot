/**
 * created by Wen.
 */
package com.migu.tsg.damportal.domain.base;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * 基础方法不建议修改,如需修改请修改对应的子类
 * @author wEn
 * @CreatDate: 2020-10-23 
 */
public class BaseDomain extends BaseQuery {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
