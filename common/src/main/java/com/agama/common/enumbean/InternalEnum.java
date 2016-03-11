package com.agama.common.enumbean;

/**
 * @Description:是否属于内部
 * @Author:杨远高
 * @Since :2016年3月1日 上午10:52:50
 */
public enum InternalEnum {
	INTERNAL(0,"内部"), OUTER(1,"外部");
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
	private InternalEnum(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	
}
