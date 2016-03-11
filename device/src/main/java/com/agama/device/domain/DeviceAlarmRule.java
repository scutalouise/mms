package com.agama.device.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;



import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.agama.authority.entity.BaseDomain;
import com.agama.common.domain.StateEnum;
import com.agama.common.enumbean.AlarmOptionUnitEnum;
import com.agama.common.enumbean.CountUnitEnum;
import com.agama.common.enumbean.DeviceAlarmOptionEnum;
import com.agama.common.enumbean.EnabledStateEnum;
import com.agama.common.enumbean.FirstDeviceType;
import com.agama.common.enumbean.OperationType;
import com.agama.common.enumbean.StatusEnum;
import com.agama.common.enumbean.TimeUnitEnum;
import com.agama.common.enumbean.SecondDeviceType;
@Entity
public class DeviceAlarmRule extends BaseDomain{

	/**
	 * long
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 告警的配置信息
	 */
	@Enumerated(EnumType.STRING)
	private DeviceAlarmOptionEnum deviceAlarmOption;
	/**
	 * 告警规则值
	 */
	private Double value;
	/**
	 * 最小值
	 */
	private Double minValue;
	/**
	 * 最大值
	 */
	private Double maxValue;
	
	@Enumerated(EnumType.STRING)
	private AlarmOptionUnitEnum alarmOptionUnit;
	
	/**
	 * 操作类型
	 */
	private OperationType operationType;
	
	/**
	 * 告警状态
	 */
	@Enumerated(EnumType.STRING)
	private StateEnum state;
	/**
	 * 删除状态
	 */
	@Enumerated(EnumType.STRING)
	private StatusEnum status;
	/**
	 * 是否启用
	 */
	@Enumerated(EnumType.STRING)
	private EnabledStateEnum enabled;
	/**
	 * 描述
	 */
	@Column(length=500)
	private String remark;
	
	/**
	 * 设备告警条件
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "deviceAlarmConditionId")
	private DeviceAlarmCondition deviceAlarmCondition;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DeviceAlarmOptionEnum getDeviceAlarmOption() {
		return deviceAlarmOption;
	}

	public void setDeviceAlarmOption(DeviceAlarmOptionEnum deviceAlarmOption) {
		this.deviceAlarmOption = deviceAlarmOption;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public Double getMinValue() {
		return minValue;
	}

	public void setMinValue(Double minValue) {
		this.minValue = minValue;
	}

	public Double getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(Double maxValue) {
		this.maxValue = maxValue;
	}

	public OperationType getOperationType() {
		return operationType;
	}

	public void setOperationType(OperationType operationType) {
		this.operationType = operationType;
	}

	public StateEnum getState() {
		return state;
	}

	public void setState(StateEnum state) {
		this.state = state;
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

	public DeviceAlarmCondition getDeviceAlarmCondition() {
		return deviceAlarmCondition;
	}

	public void setDeviceAlarmCondition(DeviceAlarmCondition deviceAlarmCondition) {
		this.deviceAlarmCondition = deviceAlarmCondition;
	}

	public AlarmOptionUnitEnum getAlarmOptionUnit() {
		return alarmOptionUnit;
	}

	public void setAlarmOptionUnit(AlarmOptionUnitEnum alarmOptionUnit) {
		this.alarmOptionUnit = alarmOptionUnit;
	}


	
	
}
