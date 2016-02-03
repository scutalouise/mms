package com.agama.common.domain;

public enum StateEnum {
	good(0,"正常"),warning(1,"告警"),error(2,"异常");
	
	private StateEnum(int index, String remark) {
		this.index = index;
		this.remark = remark;
	}
	private int index;
	private String remark;
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	

}
