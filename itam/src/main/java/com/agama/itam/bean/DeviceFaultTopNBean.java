package com.agama.itam.bean;

import java.util.Date;

public class DeviceFaultTopNBean {
	
	private Integer orgId;
	private String orgName;
	private Date date;
	private String deviceType;
	private Number quantity;
	
	public Integer getOrgId() {
		return orgId;
	}
	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public Number getQuantity() {
		return quantity;
	}
	public void setQuantity(Number quantity) {
		this.quantity = quantity;
	}
    
	
}
