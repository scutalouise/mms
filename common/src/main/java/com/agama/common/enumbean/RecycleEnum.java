package com.agama.common.enumbean;

/**
 * @Description:回收站状态
 * @Author:杨远高
 * @Since :2016年1月29日 上午9:44:07
 */
public enum RecycleEnum {
	YES(0,"已还原"), NO(1,"未还原");
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
	private RecycleEnum(int id, String name) {
		this.id = id;
		this.name = name;
	}
}
