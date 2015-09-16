package com.agama.pemm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agama.common.service.impl.BaseServiceImpl;
import com.agama.pemm.dao.IAlarmLogDao;
import com.agama.pemm.domain.AlarmLog;
import com.agama.pemm.service.IAlarmLogService;
@Service
@Transactional
public class AlarmLogServiceImpl extends BaseServiceImpl<AlarmLog, Integer>
		implements IAlarmLogService {
	@Autowired
	private IAlarmLogDao alarmLogDao;

	@Override
	public Object getAlarmNumAndTime(String areaInfoStr,String beginDate,String endDate) {

		return alarmLogDao.getAlarmNumAndTime(areaInfoStr,beginDate,endDate);
	}

}
