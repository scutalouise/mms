package com.agama.pemm.domain;

import javax.persistence.Entity;

import com.agama.authority.entity.BaseDomain;

/**
 * @Description:放电计划实体类
 * @Author:ranjunfeng
 * @Since :2015年11月25日 上午11:37:34
 */
@Entity
public class DischargeTask extends BaseDomain{

	/**
	 * long
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer deviceId; //放电设备ID
	private String scheduleName;//任务名称
	private String scheduleGroup;//任务组
	private Integer status;
	public Integer getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}
	
	public String getScheduleName() {
		return scheduleName;
	}
	public void setScheduleName(String scheduleName) {
		this.scheduleName = scheduleName;
	}
	public String getScheduleGroup() {
		return scheduleGroup;
	}
	public void setScheduleGroup(String scheduleGroup) {
		this.scheduleGroup = scheduleGroup;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	

}
