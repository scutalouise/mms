package com.agama.pemm.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.agama.authority.entity.BaseDomain;
import com.agama.common.domain.StateEnum;
import com.agama.common.enumbean.DeviceInterfaceType;

@Entity
public class AlarmLog extends BaseDomain{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5026894469462294487L;
	private  Date collectTime;
	private String content;
	private StateEnum alarmType;
	private DeviceInterfaceType deviceInterfaceType;
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="deviceId")
	private Device device;
	private Integer status;
	@Transient
	private String deviceName;
	@Transient
	private String gitInfoIp;
	@Transient
	private Integer runState;
	@Transient
	private Integer deviceTypeIndex;
	@Transient
	private String organizationName;

	public Date getCollectTime() {
		return collectTime;
	}
	public void setCollectTime(Date collectTime) {
		this.collectTime = collectTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public StateEnum getAlarmType() {
		return alarmType;
	}
	public void setAlarmType(StateEnum alarmType) {
		this.alarmType = alarmType;
	}
	public DeviceInterfaceType getDeviceInterfaceType() {
		return deviceInterfaceType;
	}
	public void setDeviceInterfaceType(DeviceInterfaceType deviceInterfaceType) {
		this.deviceInterfaceType = deviceInterfaceType;
	}
	public Device getDevice() {
		return device;
	}
	public void setDevice(Device device) {
		this.device = device;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public String getGitInfoIp() {
		return gitInfoIp;
	}
	public void setGitInfoIp(String gitInfoIp) {
		this.gitInfoIp = gitInfoIp;
	}
	public Integer getRunState() {
		return runState;
	}
	public void setRunState(Integer runState) {
		this.runState = runState;
	}
	public Integer getDeviceTypeIndex() {
		return deviceTypeIndex;
	}
	public void setDeviceTypeIndex(Integer deviceTypeIndex) {
		this.deviceTypeIndex = deviceTypeIndex;
	}
	public String getOrganizationName() {
		return organizationName;
	}
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	
	
	
	
}
