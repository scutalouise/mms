package com.agama.common.enumbean;

public enum AirConditioningUnitState {
	SINGLEMACHINE(0,"单机"),BACKMACHINE(1,"备机"),HOSTMACHINE(2,"主机");
	private Integer id;
	private String value;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	private AirConditioningUnitState(Integer id, String value) {
		this.id = id;
		this.value = value;
	}
	public static AirConditioningUnitState getAirConditioningUnitStateByOrdinal(int ordinal){
		for (AirConditioningUnitState acu : AirConditioningUnitState.values()) {
			if(acu.ordinal()==ordinal){
				return acu;
			}
		}
		return null;
	}
	

}
