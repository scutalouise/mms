package com.agama.common.enumbean;

public enum OSBitsEnum {

	LOW(32, "32位"), NO(64, "64位");

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

	private OSBitsEnum(int id, String name) {
		this.id = id;
		this.name = name;
	}

}
