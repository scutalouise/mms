package com.agama.device.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agama.common.dao.utils.Page;
import com.agama.common.dao.utils.PropertyFilter;
import com.agama.common.domain.StateEnum;
import com.agama.common.enumbean.OperationType;
import com.agama.common.enumbean.StatusEnum;
import com.agama.common.service.impl.BaseServiceImpl;
import com.agama.device.dao.IDeviceAlarmRuleDao;
import com.agama.device.domain.DeviceAlarmRule;
import com.agama.device.service.IDeviceAlarmRuleService;

/**
 * @Description:设备告警规则业务逻辑层实现类
 * @Author:ranjunfeng
 * @Since :2016年3月3日 下午1:34:14
 */
@Service
@Transactional
public class DeviceAlarmRuleServiceImpl extends
		BaseServiceImpl<DeviceAlarmRule, Integer> implements
		IDeviceAlarmRuleService {

	@Autowired
	private IDeviceAlarmRuleDao deviceAlarmRuleDao;
	@Override
	public Page<DeviceAlarmRule> searchByAlarmTemplateId(
			Page<DeviceAlarmRule> page,
			Integer alarmTemplateId,StatusEnum status) {
		
		return deviceAlarmRuleDao.searchByAlarmTemplateIdAndStatus(page,alarmTemplateId,status);
	}
	@Override
	public void delete(List<Integer> deviceAlarmRuleIdList) {
		for (Integer deviceAlarmRuleId : deviceAlarmRuleIdList) {
			deviceAlarmRuleDao.delete(deviceAlarmRuleId);
		}
		
	}
	@Override
	public StateEnum ruleCompare(OperationType operationType,
			Double compareValue, Double value, Double minValue,
			Double maxValue, StateEnum state) {
		StateEnum runState = StateEnum.good;

		// 操作类型判断
		if (operationType == OperationType.GT) {
			if (compareValue > value) {
				
				runState = state;
			}
		} else if (operationType == OperationType.EQ) {
			if (compareValue == Double.parseDouble(value + "")) {
				runState = state;
			}
		} else if (operationType == OperationType.GE) {
			if (compareValue >= value) {
				runState = state;
			}
		} else if (operationType == OperationType.LT) {
			if (compareValue < value) {
				runState = state;
			}
		} else if (operationType == OperationType.LE) {
			if (compareValue <= value) {
				runState = state;
			}
		} else if (operationType == OperationType.BELONGTO) {
			if (compareValue >= minValue && compareValue <= maxValue) {
				runState = state;
			}
		} else if (operationType == OperationType.NOTBELONGTO) {
			if (compareValue <= minValue || compareValue >= maxValue) {
				runState = state;
			}
		}
		return runState;
	}

}
