package com.agama.device.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.agama.authority.entity.BaseDomain;
import com.agama.common.domain.StateEnum;
import com.agama.common.enumbean.AlarmOptionType;
import com.agama.common.enumbean.DeviceInterfaceType;
import com.agama.common.enumbean.StatusEnum;

@Entity
public class AlarmLog extends BaseDomain{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5026894469462294487L;
	/**
	 * 获取时间
	 */
	private  Date collectTime;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 报警状态
	 */
	private StateEnum currentState;
	/**
	 * 告警的设备类型
	 */
	private AlarmOptionType alarmOptionType;
	/**
	 * 设备唯一标识
	 */
	private String identifier;
	/**
	 * 状态
	 */
	private StatusEnum status;
	public Date getCollectTime() {
		return collectTime;
	}
	public void setCollectTime(Date collectTime) {
		this.collectTime = collectTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public StateEnum getCurrentState() {
		return currentState;
	}
	public void setCurrentState(StateEnum currentState) {
		this.currentState = currentState;
	}
	public AlarmOptionType getAlarmOptionType() {
		return alarmOptionType;
	}
	public void setAlarmOptionType(AlarmOptionType alarmOptionType) {
		this.alarmOptionType = alarmOptionType;
	}
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	public StatusEnum getStatus() {
		return status;
	}
	public void setStatus(StatusEnum status) {
		this.status = status;
	}
	
	

	
	
}
