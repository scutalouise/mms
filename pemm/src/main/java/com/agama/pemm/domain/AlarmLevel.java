package com.agama.pemm.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
	
/**
 * @Author:ranjunfeng
 * @Since :2015年9月17日 下午5:16:19
 * @Description:告警等级实体类
 */
@Entity
public class AlarmLevel extends BaseDomain{
	private static final long serialVersionUID = 1L;
	
	private String name; //报警名称
	private Integer alarmSort;//告警等级序号 
	private Integer isEmail;//是否邮件报警(0:是,1:否)
	private Integer isSms;//是否短信(0:是,1:否)
	private Integer isSound;//是否声音报警(0:是,1:否)
	private String remark;
	private Integer enabled;//是否启用(0:启用,1:禁用)
	private Integer status;//删除状态(0:删除,1:正常)
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
	public Integer getIsEmail() {
		return isEmail;
	}
	public void setIsEmail(Integer isEmail) {
		this.isEmail = isEmail;
	}
	
	public Integer getIsSms() {
		return isSms;
	}
	public void setIsSms(Integer isSms) {
		this.isSms = isSms;
	}
	public Integer getIsSound() {
		return isSound;
	}
	public void setIsSound(Integer isSound) {
		this.isSound = isSound;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getEnabled() {
		return enabled;
	}
	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public List<AlarmCondition> getAlarmConditions() {
		return alarmConditions;
	}
	public void setAlarmConditions(List<AlarmCondition> alarmConditions) {
		this.alarmConditions = alarmConditions;
	}
	
	

}
