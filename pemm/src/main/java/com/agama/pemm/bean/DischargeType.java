package com.agama.pemm.bean;

/**
 * @Description:放电类型枚举
 * @Author:ranjunfeng
 * @Since :2015年12月2日 上午9:38:01
 */
public enum DischargeType {

	AUTOMATIC("自动"), MANUAL("手动");
	private String name;
	private DischargeType(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
}
