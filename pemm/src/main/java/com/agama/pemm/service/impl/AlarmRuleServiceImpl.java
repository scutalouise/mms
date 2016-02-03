package com.agama.pemm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agama.common.domain.StateEnum;
import com.agama.common.enumbean.OperationType;
import com.agama.common.service.impl.BaseServiceImpl;
import com.agama.pemm.dao.IAlarmRuleDao;
import com.agama.pemm.domain.AlarmRule;
import com.agama.pemm.service.IAlarmRuleService;

@Service
public class AlarmRuleServiceImpl extends BaseServiceImpl<AlarmRule, Integer>
		implements IAlarmRuleService {
	
	@Autowired
	private IAlarmRuleDao alarmRuleDao;
	
	@Override
	public void updateStatusByIds(String ids) {
		alarmRuleDao.updateStatusByIds(ids);
		
	}
	
	@Override
	public StateEnum ruleCompare(OperationType operationType,
			Double compareValue, Integer value, Integer minValue,
			Integer maxValue,StateEnum state) {
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
