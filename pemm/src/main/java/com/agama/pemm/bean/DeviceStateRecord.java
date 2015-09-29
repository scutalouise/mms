package com.agama.pemm.bean;

import javax.persistence.Entity;

import com.agama.pemm.domain.BaseDomain;


public class DeviceStateRecord{
	private Integer gitInfoId;
	private DeviceType deviceType;
	private Long count;
	private StateEnum currentState;
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

}
