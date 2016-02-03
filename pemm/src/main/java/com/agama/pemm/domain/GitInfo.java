package com.agama.pemm.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.agama.authority.entity.BaseDomain;
import com.agama.common.domain.ServerStateEnum;
import com.agama.common.domain.StateEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@DynamicUpdate @DynamicInsert
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class GitInfo extends BaseDomain{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4681750304187341157L;
	private String ip;
	private Integer areaInfoId;
	private int managerId;
	private String managerName;
	private String vendor;
	private String brand;
	@Column(columnDefinition="DATE")
	private Date buyTime;
	private String name;
	private int enabled;
	private int status;
	private String location;
	private Integer organizationId;
	private String organizationName;
	@Enumerated(EnumType.STRING)
	private StateEnum currentState;
	
	@Transient
	private List<UpsStatus> upsStatusList;
	@Transient
	private List<ThStatus> thStatusList;
	@Transient
	private List<AcStatus> acStatusList;
	
	@Transient
	private List<SwitchInputStatus> waterStatusList;
	@Transient
	private List<SwitchInputStatus> smokeStatusList;
	@Column(length=5000)
	private String remark;
	@OneToMany(mappedBy = "gitInfo", cascade = CascadeType.ALL , fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Device> devices;
	private ServerStateEnum serverState;
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	
	
	public Integer getAreaInfoId() {
		return areaInfoId;
	}
	public void setAreaInfoId(Integer areaInfoId) {
		this.areaInfoId = areaInfoId;
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
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	
	
	public Integer getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(Integer organizationId) {
		this.organizationId = organizationId;
	}
	public String getOrganizationName() {
		return organizationName;
	}
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public List<Device> getDevices() {
		return devices;
	}
	public void setDevices(List<Device> devices) {
		this.devices = devices;
	}
	public List<UpsStatus> getUpsStatusList() {
		return upsStatusList;
	}
	public void setUpsStatusList(List<UpsStatus> upsStatusList) {
		this.upsStatusList = upsStatusList;
	}
	public List<ThStatus> getThStatusList() {
		return thStatusList;
	}
	public void setThStatusList(List<ThStatus> thStatusList) {
		this.thStatusList = thStatusList;
	}
	
	
	
	public List<AcStatus> getAcStatusList() {
		return acStatusList;
	}
	public void setAcStatusList(List<AcStatus> acStatusList) {
		this.acStatusList = acStatusList;
	}
	public List<SwitchInputStatus> getWaterStatusList() {
		return waterStatusList;
	}
	public void setWaterStatusList(List<SwitchInputStatus> waterStatusList) {
		this.waterStatusList = waterStatusList;
	}
	public List<SwitchInputStatus> getSmokeStatusList() {
		return smokeStatusList;
	}
	public void setSmokeStatusList(List<SwitchInputStatus> smokeStatusList) {
		this.smokeStatusList = smokeStatusList;
	}
	public ServerStateEnum getServerState() {
		return serverState;
	}
	public void setServerState(ServerStateEnum serverState) {
		this.serverState = serverState;
	}
	public StateEnum getCurrentState() {
		return currentState;
	}
	public void setCurrentState(StateEnum currentState) {
		this.currentState = currentState;
	}
	
	

	
	
}
