package com.agama.pemm.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Transient;

import com.agama.authority.entity.BaseDomain;

/**
 * @Description:开关输入量状态信息实体
 * @Author:ranjunfeng
 * @Since :2015年10月15日 下午3:22:51
 */
@Entity
public class SwitchInputStatus extends BaseDomain{
	/**
	 * long
	 */
	private static final long serialVersionUID = -1944042589427304675L;
	private String name;//名称
	private Integer inputSignal; //信号
	private Date collectTime; //获取数据时间
	private Integer currentState;//当前状态
	private Integer status; //删除状态
	private Integer deviceId;
	@Transient
	private Integer deviceIndex;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getInputSignal() {
		return inputSignal;
	}
	public void setInputSignal(Integer inputSignal) {
		this.inputSignal = inputSignal;
	}
	public Date getCollectTime() {
		return collectTime;
	}
	public void setCollectTime(Date collectTime) {
		this.collectTime = collectTime;
	}
	
	
	
	public Integer getCurrentState() {
		return currentState;
	}
	public void setCurrentState(Integer currentState) {
		this.currentState = currentState;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	
	public Integer getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}
	public Integer getDeviceIndex() {
		return deviceIndex;
	}
	public void setDeviceIndex(Integer deviceIndex) {
		this.deviceIndex = deviceIndex;
	}
	


}
