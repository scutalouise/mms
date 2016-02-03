package com.agama.pemm.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agama.common.domain.StateEnum;
import com.agama.common.enumbean.DeviceInterfaceType;
import com.agama.common.enumbean.OperationType;
import com.agama.common.service.impl.BaseServiceImpl;
import com.agama.pemm.dao.IAlarmRuleDao;
import com.agama.pemm.dao.ISwitchInputStatusDao;
import com.agama.pemm.domain.AlarmRule;
import com.agama.pemm.domain.Device;
import com.agama.pemm.domain.SwitchInputStatus;
import com.agama.pemm.service.IAlarmRuleService;
import com.agama.pemm.service.ISwitchInputStatusService;

/**
 * @Description:开关量输入信息状态业务处理类
 * @Author:ranjunfeng
 * @Since :2015年10月15日 下午3:51:17
 */
@Service
public class SwitchInputStatusServiceImpl extends
		BaseServiceImpl<SwitchInputStatus, Integer> implements
		ISwitchInputStatusService {
	@Autowired
	private ISwitchInputStatusDao switchInputStatusDao;
	@Autowired
	private IAlarmRuleDao alarmRuleDao;
	@Autowired
	private IAlarmRuleService alarmRuleService;

	@Override
	public Map<String, Object> checkAlarm(SwitchInputStatus switchInputStatus,
			Integer alarmConditionId, Device device) {
		Map<String, Object> alarmResultMap = new HashMap<String, Object>();
		StateEnum runState = StateEnum.good;
		List<AlarmRule> alarmRules = alarmRuleDao
				.getListByConditionIdAndDeviceInterfaceType(alarmConditionId,
						device.getDeviceInterfaceType());
		List<Map<String,Object>> alarmMapList=new ArrayList<Map<String,Object>>();

		// 报警内容
		StringBuffer alarmContent = new StringBuffer();
		for (AlarmRule alarmRule : alarmRules) {
			// 状态
			Map<String, Object> waterImmersionStateMap = checkSwitchInputState(
					switchInputStatus, alarmRule);
			if (runState.ordinal() < StateEnum.valueOf(
					waterImmersionStateMap.get("runState").toString())
					.ordinal()) {
				runState = StateEnum.valueOf(waterImmersionStateMap.get(
						"runState").toString());
			}
			alarmContent.append(waterImmersionStateMap.get("alarmContent")
					.toString());
			if(StateEnum.valueOf(waterImmersionStateMap.get("runState").toString())!=StateEnum.good){
				alarmMapList.add(waterImmersionStateMap);
			}

		}
		if(alarmContent.indexOf("、")>0){
			alarmContent=new StringBuffer(alarmContent.substring(0,alarmContent.length()-1));
		}
		alarmResultMap.put("runState", runState);
		alarmResultMap.put("alarmContent", alarmContent.toString());
		alarmResultMap.put("alarmMapList", alarmMapList);

		return alarmResultMap;
	}

	@Override
	public Map<String, Object> checkSwitchInputState(
			SwitchInputStatus switchInputStatus, AlarmRule alarmRule) {
		Map<String, Object> waterImmersionStateMap = new HashMap<String, Object>();
		StateEnum runState = StateEnum.good;
		StringBuffer alarmContent = new StringBuffer();
		OperationType operationType = alarmRule.getOperationType();
		Integer value = alarmRule.getValue();
		Integer minValue = alarmRule.getMinValue();
		Integer maxValue = alarmRule.getMaxValue();
		runState = alarmRuleService.ruleCompare(operationType,
				Double.parseDouble(switchInputStatus.getInputSignal() + ""), value,
				minValue, maxValue,alarmRule.getRuleAlarmType());
		if (runState == StateEnum.error) {
			alarmContent.append("").append(alarmRule.getRemark()).append("、");
		}
		waterImmersionStateMap.put("runState", runState);
		waterImmersionStateMap.put("alarmContent", alarmContent.toString());
		return waterImmersionStateMap;
	}

	@Override
	public List<SwitchInputStatus> findLatestData(Integer gitInfoId,DeviceInterfaceType deviceInterfaceType) {
		return switchInputStatusDao.findLatestData(gitInfoId,deviceInterfaceType);
	}

}
