package com.agama.device.service;

import java.util.List;

import com.agama.common.service.IBaseService;
import com.agama.device.controller.DeviceAlarmConditionController;
import com.agama.device.domain.DeviceAlarmCondition;

public interface IDeviceAlarmConditionService extends
		IBaseService<DeviceAlarmCondition, Integer> {

	public void delete(List<Integer> deviceAlarmConditionIdList);

}
