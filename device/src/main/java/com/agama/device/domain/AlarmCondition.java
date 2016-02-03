package com.agama.device.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.agama.authority.entity.BaseDomain;
import com.agama.common.enumbean.AlarmDeviceType;
import com.agama.common.enumbean.AlarmOptionType;
import com.agama.common.enumbean.AlarmType;
import com.agama.common.enumbean.BrandType;
import com.agama.common.enumbean.DeviceInterfaceType;
import com.agama.common.enumbean.EnabledStateEnum;
import com.agama.common.enumbean.StatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @Author:ranjunfeng
 * @Since :2015年9月21日 上午11:35:33
 * @Description:告警条件实体
 */
@Entity
public class AlarmCondition extends BaseDomain{

	/**
	 * long
	 */
	private static final long serialVersionUID = 9108222651131531502L;
	/**
	 * 报警条件名称
	 */
	private String name;  
	/**
	 * 异常持续时间
	 */
	private Integer stayTime;
	/**
	 * 重复报警次数
	 */
	private Integer repeatCount;
	/**
	 * 重复报警的间隔时间
	 */
	private Integer intervalTime;
	/**
	 * 报警消失后是否通知
	 */
	private Integer noticeAfter; 
	/**
	 * 删除状态
	 */
	private StatusEnum status; 
	/**
	 * 是否启用
	 */
	private EnabledStateEnum enabled;
	/**
	 * 告警设备
	 */
	private AlarmDeviceType alarmDeviceType;

	
	/**
	 * 告警操作类型
	 */
	@Enumerated(EnumType.STRING)
	private AlarmOptionType alarmOptionType;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "alarmTemplateId")
	private AlarmTemplate alarmTemplate;
	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "alarmLevelId")
	private AlarmLevel alarmLevel;
	
	@OneToMany(mappedBy="alarmCondition" , fetch = FetchType.LAZY)
	@JsonIgnore
	private List<AlarmRule> alarmRules;
	
	
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStayTime() {
		return stayTime;
	}

	public void setStayTime(Integer stayTime) {
		this.stayTime = stayTime;
	}

	public Integer getRepeatCount() {
		return repeatCount;
	}

	public void setRepeatCount(Integer repeatCount) {
		this.repeatCount = repeatCount;
	}

	public Integer getIntervalTime() {
		return intervalTime;
	}

	public void setIntervalTime(Integer intervalTime) {
		this.intervalTime = intervalTime;
	}

	public Integer getNoticeAfter() {
		return noticeAfter;
	}

	public void setNoticeAfter(Integer noticeAfter) {
		this.noticeAfter = noticeAfter;
	}

	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}

	

	public AlarmDeviceType getAlarmDeviceType() {
		return alarmDeviceType;
	}

	public void setAlarmDeviceType(AlarmDeviceType alarmDeviceType) {
		this.alarmDeviceType = alarmDeviceType;
	}

	
	
	

	public AlarmOptionType getAlarmOptionType() {
		return alarmOptionType;
	}

	public void setAlarmOptionType(AlarmOptionType alarmOptionType) {
		this.alarmOptionType = alarmOptionType;
	}

	public AlarmTemplate getAlarmTemplate() {
		return alarmTemplate;
	}

	public void setAlarmTemplate(AlarmTemplate alarmTemplate) {
		this.alarmTemplate = alarmTemplate;
	}

	public AlarmLevel getAlarmLevel() {
		return alarmLevel;
	}

	public void setAlarmLevel(AlarmLevel alarmLevel) {
		this.alarmLevel = alarmLevel;
	}

	public EnabledStateEnum getEnabled() {
		return enabled;
	}

	public void setEnabled(EnabledStateEnum enabled) {
		this.enabled = enabled;
	}

	

	public List<AlarmRule> getAlarmRules() {
		return alarmRules;
	}

	public void setAlarmRules(List<AlarmRule> alarmRules) {
		this.alarmRules = alarmRules;
	}

	
}
