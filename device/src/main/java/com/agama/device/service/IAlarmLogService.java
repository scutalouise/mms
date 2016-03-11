package com.agama.device.service;

import java.util.List;
import java.util.Map;

import com.agama.common.service.IBaseService;
import com.agama.device.domain.AlarmCondition;
import com.agama.device.domain.AlarmLog;

public interface IAlarmLogService extends IBaseService<AlarmLog, Integer> {
	
	public void alarmMessage(Integer managerId,String identifier, String alarmContent,
			AlarmCondition alarmCondition);
	public void saveAlarmLog(String identifier,Map<String,Object> alarmMap);
	
	public Object getAlarmNumAndTimeForYear(String beginDate, String endDate);
	public Object getAlarmNumAndTimeForMonth(String beginDate, String endDate);
	public Object getAlarmNumAndTimeForDay(String beginDate, String endDate);
	public List<AlarmLog> getAlarmLog();
}
