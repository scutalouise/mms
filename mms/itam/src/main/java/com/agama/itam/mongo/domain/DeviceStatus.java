package com.agama.itam.mongo.domain;

import java.util.Date;
import java.util.Map;

import javax.persistence.Column;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @Description:设备状态mongodb设备状态实体
 * @Author:ranjunfeng
 * @Since :2016年1月13日 上午9:34:56
 */
@Document
public class DeviceStatus {
	@Id
	private String id;

	
	private Map<String,Object> dataInfo;
	private Date checkTime;
	private String deviceType;
	private String identifier;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Map<String,Object> getDataInfo() {
		return dataInfo;
	}
	public void setDataInfo(Map<String,Object> dataInfo) {
		this.dataInfo = dataInfo;
	}
	public Date getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	

}
