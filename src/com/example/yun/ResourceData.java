package com.example.yun;

import java.io.Serializable;

public class ResourceData extends BaseBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4059314637140562623L;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 编号
	 */
	private String number;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	
}
