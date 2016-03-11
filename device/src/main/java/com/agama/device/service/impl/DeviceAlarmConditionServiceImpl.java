package com.agama.device.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agama.common.dao.utils.Page;
import com.agama.common.dao.utils.PropertyFilter;
import com.agama.common.enumbean.StatusEnum;
import com.agama.common.service.impl.BaseServiceImpl;
import com.agama.device.controller.DeviceAlarmConditionController;
import com.agama.device.dao.IDeviceAlarmConditionDao;
import com.agama.device.domain.DeviceAlarmCondition;
import com.agama.device.service.IDeviceAlarmConditionService;
@Service
@Transactional
public class DeviceAlarmConditionServiceImpl extends
		BaseServiceImpl<DeviceAlarmCondition, Integer> implements
		IDeviceAlarmConditionService {
@Autowired
private IDeviceAlarmConditionDao deviceAlarmConditionDao;
	@Override
	public void delete(List<Integer> deviceAlarmConditionIdList) {
		for (Integer deviceAlarmConditionId : deviceAlarmConditionIdList) {
			deviceAlarmConditionDao.updateStatusById(deviceAlarmConditionId,StatusEnum.DELETED);
		}
		
	}

	

}
