package com.agama.device.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.agama.authority.entity.BaseDomain;
import com.agama.common.enumbean.EnabledStateEnum;
import com.agama.common.enumbean.StatusEnum;
import com.agama.common.enumbean.UsingStateEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
	
/**
 * @Author:ranjunfeng
 * @Since :2015年9月17日 下午5:16:19
 * @Description:告警等级实体类
 */
@Entity
public class AlarmLevel extends BaseDomain{
	private static final long serialVersionUID = 1L;
	/**
	 * 报警等级名称
	 */
	private String name; 
	/**
	 * 报警等级序号
	 */
	private int alarmSort;
	/**
	 * 是否邮件报警
	 */
	private UsingStateEnum isEmail;
	/**
	 * 是否短信报警
	 */
	private UsingStateEnum isSms;
	/**
	 * 是否声音报警
	 */
	private UsingStateEnum isSound;
	/**
	 * 是否启用
	 */
	private EnabledStateEnum enabled;
	/**
	 * 删除状态
	 */
	private StatusEnum status;
	/**
	 * 描述
	 */
	@Column(length=500)
	private String remark;
	
	@OneToMany(mappedBy="alarmLevel",cascade= CascadeType.MERGE , fetch = FetchType.LAZY)
	@JsonIgnore
	private List<AlarmCondition> alarmConditions;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAlarmSort() {
		return alarmSort;
	}
	public void setAlarmSort(Integer alarmSort) {
		this.alarmSort = alarmSort;
	}
	public UsingStateEnum getIsEmail() {
		return isEmail;
	}
	public void setIsEmail(UsingStateEnum isEmail) {
		this.isEmail = isEmail;
	}
	
	public UsingStateEnum getIsSms() {
		return isSms;
	}
	public void setIsSms(UsingStateEnum isSms) {
		this.isSms = isSms;
	}
	public UsingStateEnum getIsSound() {
		return isSound;
	}
	public void setIsSound(UsingStateEnum isSound) {
		this.isSound = isSound;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	public List<AlarmCondition> getAlarmConditions() {
		return alarmConditions;
	}
	public void setAlarmConditions(List<AlarmCondition> alarmConditions) {
		this.alarmConditions = alarmConditions;
	}
	
	

}
