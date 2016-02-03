package com.agama.pemm.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Transient;

import com.agama.authority.entity.BaseDomain;
import com.agama.common.domain.StateEnum;

/**
 * @Description:温湿度状态信息实体类
 * @Author:ranjunfeng
 * @Since :2015年10月12日 上午10:00:15
 */
@Entity
public class ThStatus extends BaseDomain{
	/**
	 * long
	 */
	private static final long serialVersionUID = 4635898969131379607L;
	private String name; //名称
	private String interfaceType;//接口类型
	private String modelNumber;//设备型号
	private double temperature; //温度
	private double humidity;//湿度
	private Date collectTime; //获取数据时间
	private Integer status; //删除状态

	private String temperatureState; //温度状态

	private String humidityState; //湿度状态
	
	@Transient
	private Integer deviceIndex;

	private Integer deviceId;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	
	public String getInterfaceType() {
		return interfaceType;
	}
	public void setInterfaceType(String interfaceType) {
		this.interfaceType = interfaceType;
	}
	public String getModelNumber() {
		return modelNumber;
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
	public void setModelNumber(String modelNumber) {
		this.modelNumber = modelNumber;
	}
	public double getTemperature() {
		return temperature;
	}
	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}
	public double getHumidity() {
		return humidity;
	}
	public void setHumidity(double humidity) {
		this.humidity = humidity;
	}
	public Date getCollectTime() {
		return collectTime;
	}
	public void setCollectTime(Date collectTime) {
		this.collectTime = collectTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getTemperatureState() {
		return temperatureState;
	}
	public void setTemperatureState(String temperatureState) {
		this.temperatureState = temperatureState;
	}
	public String getHumidityState() {
		return humidityState;
	}
	public void setHumidityState(String humidityState) {
		this.humidityState = humidityState;
	}
	
	
	
	
	

}
