package com.agama.itam.bean;

import java.util.Date;

public class MaintenanceTopNBean {

	private Object problemId;
	private String deviceType;
	private Date recordTime;
	private Object time;
	private Integer brandId;
	private String brandName;
	private String MaintenanceName;
	private Number quantity;
	
	public Object getProblemId() {
		return problemId;
	}
	public void setProblemId(Object problemId) {
		this.problemId = problemId;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public Date getRecordTime() {
		return recordTime;
	}
	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}
	public Object getTime() {
		return time;
	}
	public void setTime(Object time) {
		this.time = time;
	}
	public Integer getBrandId() {
		return brandId;
	}
	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getMaintenanceName() {
		return MaintenanceName;
	}
	public void setMaintenanceName(String maintenanceName) {
		MaintenanceName = maintenanceName;
	}
	public Number getQuantity() {
		return quantity;
	}
	public void setQuantity(Number quantity) {
		this.quantity = quantity;
	}
	
	
}
