package com.agama.pemm.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.agama.authority.entity.BaseDomain;
import com.agama.common.domain.StateEnum;
import com.agama.common.enumbean.DeviceInterfaceType;
import com.agama.common.enumbean.DeviceType;
import com.agama.pemm.bean.DeviceStateRecord;
import com.alibaba.fastjson.annotation.JSONCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Device extends BaseDomain {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1831449158942995063L;
	@Enumerated(EnumType.ORDINAL)
	private DeviceType deviceType;
	private int managerId;
	private String managerName;
	private String vendor;
	private String brand;
	private Date buyTime;
	private String name;
	private int enabled;
	private int status;
	
	private int deviceIndex;
	private StateEnum currentState; 
	private String stateDetails; 
	@Column(length=5000)
	private String remark;
	@ManyToOne( fetch = FetchType.LAZY)
	@JoinColumn(name = "gitInfoId")
	@JsonIgnoreProperties({"id","buyTime","remark","serverState","currentState","upsStatusList","thStatusList","waterStatusList","smokeStatusList"})
	private GitInfo gitInfo;   
	
	@OneToMany(mappedBy="device",cascade= {CascadeType.ALL} , fetch = FetchType.LAZY)
	@JsonIgnore
	private List<UpsStatus> upsStatuses;
	
	private Integer alarmTemplateId;
	private String alarmTemplateName;
	private DeviceInterfaceType deviceInterfaceType; //设备接口类型
	
	@Transient
	private DeviceStateRecord deviceStateRecord;
	public DeviceType getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(DeviceType deviceType) {
		this.deviceType = deviceType;
	}

	public int getManagerId() {
		return managerId;
	}

	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}

	
	
	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public Date getBuyTime() {
		return buyTime;
	}

	public void setBuyTime(Date buyTime) {
		this.buyTime = buyTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getEnabled() {
		return enabled;
	}

	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getDeviceIndex() {
		return deviceIndex;
	}

	public void setDeviceIndex(int deviceIndex) {
		this.deviceIndex = deviceIndex;
	}
	
	
	

	public StateEnum getCurrentState() {
		return currentState;
	}

	public void setCurrentState(StateEnum currentState) {
		this.currentState = currentState;
	}

	public String getStateDetails() {
		return stateDetails;
	}

	public void setStateDetails(String stateDetails) {
		this.stateDetails = stateDetails;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public GitInfo getGitInfo() {
		return gitInfo;
	}

	public void setGitInfo(GitInfo gitInfo) {
		this.gitInfo = gitInfo;
	}

	public DeviceStateRecord getDeviceStateRecord() {
		return deviceStateRecord;
	}

	public void setDeviceStateRecord(DeviceStateRecord deviceStateRecord) {
		this.deviceStateRecord = deviceStateRecord;
	}

	public List<UpsStatus> getUpsStatuses() {
		return upsStatuses;
	}

	public void setUpsStatuses(List<UpsStatus> upsStatuses) {
		this.upsStatuses = upsStatuses;
	}

	public Integer getAlarmTemplateId() {
		return alarmTemplateId;
	}

	public void setAlarmTemplateId(Integer alarmTemplateId) {
		this.alarmTemplateId = alarmTemplateId;
	}

	public String getAlarmTemplateName() {
		return alarmTemplateName;
	}

	public void setAlarmTemplateName(String alarmTemplateName) {
		this.alarmTemplateName = alarmTemplateName;
	}

	public DeviceInterfaceType getDeviceInterfaceType() {
		return deviceInterfaceType;
	}

	public void setDeviceInterfaceType(DeviceInterfaceType deviceInterfaceType) {
		this.deviceInterfaceType = deviceInterfaceType;
	}

	
	
	

}
