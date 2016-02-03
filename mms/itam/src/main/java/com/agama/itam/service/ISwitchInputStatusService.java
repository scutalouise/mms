package com.agama.itam.service;

import java.util.Map;

import com.agama.common.domain.StateEnum;
import com.agama.device.domain.AlarmRule;
import com.agama.device.domain.PeDevice;
import com.agama.itam.mongo.domain.SwitchInputStatus;

public interface ISwitchInputStatusService {

	public void collectSwitchInputStatus(String ip, Integer index, String identifier);

	public StateEnum switchInputAlarmCondition(SwitchInputStatus inputStatus);

	public Map<String, Object> checkSwitchInputState(
			SwitchInputStatus switchInputStatus, AlarmRule alarmRule);
	public Map<String, Object> switchInputDeviceAlarm(
			SwitchInputStatus switchInputStatus, Integer alarmConditionId, PeDevice peDevice);
	
}
