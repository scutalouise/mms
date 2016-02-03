package com.agama.device.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.agama.authority.entity.BaseDomain;
import com.agama.common.enumbean.FirstDeviceType;
import com.agama.common.enumbean.SecondDeviceType;

@Entity
@DynamicInsert
@DynamicUpdate
public class DeviceInventory extends BaseDomain {

	private static final long serialVersionUID = -7648518458616886563L;
	private int quantity;
	private int scrapQuantity;
	private int freeQuantity;
	private FirstDeviceType firstDeviceType;
	private SecondDeviceType secondDeviceType;
	private int brandId;
	private String brandName;
	private String otherNote;

	@Column(nullable = false)
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Column(nullable = false)
	public int getScrapQuantity() {
		return scrapQuantity;
	}

	public void setScrapQuantity(int scrapQuantity) {
		this.scrapQuantity = scrapQuantity;
	}

	@Column(nullable = false)
	public int getFreeQuantity() {
		return freeQuantity;
	}

	public void setFreeQuantity(int freeQuantity) {
		this.freeQuantity = freeQuantity;
	}

	@Column(nullable = false)
	public FirstDeviceType getFirstDeviceType() {
		return firstDeviceType;
	}

	public void setFirstDeviceType(FirstDeviceType firstDeviceType) {
		this.firstDeviceType = firstDeviceType;
	}

	@Column(nullable = false)
	public SecondDeviceType getSecondDeviceType() {
		return secondDeviceType;
	}

	public void setSecondDeviceType(SecondDeviceType secondDeviceType) {
		this.secondDeviceType = secondDeviceType;
	}

	@Column(nullable = false)
	public int getBrandId() {
		return brandId;
	}

	public void setBrandId(int brandId) {
		this.brandId = brandId;
	}

	@Transient
	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getOtherNote() {
		return otherNote;
	}

	public void setOtherNote(String otherNote) {
		this.otherNote = otherNote;
	}

}
