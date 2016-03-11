package com.agama.device.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.agama.common.enumbean.FirstDeviceType;
import com.agama.common.enumbean.SecondDeviceType;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@DynamicInsert
@DynamicUpdate
public class DeviceInventory implements Serializable {

	private static final long serialVersionUID = 1296318501886858516L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable = false, nullable = false)
	private Integer id;
	@Version
	@JsonIgnore
	private Integer version;
	@Column(nullable = false)
	private int quantity;
	@Column(nullable = false)
	private int scrapQuantity;
	@Column(nullable = false)
	private int freeQuantity;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private FirstDeviceType firstDeviceType;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private SecondDeviceType secondDeviceType;
	@Column(nullable = false)
	private int brandId;
	@Transient
	private String brandName;
	private String otherNote;

	public DeviceInventory() {
		
	}
	
	public DeviceInventory(Integer id, int quantity, int scrapQuantity, int freeQuantity,
			FirstDeviceType firstDeviceType, SecondDeviceType secondDeviceType, int brandId, String brandName,
			String otherNote) {
		this.id = id;
		this.quantity = quantity;
		this.scrapQuantity = scrapQuantity;
		this.freeQuantity = freeQuantity;
		this.firstDeviceType = firstDeviceType;
		this.secondDeviceType = secondDeviceType;
		this.brandId = brandId;
		this.brandName = brandName;
		this.otherNote = otherNote;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getScrapQuantity() {
		return scrapQuantity;
	}

	public void setScrapQuantity(int scrapQuantity) {
		this.scrapQuantity = scrapQuantity;
	}

	public int getFreeQuantity() {
		return freeQuantity;
	}

	public void setFreeQuantity(int freeQuantity) {
		this.freeQuantity = freeQuantity;
	}

	public FirstDeviceType getFirstDeviceType() {
		return firstDeviceType;
	}

	public void setFirstDeviceType(FirstDeviceType firstDeviceType) {
		this.firstDeviceType = firstDeviceType;
	}

	public SecondDeviceType getSecondDeviceType() {
		return secondDeviceType;
	}

	public void setSecondDeviceType(SecondDeviceType secondDeviceType) {
		this.secondDeviceType = secondDeviceType;
	}

	public int getBrandId() {
		return brandId;
	}

	public void setBrandId(int brandId) {
		this.brandId = brandId;
	}

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
