package com.agama.pemm.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Transient;

import com.agama.authority.entity.BaseDomain;
import com.agama.common.domain.StateEnum;
import com.agama.common.enumbean.AlarmType;
import com.agama.common.enumbean.OperationType;

/**
 * @Author:ranjunfeng
 * @Since :2015年9月28日 下午2:50:47
 * @Description:报警规则
 */
@Entity
public class AlarmRule extends BaseDomain {
	/**
	 * long
	 */
	private static final long serialVersionUID = 8107582007557294219L;
	private Integer value;// 正常值
	private Integer minValue;// 最小值
	private Integer maxValue;// 最大值
	@Enumerated(EnumType.ORDINAL)
	private AlarmType alarmType;// 告警类型
	private OperationType operationType; // 操作类型
	private Integer alarmConditionId;// 报警条件ID
	@Transient
	private String alarmConditionName;// 报警条件名称
	
	private String deviceInterfaceType;
	
	private Integer status;
	private Integer enabled;
	@Column(length=5000)
	private String remark;
	
	@Enumerated(EnumType.STRING)
	private StateEnum ruleAlarmType;
	public Integer getValue() {
		return value;
	}
	public void setValue(Integer value) {
		this.value = value;
	}
	public Integer getMinValue() {
		return minValue;
	}
	public void setMinValue(Integer minValue) {
		this.minValue = minValue;
	}
	public Integer getMaxValue() {
		return maxValue;
	}
	public void setMaxValue(Integer maxValue) {
		this.maxValue = maxValue;
	}
	public AlarmType getAlarmType() {
		return alarmType;
	}
	public void setAlarmType(AlarmType alarmType) {
		this.alarmType = alarmType;
	}
	public OperationType getOperationType() {
		return operationType;
	}
	public void setOperationType(OperationType operationType) {
		this.operationType = operationType;
	}
	public Integer getAlarmConditionId() {
		return alarmConditionId;
	}
	public void setAlarmConditionId(Integer alarmConditionId) {
		this.alarmConditionId = alarmConditionId;
	}
	public String getAlarmConditionName() {
		return alarmConditionName;
	}
	public void setAlarmConditionName(String alarmConditionName) {
		this.alarmConditionName = alarmConditionName;
	}
	
	
	
	public String getDeviceInterfaceType() {
		return deviceInterfaceType;
	}
	public void setDeviceInterfaceType(String deviceInterfaceType) {
		this.deviceInterfaceType = deviceInterfaceType;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getEnabled() {
		return enabled;
	}
	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public StateEnum getRuleAlarmType() {
		return ruleAlarmType;
	}
	public void setRuleAlarmType(StateEnum ruleAlarmType) {
		this.ruleAlarmType = ruleAlarmType;
	}
	
	

}
