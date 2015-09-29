package com.agama.pemm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agama.common.dao.utils.Page;
import com.agama.common.dao.utils.PropertyFilter;
import com.agama.common.service.impl.BaseServiceImpl;
import com.agama.pemm.dao.IAlarmConditionDao;
import com.agama.pemm.domain.AlarmCondition;
import com.agama.pemm.service.IAlarmConditionService;
@Service
public class AlarmConditionServiceImpl extends
		BaseServiceImpl<AlarmCondition, Integer> implements
		IAlarmConditionService {
@Autowired
private IAlarmConditionDao alarmConditionDao;
	@Override
	public void updateStatusByIds(String ids) {
		alarmConditionDao.updateStatusByIds(ids);
		
	}

	
}
