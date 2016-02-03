package com.agama.common.enumbean;

public enum AirConditioningRunState {
	UNKNOWN(0,"未知"),SHUTDOWN(1,"精密"),RUN(2,"运行"),STANDBY(3,"待机"),LOCK(4,"锁定");
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

	private AirConditioningRunState(Integer id,String value) {
		this.id=id;
		this.value=value;
	}

	public static AirConditioningRunState getAirConditioningRunStateByOrdinal(int ordinal){
		for (AirConditioningRunState ac : AirConditioningRunState.values()) {
			if(ac.ordinal()==ordinal){
				return ac;
			}
		}
		return null;
	}
}
