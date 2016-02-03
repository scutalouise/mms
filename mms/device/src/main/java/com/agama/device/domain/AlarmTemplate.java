package com.agama.device.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.agama.authority.entity.BaseDomain;
import com.agama.common.enumbean.EnabledStateEnum;
import com.agama.common.enumbean.StatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class AlarmTemplate extends BaseDomain {
	/**
	 * long
	 */
	private static final long serialVersionUID = -8702455687112791473L;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 是否启用
	 */
	private EnabledStateEnum enabled;
	/**
	 * 状态
	 */
	private StatusEnum status;
	/**
	 * 描述
	 */
	@Column(length=5000)
	private String remark;
	
	
	@OneToMany(mappedBy="alarmTemplate",cascade= CascadeType.MERGE , fetch = FetchType.LAZY)
	@JsonIgnore
	private List<AlarmCondition> alarmConditions;
	
	public List<AlarmCondition> getAlarmConditions() {
		return alarmConditions;
	}

	public void setAlarmConditions(List<AlarmCondition> alarmConditions) {
		this.alarmConditions = alarmConditions;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	
	public EnabledStateEnum getEnabled() {
		return enabled;
	}

	public void setEnabled(EnabledStateEnum enabled) {
		this.enabled = enabled;
	}

	
	
	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	

	
	
	
	

}
