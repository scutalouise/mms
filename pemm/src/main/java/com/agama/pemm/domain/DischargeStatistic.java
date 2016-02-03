package com.agama.pemm.domain;

import java.util.Date;

import javax.persistence.Entity;

import com.agama.authority.entity.BaseDomain;

/**
 * @Description: 放电统计实体对象
 * @Author:ranjunfeng
 * @Since :2015年12月9日 上午10:21:36
 */
@Entity
public class DischargeStatistic extends BaseDomain {

	/**
	 * long
	 */
	private static final long serialVersionUID = 1L;
	// 当前时间
	private Date collectTime;

	// 放电结束的容量
	private Double capacity;
	// 设备的负载
	private Double deviceLoad;
	//批次
	private Long batch;
	
	// 放电设备的id
	private Integer deviceId;

	

	public Date getCollectTime() {
		return collectTime;
	}

	public void setCollectTime(Date collectTime) {
		this.collectTime = collectTime;
	}

	public Double getCapacity() {
		return capacity;
	}

	public void setCapacity(Double capacity) {
		this.capacity = capacity;
	}

	public Double getDeviceLoad() {
		return deviceLoad;
	}

	public void setDeviceLoad(Double deviceLoad) {
		this.deviceLoad = deviceLoad;
	}

	public Long getBatch() {
		return batch;
	}

	public void setBatch(Long batch) {
		this.batch = batch;
	}

	public Integer getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}

	
	
}
