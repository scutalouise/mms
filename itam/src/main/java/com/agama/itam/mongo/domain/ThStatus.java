package com.agama.itam.mongo.domain;

import java.util.Date;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

import com.agama.common.enumbean.LinkStateEnum;
import com.agama.common.enumbean.StatusEnum;

@Document
public class ThStatus {
	@Id
	private String id;
	private String name; //名称
	private String interfaceType;//接口类型
	private String modelNumber;//设备型号
	private double temperature; //温度
	private double humidity;//湿度
	private Date collectTime; //获取数据时间
	private StatusEnum status; //删除状态
	private String temperatureState; //温度状态
	private String humidityState; //湿度状态
	private String identifier; //动环设备唯一标识
	private LinkStateEnum linkState;//链接状态
	
	
	public String getName() {
		return name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public StatusEnum getStatus() {
		return status;
	}
	public void setStatus(StatusEnum status) {
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
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	public LinkStateEnum getLinkState() {
		return linkState;
	}
	public void setLinkState(LinkStateEnum linkState) {
		this.linkState = linkState;
	}
	
	
	
}
