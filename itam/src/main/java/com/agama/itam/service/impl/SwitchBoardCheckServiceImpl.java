package com.agama.itam.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dozer.inject.Inject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agama.common.domain.StateEnum;
import com.agama.common.enumbean.AlarmRuleType;
import com.agama.common.enumbean.OperationType;
import com.agama.device.domain.AlarmRule;
import com.agama.device.service.IAlarmRuleService;
import com.agama.itam.mongo.domain.DeviceStatus;
import com.agama.itam.service.IDeviceCheckService;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
@Service(value="switchBoardCheckService")
public class SwitchBoardCheckServiceImpl  implements IDeviceCheckService{
	@Autowired
	private IAlarmRuleService alarmRuleService;
	
	public Map<String, Object> checkDevice(DeviceStatus deviceStatus,
			AlarmRule alarmRule) {
		Map<String, Object> switchBoardMap = new HashMap<String, Object>();
		//运行状态
		StateEnum runState = StateEnum.good;
		StringBuffer alarmContent = new StringBuffer();
		OperationType operationType = alarmRule.getOperationType();
		Integer value = alarmRule.getValue();
		Integer minValue = alarmRule.getMinValue();
		Integer maxValue = alarmRule.getMaxValue();
		// 华为交换机CUP检测
		 
		if(alarmRule.getAlarmRuleType().toString().equals(AlarmRuleType.SWITCHBOARD_HW_CPU.toString())){
			
			List<JSONObject> jsonObjects=JSONArray.parseArray(deviceStatus.getDataInfo().get("entityInfo").toString(), JSONObject.class);
			StateEnum cpuRunState=StateEnum.good;
			for (JSONObject jsonObject : jsonObjects) {
				
				Double cpuUsage=Double.parseDouble(jsonObject.getString("cpuUsage").split("%")[0]);
				cpuRunState=alarmRuleService.ruleCompare(operationType,cpuUsage, value,minValue, maxValue,alarmRule.getState());
				if(cpuRunState.ordinal()>runState.ordinal()){
					runState=cpuRunState;
				}
			}
		}else if((alarmRule.getAlarmRuleType().toString()).equals(AlarmRuleType.SWITCHBOARD_HW_TEMPERATURE.toString())){
			//华为交换机温湿度检测
			
			List<JSONObject> jsonObjects=JSONArray.parseArray(deviceStatus.getDataInfo().get("entityInfo").toString(), JSONObject.class);
			StateEnum temperatureRunState=StateEnum.good;
			for (JSONObject jsonObject : jsonObjects) {
				
				Double temperature=Double.parseDouble(jsonObject.getString("temperature").split("°C")[0].trim());
				temperatureRunState=alarmRuleService.ruleCompare(operationType,temperature, value,minValue, maxValue,alarmRule.getState());
				if(temperatureRunState.ordinal()>runState.ordinal()){
					runState=temperatureRunState;
				}
			}
		}else if(alarmRule.getAlarmRuleType()==AlarmRuleType.SWITCHBOARD_HW_MEMORY){
			//华为交换机内存检测
			List<JSONObject> jsonObjects=JSONArray.parseArray(deviceStatus.getDataInfo().get("entityInfo").toString(), JSONObject.class);
			StateEnum memoryRunState=StateEnum.good;
			for (JSONObject jsonObject : jsonObjects) {
				Double memoryUsage=Double.parseDouble(jsonObject.getString("memoryUsage").split("%")[0].trim());
				memoryRunState=alarmRuleService.ruleCompare(operationType,memoryUsage, value,minValue, maxValue,alarmRule.getState());
				if(memoryRunState.ordinal()>runState.ordinal()){
					runState=memoryRunState;
				}
			}
		}
		
		
		if(runState!=StateEnum.good){
			alarmContent.append("").append(alarmRule.getRemark()).append("、");
		}
		switchBoardMap.put("runState", runState);
		switchBoardMap.put("alarmContent", alarmContent.toString());
		return switchBoardMap;
	}

	
}
