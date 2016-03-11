package com.agama.device.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.agama.authority.entity.BaseDomain;
import com.agama.common.enumbean.EnabledStateEnum;
import com.agama.common.enumbean.FirstDeviceType;
import com.agama.common.enumbean.SecondDeviceType;
import com.agama.common.enumbean.StatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @Description:设备告警条件实体
 * @Author:ranjunfeng
 * @Since :2016年3月9日 上午9:25:52
 */
@Entity
public class DeviceAlarmCondition extends BaseDomain{
	/**
	 * 告警条件名称
	 */
	private String name;
	/**
	 * 告警设备类型
	 */
	@Enumerated(EnumType.STRING)
	private FirstDeviceType alarmDeviceType;
	/**
     * 告警设备
	 */
	@Enumerated(EnumType.STRING)
	private SecondDeviceType alarmDevice;
	
	
	/**
	 * 删除状态
	 */
	@Enumerated(EnumType.STRING)
	private StatusEnum status;
	
	/**
	 * 是否启用
	 */
	@Enumerated(EnumType.STRING)
	private EnabledStateEnum enabled;
	
	
	/**
	 * 告警模板外键
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "alarmTemplateId")
	private AlarmTemplate alarmTemplate;
	
	
	/**
	 * 告警等级外键
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "alarmLevelId")
	private AlarmLevel alarmLevel;
	
	
	@OneToMany(mappedBy="deviceAlarmCondition",cascade= CascadeType.MERGE , fetch = FetchType.LAZY)
	@JsonIgnore
	private List<DeviceAlarmRule> deviceAlarmRules;


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public FirstDeviceType getAlarmDeviceType() {
		return alarmDeviceType;
	}


	public void setAlarmDeviceType(FirstDeviceType alarmDeviceType) {
		this.alarmDeviceType = alarmDeviceType;
	}


	public SecondDeviceType getAlarmDevice() {
		return alarmDevice;
	}


	public void setAlarmDevice(SecondDeviceType alarmDevice) {
		this.alarmDevice = alarmDevice;
	}


	public StatusEnum getStatus() {
		return status;
	}


	public void setStatus(StatusEnum status) {
		this.status = status;
	}


	public EnabledStateEnum getEnabled() {
		return enabled;
	}


	public void setEnabled(EnabledStateEnum enabled) {
		this.enabled = enabled;
	}


	public AlarmTemplate getAlarmTemplate() {
		return alarmTemplate;
	}


	public void setAlarmTemplate(AlarmTemplate alarmTemplate) {
		this.alarmTemplate = alarmTemplate;
	}


	public AlarmLevel getAlarmLevel() {
		return alarmLevel;
	}


	public void setAlarmLevel(AlarmLevel alarmLevel) {
		this.alarmLevel = alarmLevel;
	}


	public List<DeviceAlarmRule> getDeviceAlarmRules() {
		return deviceAlarmRules;
	}


	public void setDeviceAlarmRules(List<DeviceAlarmRule> deviceAlarmRules) {
		this.deviceAlarmRules = deviceAlarmRules;
	}
	
	
	
}
