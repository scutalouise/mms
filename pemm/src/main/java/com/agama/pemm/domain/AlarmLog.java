package com.agama.pemm.domain;

import java.util.Date;

import javax.persistence.Entity;

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
	private Integer deviceId;
	
	
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
	public Integer getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}

	
	
	
	
}
