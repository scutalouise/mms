package com.agama.device.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import com.agama.authority.entity.BaseDomain;
import com.agama.common.enumbean.DeviceUsedStateEnum;
import com.agama.common.enumbean.EnabledStateEnum;
import com.agama.common.enumbean.MaintainWayEnum;
import com.agama.common.enumbean.StatusEnum;
import com.agama.common.enumbean.UsedEnum;
import com.agama.common.enumbean.UsingStateEnum;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@DynamicInsert
@DynamicUpdate
public class UnintelligentDevice extends BaseDomain {

	private static final long serialVersionUID = -6847423963032001605L;
	@Column(nullable = false)
	private EnabledStateEnum enable;
	@Column(nullable = false)
	private StatusEnum status;
	@Column(nullable = false, length = 32)
	private String identifier;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	private Date manufactureDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	private Date warrantyDate;
	private String name;
	private String remark;
	@Column(length = 50)
	private String model;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	@Column(nullable = false)
	private Date updateTime;
	private Integer managerId;
	@Transient
	private String managerName;// 管理人员名字；
	@Column(nullable = false)
	private Integer purchaseId;
	@Transient
	private String purchaseName;// 购买批次名
	@Column(nullable = false)
	private Integer organizationId;
	@Transient
	private String organizationName;// 所在组织名；
	private Integer userDeviceTypeId;
	@Transient
	private String userDeviceTypeName;// 自定义分类名；
	@Enumerated(EnumType.STRING)
	private UsedEnum obtainState; // 是否领用
	@DateTimeFormat(pattern = "yyyy-MM-dd HH-mm-ss")
	private Date obtainTime;
	@Enumerated(EnumType.STRING)
	private UsingStateEnum secondmentState; //是否是借调
	@Enumerated(EnumType.STRING)
	private UsingStateEnum scrappedState; //报废状态
	@Enumerated(EnumType.STRING)
	private DeviceUsedStateEnum deviceUsedState; //设备使用状态
	@Transient
	private String deviceUsedStateValue;
	private Integer obtainUserId;//领用人id
	@Transient
	private String obtainUserName; //领用人名称
	private Integer maintainOrgId;          //运维组织ID
	@Transient
	private String maintainOrgName;         //运维组织名称，临时的字段
	@Enumerated(EnumType.STRING)
	private MaintainWayEnum maintainWay;    //运维方式
	

	public EnabledStateEnum getEnable() {
		return enable;
	}

	public void setEnable(EnabledStateEnum enable) {
		this.enable = enable;
	}

	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public Date getManufactureDate() {
		return manufactureDate;
	}

	public void setManufactureDate(Date manufactureDate) {
		this.manufactureDate = manufactureDate;
	}

	public Date getWarrantyDate() {
		return warrantyDate;
	}

	public void setWarrantyDate(Date warrantyDate) {
		this.warrantyDate = warrantyDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getManagerId() {
		return managerId;
	}

	public void setManagerId(Integer managerId) {
		this.managerId = managerId;
	}

	public Integer getPurchaseId() {
		return purchaseId;
	}

	public void setPurchaseId(Integer purchaseId) {
		this.purchaseId = purchaseId;
	}

	public Integer getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Integer organizationId) {
		this.organizationId = organizationId;
	}

	public Integer getUserDeviceTypeId() {
		return userDeviceTypeId;
	}

	public void setUserDeviceTypeId(Integer userDeviceTypeId) {
		this.userDeviceTypeId = userDeviceTypeId;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getPurchaseName() {
		return purchaseName;
	}

	public void setPurchaseName(String purchaseName) {
		this.purchaseName = purchaseName;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getUserDeviceTypeName() {
		return userDeviceTypeName;
	}

	public void setUserDeviceTypeName(String userDeviceTypeName) {
		this.userDeviceTypeName = userDeviceTypeName;
	}

	public UsedEnum getObtainState() {
		return obtainState;
	}

	public void setObtainState(UsedEnum obtainState) {
		this.obtainState = obtainState;
	}

	public Date getObtainTime() {
		return obtainTime;
	}

	public void setObtainTime(Date obtainTime) {
		this.obtainTime = obtainTime;
	}

	public UsingStateEnum getSecondmentState() {
		return secondmentState;
	}

	public void setSecondmentState(UsingStateEnum secondmentState) {
		this.secondmentState = secondmentState;
	}

	public UsingStateEnum getScrappedState() {
		return scrappedState;
	}

	public void setScrappedState(UsingStateEnum scrappedState) {
		this.scrappedState = scrappedState;
	}

	public DeviceUsedStateEnum getDeviceUsedState() {
		return deviceUsedState;
	}

	
	
	public void setDeviceUsedState(DeviceUsedStateEnum deviceUsedState) {
		this.deviceUsedState = deviceUsedState;
	}
	
	

	public String getDeviceUsedStateValue() {
		return deviceUsedStateValue;
	}

	public void setDeviceUsedStateValue(String deviceUsedStateValue) {
		this.deviceUsedStateValue = deviceUsedStateValue;
	}

	public Integer getObtainUserId() {
		return obtainUserId;
	}

	public void setObtainUserId(Integer obtainUserId) {
		this.obtainUserId = obtainUserId;
	}

	public String getObtainUserName() {
		return obtainUserName;
	}

	public void setObtainUserName(String obtainUserName) {
		this.obtainUserName = obtainUserName;
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
	
}
