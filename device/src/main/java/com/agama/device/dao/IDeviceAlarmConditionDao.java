package com.agama.device.dao;

import java.util.List;

import com.agama.common.dao.IBaseDao;
import com.agama.common.enumbean.StatusEnum;
import com.agama.device.controller.DeviceAlarmConditionController;
import com.agama.device.domain.DeviceAlarmCondition;

public interface IDeviceAlarmConditionDao extends
		IBaseDao<DeviceAlarmCondition, Integer> {

	public void updateStatusById(Integer deviceAlarmConditionId, StatusEnum status);

	public List<DeviceAlarmCondition> findListByTemplateId(Integer alarmTemplateId);
}
