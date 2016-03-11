package com.agama.common.enumbean;

public enum TemplateTypeEnum {
	MONITORINGALARM("监控告警"),
	DEVICEALARM("设备告警");
	
	private TemplateTypeEnum(String name) {
		this.name=name;
	}
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
