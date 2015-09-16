package com.agama.pemm.bean;

import javax.persistence.Entity;

import com.agama.pemm.domain.BaseDomain;


public class DeviceStateRecord{
	private Integer gitInfoId;
	private DeviceType deviceType;
	private Long count;
	private Integer currentState;
	private String stateDetails;
	public Integer getGitInfoId() {
		return gitInfoId;
	}
	public void setGitInfoId(Integer gitInfoId) {
		this.gitInfoId = gitInfoId;
	}
	public DeviceType getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(DeviceType deviceType) {
		this.deviceType = deviceType;
	}
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	public Integer getCurrentState() {
		return currentState;
	}
	public void setCurrentState(Integer currentState) {
		this.currentState = currentState;
	}
	public String getStateDetails() {
		return stateDetails;
	}
	public void setStateDetails(String stateDetails) {
		this.stateDetails = stateDetails;
	}

}
