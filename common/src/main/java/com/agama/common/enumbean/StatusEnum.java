package com.agama.common.enumbean;

/**
 * @Description:状态枚举
 * @Author:ranjunfeng
 * @Since :2015年12月24日 上午11:51:43
 */
public enum StatusEnum {
	NORMAL(0,"正常"), DELETED(1,"删除");
	private int id;
	private String name;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	private StatusEnum(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	
}
