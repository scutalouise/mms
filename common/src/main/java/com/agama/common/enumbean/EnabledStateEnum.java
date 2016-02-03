package com.agama.common.enumbean;

/**
 * @Description:启用状态枚举 实体类
 * @Author:ranjunfeng
 * @Since :2015年12月24日 下午1:37:24
 */
public enum EnabledStateEnum {
	ENABLED(0,"启用"),DISABLED(1,"禁用");
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
	private EnabledStateEnum(int id, String name) {
		this.id = id;
		this.name = name;
	}
	

}
