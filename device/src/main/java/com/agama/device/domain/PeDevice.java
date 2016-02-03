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
import com.agama.common.domain.StateEnum;
import com.agama.common.enumbean.DeviceInterfaceType;
import com.agama.common.enumbean.DeviceType;
import com.agama.common.enumbean.EnabledStateEnum;
import com.agama.common.enumbean.StatusEnum;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@DynamicInsert
@DynamicUpdate
public class PeDevice extends BaseDomain {

	private static final long serialVersionUID = 3984330794206576366L;
	private EnabledStateEnum enable;
	private StatusEnum status;
	private String identifier;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date manufactureDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date warrantyDate;
	private String name;
	private String remark;
	private String model;
	private Date updateTime;
	private Integer managerId;
	@Transient
	private String managerName;// 管理人员名字；
	private Integer roleId;
	@Transient
	private String roleName;// 角色名；
	private Integer purchaseId;
	@Transient
	private String purchaseName;// 购买批次名
	private Integer organizationId;
	@Transient
	private String organizationName;// 所在组织名；
	private Integer userDeviceTypeId;
	@Transient
	private String userDeviceTypeName;// 自定义分类名；
	private DeviceType dhDeviceType;
	private DeviceInterfaceType dhDeviceInterfaceType;
	private Integer dhDeviceIndex;
	private Integer alarmTemplateId;
	@Transient
	private String alarmTemplateName;// 告警模板名；
	private Integer collectDeviceId;
	@Transient
	private String collectionDeviceName;// 采集设备名；
	@Enumerated(EnumType.STRING)
	private StateEnum currentState;// 设备当前状态

	@Column(nullable = false)
	public EnabledStateEnum getEnable() {
		return enable;
	}

	public void setEnable(EnabledStateEnum enable) {
		this.enable = enable;
	}

	@Column(nullable = false)
	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}

	@Column(nullable = false, length = 32)
	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	@Temporal(TemporalType.DATE)
	public Date getManufactureDate() {
		return manufactureDate;
	}

	public void setManufactureDate(Date manufactureDate) {
		this.manufactureDate = manufactureDate;
	}

	@Temporal(TemporalType.DATE)
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

	@Column(length = 50)
	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	@Column(nullable = false)
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

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	@Column(nullable = false)
	public Integer getPurchaseId() {
		return purchaseId;
	}

	public void setPurchaseId(Integer purchaseId) {
		this.purchaseId = purchaseId;
	}

	@Column(nullable = false)
	public Integer getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Integer organizationId) {
		this.organizationId = organizationId;
	}

	@Column(nullable = false)
	public Integer getUserDeviceTypeId() {
		return userDeviceTypeId;
	}

	public void setUserDeviceTypeId(Integer userDeviceTypeId) {
		this.userDeviceTypeId = userDeviceTypeId;
	}

	@Column(nullable = false)
	public DeviceType getDhDeviceType() {
		return dhDeviceType;
	}

	public void setDhDeviceType(DeviceType dhDeviceType) {
		this.dhDeviceType = dhDeviceType;
	}

	@Column(nullable = false)
	public DeviceInterfaceType getDhDeviceInterfaceType() {
		return dhDeviceInterfaceType;
	}

	public void setDhDeviceInterfaceType(DeviceInterfaceType dhDeviceInterfaceType) {
		this.dhDeviceInterfaceType = dhDeviceInterfaceType;
	}

	@Column(nullable = false)
	public Integer getDhDeviceIndex() {
		return dhDeviceIndex;
	}

	public void setDhDeviceIndex(Integer dhDeviceIndex) {
		this.dhDeviceIndex = dhDeviceIndex;
	}

//	@Column(nullable = false)
	public Integer getAlarmTemplateId() {
		return alarmTemplateId;
	}

	public void setAlarmTemplateId(Integer alarmTemplateId) {
		this.alarmTemplateId = alarmTemplateId;
	}

	@Column(nullable = false)
	public Integer getCollectDeviceId() {
		return collectDeviceId;
	}

	public void setCollectDeviceId(Integer collectDeviceId) {
		this.collectDeviceId = collectDeviceId;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
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

	public String getAlarmTemplateName() {
		return alarmTemplateName;
	}

	public void setAlarmTemplateName(String alarmTemplateName) {
		this.alarmTemplateName = alarmTemplateName;
	}

	public String getCollectionDeviceName() {
		return collectionDeviceName;
	}

	public void setCollectionDeviceName(String collectionDeviceName) {
		this.collectionDeviceName = collectionDeviceName;
	}

	public StateEnum getCurrentState() {
		return currentState;
	}

	public void setCurrentState(StateEnum currentState) {
		this.currentState = currentState;
	}

}
