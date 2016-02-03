package com.agama.pemm.bean;

import com.agama.common.domain.StateEnum;
import com.agama.common.enumbean.DeviceInterfaceType;


public class DeviceStateRecord{
	private Integer gitInfoId;
	private DeviceInterfaceType deviceInterfaceType;
	private Long count;
	private StateEnum currentState;
	private String stateDetails;
	public Integer getGitInfoId() {
		return gitInfoId;
	}
	public void setGitInfoId(Integer gitInfoId) {
		this.gitInfoId = gitInfoId;
	}
	
	public DeviceInterfaceType getDeviceInterfaceType() {
		return deviceInterfaceType;
	}
	public void setDeviceInterfaceType(DeviceInterfaceType deviceInterfaceType) {
		this.deviceInterfaceType = deviceInterfaceType;
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
