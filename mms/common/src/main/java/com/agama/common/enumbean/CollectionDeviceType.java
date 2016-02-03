package com.agama.common.enumbean;

/**
 * @Description:采集设备枚举实体类
 * @Author:ranjunfeng
 * @Since :2015年12月17日 上午10:53:35
 */
public enum CollectionDeviceType {
	GIT("GIT网关"),
	COLLECTOR("采集器"),
	HANDTERMINAL("手持终端");
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	private CollectionDeviceType(String name) {
		 this.name=name;
	}

}
