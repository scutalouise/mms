package com.agama.pemm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agama.common.service.impl.BaseServiceImpl;
import com.agama.pemm.dao.IAlarmTemplateDao;
import com.agama.pemm.domain.AlarmTemplate;
import com.agama.pemm.service.IAlarmTemplateService;

@Service
public class AlarmTemplateServiceImpl extends
		BaseServiceImpl<AlarmTemplate, Integer> implements
		IAlarmTemplateService {
	@Autowired
	private IAlarmTemplateDao alarmTemplateDao;


	public void updateStatusByIds(String ids) {
		alarmTemplateDao.updateStatusByIds(ids);
		
	}

	
	

}
