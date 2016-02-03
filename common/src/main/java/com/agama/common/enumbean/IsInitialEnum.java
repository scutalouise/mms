package com.agama.common.enumbean;

public enum IsInitialEnum {
	INIT(0, "初始化"), UNINIT(1, "没有初始化");

	private int id;
	private String name;

	private IsInitialEnum(int id, String name) {
		this.id = id;
		this.name = name;
	}

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

}
