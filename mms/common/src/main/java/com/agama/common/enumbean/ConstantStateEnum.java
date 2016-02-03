package com.agama.common.enumbean;

/**
 * @Description:移交状态枚举
 * @Author:ranjunfeng
 * @Since :2015年12月30日 上午10:37:04
 */
public enum ConstantStateEnum {
	YES(0,"是"),NO(1,"否");
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
	private ConstantStateEnum(int id, String name) {
		this.id = id;
		this.name = name;
	}

}
