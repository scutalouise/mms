package com.agama.itam.service;

import java.util.Map;

import com.agama.device.domain.AlarmRule;
import com.agama.itam.mongo.domain.DeviceStatus;

public interface IDeviceCheckService {
	public Map<String,Object> checkDevice(DeviceStatus deviceStatus,AlarmRule alarmRule);
}
