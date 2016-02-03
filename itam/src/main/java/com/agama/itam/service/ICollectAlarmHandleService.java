package com.agama.itam.service;

import java.util.Map;

import com.agama.device.domain.AlarmCondition;
import com.agama.itam.mongo.domain.DeviceStatus;

/**
 * @Description:采集告警处理业务接口
 * @Author:ranjunfeng
 * @Since :2016年1月6日 下午2:14:12
 */
public interface ICollectAlarmHandleService {
	/**
	 * @Description:根据采集器推送过来的数据进行告警处理
	 * @Since :2016年1月6日 下午2:16:36
	 */
	public void alarmConditionHandle(String jsonStr);
	
	public Map<String, Object> deviceAlarm(DeviceStatus deviceStatus, AlarmCondition alarmCondition);

	

}
