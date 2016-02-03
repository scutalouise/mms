package com.agama.pemm.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.agama.authority.entity.BaseDomain;
import com.agama.pemm.bean.DischargeType;

/**
 * @Description:放电日志实体
 * @Author:ranjunfeng
 * @Since :2015年12月2日 上午9:38:27
 */
@Entity
public class DischargeLog extends BaseDomain {

	/**
	 * long
	 */
	private static final long serialVersionUID = 1L;
	//放电类型枚举
	private DischargeType dischargeType;
	//放电时间
	private Date dischargeDate; 
	//放电人员
	private String userName;
	//状态
	private Integer status;
	//放电设备
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="deviceId")
	private Device device;
	public DischargeType getDischargeType() {
		return dischargeType;
	}
	public void setDischargeType(DischargeType dischargeType) {
		this.dischargeType = dischargeType;
	}
	public Date getDischargeDate() {
		return dischargeDate;
	}
	public void setDischargeDate(Date dischargeDate) {
		this.dischargeDate = dischargeDate;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Device getDevice() {
		return device;
	}
	public void setDevice(Device device) {
		this.device = device;
	}

	
	
}
