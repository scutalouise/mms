package com.agama.pemm.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.agama.authority.entity.BaseDomain;
import com.agama.common.enumbean.AirConditioningRunState;
import com.agama.common.enumbean.AirConditioningUnitState;
@Entity
public class AcStatus extends BaseDomain{

	/**
	 * long
	 */
	private static final long serialVersionUID = 1L;
	@Transient
	private String name;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	private Double indoorTemperature;//室内温度
	private Double indoorHumidity;//室内湿度
	private Double outdoorTemperature;//室外温度
	
	private AirConditioningRunState runState;//运行状态
	@Transient
	private Integer runStateOrdinal;
	private AirConditioningUnitState unitState; //机组状态
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="deviceId")
	private Device device;
	private Integer actionIndex;//动作
	private Integer alarmIndex;//报警
	private Date collectTime; //获取数据时间
	@Transient
	private Integer deviceId;
	@Transient
	private Integer unitStateOrdinal;
	@Transient
	private Integer deviceIndex;
	
	public Integer getUnitStateOrdinal() {
		return unitStateOrdinal;
	}
	public void setUnitStateOrdinal(Integer unitStateOrdinal) {
		this.unitStateOrdinal = unitStateOrdinal;
	}
	
	public Double getIndoorTemperature() {
		return indoorTemperature;
	}
	public void setIndoorTemperature(Double indoorTemperature) {
		this.indoorTemperature = indoorTemperature;
	}
	public Double getIndoorHumidity() {
		return indoorHumidity;
	}
	public void setIndoorHumidity(Double indoorHumidity) {
		this.indoorHumidity = indoorHumidity;
	}
	public Double getOutdoorTemperature() {
		return outdoorTemperature;
	}
	public void setOutdoorTemperature(Double outdoorTemperature) {
		this.outdoorTemperature = outdoorTemperature;
	}
	
	public AirConditioningRunState getRunState() {
		return runState;
	}
	public void setRunState(AirConditioningRunState runState) {
		this.runState = runState;
	}
	public AirConditioningUnitState getUnitState() {
		return unitState;
	}
	public void setUnitState(AirConditioningUnitState unitState) {
		this.unitState = unitState;
	}
	public Integer getActionIndex() {
		return actionIndex;
	}
	public void setActionIndex(Integer actionIndex) {
		this.actionIndex = actionIndex;
	}
	public Integer getAlarmIndex() {
		return alarmIndex;
	}
	public void setAlarmIndex(Integer alarmIndex) {
		this.alarmIndex = alarmIndex;
	}
	
	
	
	public Date getCollectTime() {
		return collectTime;
	}
	public void setCollectTime(Date collectTime) {
		this.collectTime = collectTime;
	}
	public Device getDevice() {
		return device;
	}
	public void setDevice(Device device) {
		this.device = device;
	}
	public Integer getRunStateOrdinal() {
		return runStateOrdinal;
	}
	public void setRunStateOrdinal(Integer runStateOrdinal) {
		this.runStateOrdinal = runStateOrdinal;
	}
	public Integer getDeviceIndex() {
		return deviceIndex;
	}
	public void setDeviceIndex(Integer deviceIndex) {
		this.deviceIndex = deviceIndex;
	}
	public Integer getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	} 
	
	
	

}
