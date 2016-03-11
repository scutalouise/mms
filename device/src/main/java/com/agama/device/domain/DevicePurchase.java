package com.agama.device.domain;

import java.io.Serializable;
import java.util.Date;

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
import com.agama.common.enumbean.MaintainWayEnum;
import com.agama.common.enumbean.SecondDeviceType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@DynamicInsert
@DynamicUpdate
public class DevicePurchase implements Serializable {

	private static final long serialVersionUID = 8620266446701413958L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable = false, nullable = false)
	private Integer id;
	@Version
	@JsonIgnore
	private Integer version;
	private String name;
	@Column(nullable = false)
	private Integer quantity;
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date purchaseDate;
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date warrantyDate;
	@Column(nullable = false)
	private int isPurchase;
	@Column(nullable = false)
	private int deviceInventoryId;
	@Column(nullable = false)
	private Integer orgId;
	@Transient
	private String orgName;
	private String otherNote;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	@Column(nullable = false)
	private Date updateTime;
	//2016-02-24支持显示品牌名字，新增一个临时的字段；
	private Integer brandId;
	@Transient
	private String brandName;
	@Transient
	private FirstDeviceType firstDeviceType;
	@Transient
	private SecondDeviceType secondDeviceType;
	//2016-03-01  新增以下；
	private Integer supplyId;               //供应商ID
	@Transient
	private String supplyName;              //供应商名称，临时的字段
	private Integer maintainOrgId;          //运维组织ID
	@Transient
	private String maintainOrgName;         //运维组织名称，临时的字段
	@Enumerated(EnumType.STRING)
	private MaintainWayEnum maintainWay;    //运维方式
	private String purchaseOrderNum;
	
	public DevicePurchase() {
		
	}
	public DevicePurchase(Integer id, String name, Integer quantity, Date purchaseDate, Date warrantyDate, int isPurchase,
			 String orgName, String otherNote, Date updateTime, String brandName,
			FirstDeviceType firstDeviceType, SecondDeviceType secondDeviceType,MaintainWayEnum maintainWay,String purchaseOrderNum) {
		this.id = id;
		this.name = name;
		this.quantity = quantity;
		this.purchaseDate = purchaseDate;
		this.warrantyDate = warrantyDate;
		this.isPurchase = isPurchase;
		this.orgName = orgName;
		this.otherNote = otherNote;
		this.updateTime = updateTime;
		this.brandName = brandName;
		this.firstDeviceType = firstDeviceType;
		this.secondDeviceType = secondDeviceType;
		this.maintainWay = maintainWay;
		this.purchaseOrderNum = purchaseOrderNum;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public Date getWarrantyDate() {
		return warrantyDate;
	}

	public void setWarrantyDate(Date warrantyDate) {
		this.warrantyDate = warrantyDate;
	}

	public int getIsPurchase() {
		return isPurchase;
	}

	public void setIsPurchase(int isPurchase) {
		this.isPurchase = isPurchase;
	}

	public int getDeviceInventoryId() {
		return deviceInventoryId;
	}

	public void setDeviceInventoryId(int deviceInventoryId) {
		this.deviceInventoryId = deviceInventoryId;
	}

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

	public String getOtherNote() {
		return otherNote;
	}

	public void setOtherNote(String otherNote) {
		this.otherNote = otherNote;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
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
	public Integer getSupplyId() {
		return supplyId;
	}
	public void setSupplyId(Integer supplyId) {
		this.supplyId = supplyId;
	}
	public String getSupplyName() {
		return supplyName;
	}
	public void setSupplyName(String supplyName) {
		this.supplyName = supplyName;
	}
	public Integer getMaintainOrgId() {
		return maintainOrgId;
	}
	public void setMaintainOrgId(Integer maintainOrgId) {
		this.maintainOrgId = maintainOrgId;
	}
	public String getMaintainOrgName() {
		return maintainOrgName;
	}
	public void setMaintainOrgName(String maintainOrgName) {
		this.maintainOrgName = maintainOrgName;
	}
	public MaintainWayEnum getMaintainWay() {
		return maintainWay;
	}
	public void setMaintainWay(MaintainWayEnum maintainWay) {
		this.maintainWay = maintainWay;
	}
	public String getPurchaseOrderNum() {
		return purchaseOrderNum;
	}
	public void setPurchaseOrderNum(String purchaseOrderNum) {
		this.purchaseOrderNum = purchaseOrderNum;
	}
}
