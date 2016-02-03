package com.agama.device.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agama.common.service.impl.BaseServiceImpl;
import com.agama.device.dao.IAlarmTemplateDao;
import com.agama.device.domain.AlarmTemplate;
import com.agama.device.service.IAlarmTemplateService;

@Service
public class AlarmTemplateServiceImpl extends
		BaseServiceImpl<AlarmTemplate, Integer> implements
		IAlarmTemplateService {
	@Autowired
	private IAlarmTemplateDao alarmTemplateDao;
	@Override
	public void updateStatusByIds(String ids) {
		alarmTemplateDao.updateStatusByIds(ids);
		
	}
	

	
	

}
