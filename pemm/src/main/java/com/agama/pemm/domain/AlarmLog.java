package com.agama.pemm.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.agama.pemm.bean.AlarmType;
import com.agama.pemm.bean.DeviceType;

@Entity
public class AlarmLog extends BaseDomain{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5026894469462294487L;
	private  Date collectTime;
	private String content;
	private AlarmType alarmType;
	private DeviceType deviceType;
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
	public AlarmType getAlarmType() {
		return alarmType;
	}
	public void setAlarmType(AlarmType alarmType) {
		this.alarmType = alarmType;
	}
	public DeviceType getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(DeviceType deviceType) {
		this.deviceType = deviceType;
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

	
	
	
	
}
