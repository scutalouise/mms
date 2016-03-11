package com.agama.itam.bean;

import java.util.Date;

public class DeviceAccountsChartBean {

	private Date purchaseDate;
	private String brandName;
	private String deviceType;
	private Integer previousQuantity;
	private Integer inflowQuantity;
	private Number outflowQuantity;
	private Integer scrapQuantity;
	private String otherNote;
	
	public Date getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public Integer getPreviousQuantity() {
		return previousQuantity;
	}
	public void setPreviousQuantity(Integer previousQuantity) {
		this.previousQuantity = previousQuantity;
	}
	public Integer getInflowQuantity() {
		return inflowQuantity;
	}
	public void setInflowQuantity(Integer inflowQuantity) {
		this.inflowQuantity = inflowQuantity;
	}
	public Number getOutflowQuantity() {
		return outflowQuantity;
	}
	public void setOutflowQuantity(Number outflowQuantity) {
		this.outflowQuantity = outflowQuantity;
	}
	public Integer getScrapQuantity() {
		return scrapQuantity;
	}
	public void setScrapQuantity(Integer scrapQuantity) {
		this.scrapQuantity = scrapQuantity;
	}
	public String getOtherNote() {
		return otherNote;
	}
	public void setOtherNote(String otherNote) {
		this.otherNote = otherNote;
	}
	
}
