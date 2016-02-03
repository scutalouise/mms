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
import com.agama.common.enumbean.EnabledStateEnum;
import com.agama.common.enumbean.OSEnum;
import com.agama.common.enumbean.StatusEnum;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@DynamicInsert
@DynamicUpdate
public class HostDevice extends BaseDomain {

	private static final long serialVersionUID = -7596999854576529443L;
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
	private String ip;
	private String authorizationCode;
	private OSEnum oSType;
	private Integer oSBits;
	private String cpu;
	private String harddisk;
	private String motherboard;
	private String memory;
	private Integer alarmTemplateId;
	@Transient
	private String alarmTemplateName;// 告警模板名；
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

	public Integer getUserDeviceTypeId() {
		return userDeviceTypeId;
	}

	public void setUserDeviceTypeId(Integer userDeviceTypeId) {
		this.userDeviceTypeId = userDeviceTypeId;
	}

	@Column(length = 15)
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Column(length = 200)
	public String getAuthorizationCode() {
		return authorizationCode;
	}

	public void setAuthorizationCode(String authorizationCode) {
		this.authorizationCode = authorizationCode;
	}

	public OSEnum getoSType() {
		return oSType;
	}

	public void setoSType(OSEnum oSType) {
		this.oSType = oSType;
	}

	public Integer getoSBits() {
		return oSBits;
	}

	public void setoSBits(Integer oSBits) {
		this.oSBits = oSBits;
	}

	@Column(length = 100)
	public String getCpu() {
		return cpu;
	}

	public void setCpu(String cpu) {
		this.cpu = cpu;
	}

	@Column(length = 100)
	public String getHarddisk() {
		return harddisk;
	}

	public void setHarddisk(String harddisk) {
		this.harddisk = harddisk;
	}

	@Column(length = 100)
	public String getMotherboard() {
		return motherboard;
	}

	public void setMotherboard(String motherboard) {
		this.motherboard = motherboard;
	}

	@Column(length = 100)
	public String getMemory() {
		return memory;
	}

	public void setMemory(String memory) {
		this.memory = memory;
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

	
	public Integer getAlarmTemplateId() {
		return alarmTemplateId;
	}

	public void setAlarmTemplateId(Integer alarmTemplateId) {
		this.alarmTemplateId = alarmTemplateId;
	}

	public StateEnum getCurrentState() {
		return currentState;
	}

	public void setCurrentState(StateEnum currentState) {
		this.currentState = currentState;
	}

}
