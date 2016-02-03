package com.agama.device.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.agama.authority.entity.BaseDomain;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@DynamicInsert
@DynamicUpdate
public class DevicePurchase extends BaseDomain {

	private static final long serialVersionUID = 8620266446701413958L;
	private String name;
	private Integer quantity;
	private Date purchaseDate;
	private Date warrantyDate;
	private int isPurchase;
	private int deviceInventoryId;
	private Integer orgId;
	private String orgName;
	private String otherNote;
	private Date updateTime;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Column(nullable = false)
	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	public Date getWarrantyDate() {
		return warrantyDate;
	}

	public void setWarrantyDate(Date warrantyDate) {
		this.warrantyDate = warrantyDate;
	}

	@Column(nullable = false)
	public int getIsPurchase() {
		return isPurchase;
	}

	public void setIsPurchase(int isPurchase) {
		this.isPurchase = isPurchase;
	}

	@Column(nullable = false)
	public int getDeviceInventoryId() {
		return deviceInventoryId;
	}

	public void setDeviceInventoryId(int deviceInventoryId) {
		this.deviceInventoryId = deviceInventoryId;
	}

	@Column(nullable = false)
	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	@Transient
	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOtherNote() {
		return otherNote;
	}

	public void setOtherNote(String otherNote) {
		this.otherNote = otherNote;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	@Column(nullable = false)
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
