/**
 * LY.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.wen.awenboot.dal.dataobject;


import com.wen.awenboot.dal.dataobject.base.BaseDomain;

/**
 * person memo
 * @author Wen
 * @since 2020-05-25
 */
public class Person extends BaseDomain {
	// 
	private Integer id;
	// name memo
	private String name;
	// age
	private Integer age;
	// address
	private String address;

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getAge() {
		return age;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress() {
		return address;
	}
}