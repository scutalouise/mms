package com.agama.common.enumbean;

public enum OperationType {
	GT(">"),EQ("="),GE(">="),LT("<"),LE("<="),BELONGTO("属于区间"),NOTBELONGTO("不属于区间");

	private String description;

	private OperationType(String description) {

		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
