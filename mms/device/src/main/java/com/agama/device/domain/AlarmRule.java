package com.agama.device.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;

import com.agama.authority.entity.BaseDomain;
import com.agama.common.domain.StateEnum;
import com.agama.common.enumbean.AlarmRuleType;
import com.agama.common.enumbean.AlarmType;
import com.agama.common.enumbean.EnabledStateEnum;
import com.agama.common.enumbean.OperationType;
import com.agama.common.enumbean.StatusEnum;

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
	/**
	 * 正常值
	 */
	private Integer value;
	/**
	 * 最小值
	 */
	private Integer minValue;
	/**
	 * 最大值
	 */
	private Integer maxValue;
	@Enumerated(EnumType.STRING)
	private StateEnum state;
	
	/**
	 * 告警类型
	 */
	@Enumerated(EnumType.STRING)
	@Column(length=50)
	private AlarmRuleType alarmRuleType;
	
	
	
	/**
	 * 操作类型
	 */
	private OperationType operationType;
	/**
	 * 报警条件
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "alarmConditionId")
	private AlarmCondition alarmCondition;

	/**
	 * 设备状态
	 */
	private StatusEnum status;
	/**
	 * 是否启用
	 */
	private EnabledStateEnum enabled;
	/**
	 * 描述
	 */
	@Column(length=5000)
	private String remark;
	
	
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
	
	
	
	
	public StateEnum getState() {
		return state;
	}
	public void setState(StateEnum state) {
		this.state = state;
	}
	public AlarmRuleType getAlarmRuleType() {
		return alarmRuleType;
	}
	public void setAlarmRuleType(AlarmRuleType alarmRuleType) {
		this.alarmRuleType = alarmRuleType;
	}
	public OperationType getOperationType() {
		return operationType;
	}
	public void setOperationType(OperationType operationType) {
		this.operationType = operationType;
	}
	
	public AlarmCondition getAlarmCondition() {
		return alarmCondition;
	}
	public void setAlarmCondition(AlarmCondition alarmCondition) {
		this.alarmCondition = alarmCondition;
	}
	public StatusEnum getStatus() {
		return status;
	}
	public void setStatus(StatusEnum status) {
		this.status = status;
	}
	public EnabledStateEnum getEnabled() {
		return enabled;
	}
	
	
	
	public void setEnabled(EnabledStateEnum enabled) {
		this.enabled = enabled;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	
	
	

}
