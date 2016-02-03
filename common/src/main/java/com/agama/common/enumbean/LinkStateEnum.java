package com.agama.common.enumbean;

public enum LinkStateEnum {
	NORMAL(0,"正常"),LOSE(1,"链接丢失");
	
	private LinkStateEnum(int id, String remark) {
		this.id = id;
		this.remark = remark;
	}
	private int id;
	private String remark;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
