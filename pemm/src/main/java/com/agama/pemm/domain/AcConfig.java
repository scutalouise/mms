package com.agama.pemm.domain;

import java.io.Serializable;
import java.util.Arrays;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.agama.authority.entity.BaseDomain;
import com.agama.common.enumbean.EnabledStateEnum;

@Entity
public class AcConfig extends BaseDomain{
	
	
		/**
	 * long
	 */
	private static final long serialVersionUID = 7533712201938244801L;
	
	/**
	 * 设备id
	 */
	private Integer deviceId;
	/**
	 * 最低温度
	 */
	private Double minTemperature;
	/**
	 * 最高温度
	 */
	private Double maxTemperature;
	/**
	 * 最低湿度
	 */
	private Double minHumidity;
	/**
	 * 最高湿度
	 */
	private Double maxHumidity;
	/**
	 * 是否启用
	 */
	@Enumerated(EnumType.STRING)
	private EnabledStateEnum enabled;
	

	public Integer getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}
	public Double getMinTemperature() {
		return minTemperature;
	}
	public void setMinTemperature(Double minTemperature) {
		this.minTemperature = minTemperature;
	}
	public Double getMaxTemperature() {
		return maxTemperature;
	}
	public void setMaxTemperature(Double maxTemperature) {
		this.maxTemperature = maxTemperature;
	}
	public Double getMinHumidity() {
		return minHumidity;
	}
	public void setMinHumidity(Double minHumidity) {
		this.minHumidity = minHumidity;
	}
	public Double getMaxHumidity() {
		return maxHumidity;
	}
	public void setMaxHumidity(Double maxHumidity) {
		this.maxHumidity = maxHumidity;
	}
	public EnabledStateEnum getEnabled() {
		return enabled;
	}
	public void setEnabled(EnabledStateEnum enabled) {
		this.enabled = enabled;
	}

	
	
	
}
