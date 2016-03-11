package com.agama.itam.bean;

import java.util.Date;

public class SupplyTopNBean {

	private Integer brandId;
	private String brandName;
	private String supplyName;
	private Date recordTime;
	private Number quantity;
	
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
	public String getSupplyName() {
		return supplyName;
	}
	public void setSupplyName(String supplyName) {
		this.supplyName = supplyName;
	}
	public Date getRecordTime() {
		return recordTime;
	}
	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}
	public Number getQuantity() {
		return quantity;
	}
	public void setQuantity(Number quantity) {
		this.quantity = quantity;
	}
	
	
}
