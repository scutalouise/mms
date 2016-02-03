package com.agama.itam.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
@Service(value="hostCheckService")
public class HostCheckServiceImpl  implements IDeviceCheckService{
	@Autowired
	private IAlarmRuleService alarmRuleService;
	public Map<String,Object> checkDevice(DeviceStatus deviceStatus,AlarmRule alarmRule){
		Map<String, Object> hostMap = new HashMap<String, Object>();
		//运行状态
		StateEnum runState = StateEnum.good;
		StringBuffer alarmContent = new StringBuffer();
		OperationType operationType = alarmRule.getOperationType();
		Integer value = alarmRule.getValue();
		Integer minValue = alarmRule.getMinValue();
		Integer maxValue = alarmRule.getMaxValue();
		Double compareValue=null;
		if(alarmRule.getAlarmRuleType().toString().equals(AlarmRuleType.CPU.toString())){
			String cpu=deviceStatus.getDataInfo().get("cpu").toString();
			compareValue=Double.parseDouble(cpu.substring(0,cpu.length()-1));
		}else if(alarmRule.getAlarmRuleType().toString().equals(AlarmRuleType.MEMORY.toString())){
			String memory=JSONObject.parseObject(deviceStatus.getDataInfo().get("memory").toString()).getString("usageRate");
			//查看内存状态
			compareValue=Double.parseDouble(memory.substring(0,memory.length()-1));
		}else if(alarmRule.getAlarmRuleType().toString().equals(AlarmRuleType.DISK.toString())){
			//查看硬盘状态
			List<JSONObject> jsonObjects=JSONArray.parseArray(deviceStatus.getDataInfo().get("disk").toString(), JSONObject.class);
			StateEnum diskRunState=StateEnum.good;
			for (JSONObject jsonObject : jsonObjects) {
				Double usageRate=Double.parseDouble(jsonObject.getString("usageRate").split("%")[0]);
				diskRunState=alarmRuleService.ruleCompare(operationType,usageRate, value,minValue, maxValue,alarmRule.getState());
				if(diskRunState.ordinal()>runState.ordinal()){
					runState=diskRunState;
				}
			}
			
		}
		if(!alarmRule.getAlarmRuleType().toString().equals(AlarmRuleType.DISK.toString())){
			runState = alarmRuleService.ruleCompare(operationType,
				compareValue, value,
				minValue, maxValue,alarmRule.getState());
		}
		if(runState!=StateEnum.good){
			alarmContent.append("").append(alarmRule.getRemark()).append("、");
		}
		hostMap.put("runState", runState);
		hostMap.put("alarmContent", alarmContent.toString());
		return hostMap;
	}

}
