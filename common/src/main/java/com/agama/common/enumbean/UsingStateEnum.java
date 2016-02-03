package com.agama.common.enumbean;

/**
 * @Description:使用状态枚举类
 * @Author:ranjunfeng
 * @Since :2015年12月24日 下午1:28:02
 */
public enum UsingStateEnum {
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
	private UsingStateEnum(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	

}
