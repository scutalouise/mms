package com.agama.device.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.agama.authority.entity.BaseDomain;
import com.agama.common.enumbean.EnabledStateEnum;
import com.agama.common.enumbean.FirstDeviceType;
import com.agama.common.enumbean.IsInitialEnum;
import com.agama.common.enumbean.SecondDeviceType;
import com.agama.common.enumbean.StatusEnum;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@DynamicInsert
@DynamicUpdate
public class Brand extends BaseDomain {

	private static final long serialVersionUID = -2251375643268067015L;
	private FirstDeviceType firstDeviceType;
	private SecondDeviceType secondDeviceType;
	private String name;
	private String otherNote;
	private Date updateTime;
	private StatusEnum status;
	private EnabledStateEnum enable;
	private IsInitialEnum isInitial;

	@Column(nullable = false)
	public FirstDeviceType getFirstDeviceType() {
		return firstDeviceType;
	}

	public void setFirstDeviceType(FirstDeviceType firstDeviceType) {
		this.firstDeviceType = firstDeviceType;
	}

	@Column(nullable = false)
	public SecondDeviceType getSecondDeviceType() {
		return secondDeviceType;
	}

	public void setSecondDeviceType(SecondDeviceType secondDeviceType) {
		this.secondDeviceType = secondDeviceType;
	}

	@Column(nullable = false, length = 100)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOtherNote() {
		return otherNote;
	}

	public void setOtherNote(String otherNote) {
		this.otherNote = otherNote;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	@Column(nullable = false)
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	@Column(nullable = false)
	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}

	@Column(nullable = false)
	public EnabledStateEnum getEnable() {
		return enable;
	}

	public void setEnable(EnabledStateEnum enable) {
		this.enable = enable;
	}

	@Column(nullable = false)
	public IsInitialEnum getIsInitial() {
		return isInitial;
	}

	public void setIsInitial(IsInitialEnum isInitial) {
		this.isInitial = isInitial;
	}
}
