package com.agama.itam.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agama.aws.dao.MongoDao;
import com.agama.common.domain.StateEnum;
import com.agama.common.enumbean.AlarmDeviceType;
import com.agama.common.enumbean.AlarmOptionType;
import com.agama.common.enumbean.OperationType;
import com.agama.common.enumbean.StatusEnum;
import com.agama.device.dao.IAlarmConditionDao;
import com.agama.device.dao.IAlarmRuleDao;
import com.agama.device.dao.ICollectionDeviceDao;
import com.agama.device.dao.IPeDeviceDao;
import com.agama.device.domain.AlarmCondition;
import com.agama.device.domain.AlarmRule;
import com.agama.device.domain.PeDevice;
import com.agama.device.service.IAlarmConditionService;
import com.agama.device.service.IAlarmRuleService;
import com.agama.device.service.impl.AlarmConditionServiceImpl;
import com.agama.device.utils.SNMPUtil;
import com.agama.itam.mongo.domain.SwitchInputStatus;
import com.agama.itam.protocol.snmp.SwitchInputOidInfo;

import com.agama.itam.service.ISwitchInputStatusService;
import com.agama.tool.utils.ConvertUtils;
@Service
@Transactional
public class SwitchInputStatusServiceImpl implements ISwitchInputStatusService {
	@Autowired
	private IAlarmConditionDao alarmConditionDao;
	@Autowired
	private MongoDao mongoDao;
	
	
	@Autowired
	private IPeDeviceDao peDeviceDao;
	@Autowired
	private IAlarmRuleService alarmRuleService;
	@Autowired
	private IAlarmConditionService alarmConditionService;
	@Autowired
	private ICollectionDeviceDao collectionDeviceDao;
	@Autowired
	private IAlarmRuleDao alarmRuleDao;
	@Override
	public void collectSwitchInputStatus(String ip, Integer index,
			String identifier) {
		SwitchInputStatus inputStatus = new SwitchInputStatus();

		try {
			SNMPUtil snmp = new SNMPUtil();
			Map<String, String> resultMap = snmp.walkByGetNext(ip,
					"1.3.6.1.4.1.34651.2.3.1");
			if (resultMap.size() > 0) {
				
						inputStatus.setName(ConvertUtils
								.change2Chinese(resultMap
										.get(SwitchInputOidInfo.NAME.getOid()
												+ "." + index)));

						inputStatus.setInputSignal(Integer.parseInt(resultMap
								.get(SwitchInputOidInfo.SIGNAL.getOid() + "."
										+ index).trim()));
						inputStatus.setCollectTime(new Date());
						inputStatus.setStatus(StatusEnum.NORMAL);
						inputStatus.setIdentifier(identifier);
						StateEnum stateEnum =switchInputAlarmCondition(inputStatus);
						inputStatus.setCurrentState(stateEnum);
						mongoDao.save(inputStatus);
					
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public StateEnum switchInputAlarmCondition(SwitchInputStatus switchInputStatus) {
		PeDevice peDevice = peDeviceDao.findByIdentifier(switchInputStatus.getIdentifier());
		// 报警条件集合
		StateEnum currentState = StateEnum.good;
		StateEnum runState = StateEnum.good;
		List<AlarmCondition> alarmConditions = alarmConditionDao
				.getListByTemplateId(peDevice.getAlarmTemplateId(),
						AlarmDeviceType.PEDEVICE,AlarmOptionType.valueOf(peDevice.getDhDeviceInterfaceType().toString()));
		for (AlarmCondition alarmCondition : alarmConditions) {
			Map<String, Object> alarmMap = switchInputDeviceAlarm(
					switchInputStatus, alarmCondition.getId(), peDevice);
			currentState = alarmConditionService.alarmConditionHandle(peDevice.getManagerId(),peDevice.getIdentifier(), currentState,
					alarmCondition, alarmMap);
			runState = StateEnum.valueOf(alarmMap.get("runState").toString());
		}
		if (AlarmConditionServiceImpl.alarmConditionMap.get("currentState_" + peDevice.getIdentifier()) != null
				&& peDevice.getCurrentState() != AlarmConditionServiceImpl.alarmConditionMap
						.get("currentState_" + peDevice.getIdentifier())) {
			peDeviceDao.updateCurrentStateByIdentifier(
					peDevice.getIdentifier(),
					StateEnum.valueOf(AlarmConditionServiceImpl.alarmConditionMap.get(
							"currentState_" + peDevice.getIdentifier()).toString()));
		} else {
			if (peDevice.getCurrentState() != currentState) {
				peDeviceDao.updateCurrentStateByIdentifier(peDevice.getIdentifier(), currentState);
			}
		}
		StateEnum state=peDeviceDao.findSeverityStateByCollectId(peDevice.getCollectDeviceId());
		collectionDeviceDao.updateCurrentStateById(peDevice.getCollectDeviceId(),state);
		return runState;
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
				minValue, maxValue,alarmRule.getState());
		if (runState != StateEnum.good) {
			alarmContent.append("").append(alarmRule.getRemark()).append("、");
		}
		waterImmersionStateMap.put("runState", runState);
		waterImmersionStateMap.put("alarmContent", alarmContent.toString());
		return waterImmersionStateMap;
	}

	@Override
	public Map<String, Object> switchInputDeviceAlarm(
			SwitchInputStatus switchInputStatus, Integer alarmConditionId, PeDevice peDevice) {
		Map<String, Object> alarmResultMap = new HashMap<String, Object>();
		StateEnum runState = StateEnum.good;
		List<AlarmRule> alarmRules = alarmRuleDao
				.getListByConditionIdAndAlarmOptionType(alarmConditionId,
						AlarmOptionType.valueOf(peDevice.getDhDeviceInterfaceType().toString()));
		// 报警内容
		StringBuffer alarmContent = new StringBuffer();
		StringBuffer alarmRuleType=new StringBuffer();
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
			alarmRuleType.append(waterImmersionStateMap.get("alarmRuleType"));

		}
		if(alarmContent.indexOf("、")>0){
			alarmContent=new StringBuffer(alarmContent.substring(0,alarmContent.length()-1));
			alarmRuleType=new StringBuffer(alarmRuleType.substring(0,alarmRuleType.length()-1));
		}		
		alarmResultMap.put("runState", runState);
		alarmResultMap.put("alarmContent", alarmContent.toString());
		alarmResultMap.put("alarmRuleType", alarmRuleType.toString());
		return alarmResultMap;
	}


}
