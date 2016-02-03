package com.agama.pemm.bean;

import java.util.Date;

public class UpsChartBean {
	private String inputVoltage;
	private String outputVoltage;
	private String upsLoad;
	private double batteryVoltage;
	private Date collectTime;
	private Integer upsType;
	public String getInputVoltage() {
		return inputVoltage;
	}
	public void setInputVoltage(String inputVoltage) {
		this.inputVoltage = inputVoltage;
	}
	public String getOutputVoltage() {
		return outputVoltage;
	}
	public void setOutputVoltage(String outputVoltage) {
		this.outputVoltage = outputVoltage;
	}
	public String getUpsLoad() {
		return upsLoad;
	}
	public void setUpsLoad(String upsLoad) {
		this.upsLoad = upsLoad;
	}
	public double getBatteryVoltage() {
		return batteryVoltage;
	}
	public void setBatteryVoltage(double batteryVoltage) {
		this.batteryVoltage = batteryVoltage;
	}
	public Date getCollectTime() {
		return collectTime;
	}
	public void setCollectTime(Date collectTime) {
		this.collectTime = collectTime;
	}
	public Integer getUpsType() {
		return upsType;
	}
	public void setUpsType(Integer upsType) {
		this.upsType = upsType;
	}
	

}
