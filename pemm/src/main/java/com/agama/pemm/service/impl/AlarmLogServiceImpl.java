package com.agama.pemm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agama.common.dao.utils.Page;
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

	@Override
	public Object getAlarmNumAndTimeForYear(String beginDate, String endDate) {
		return alarmLogDao.getAlarmNumAndTimeForYear(beginDate,endDate);
		
	}

	@Override
	public Object getAlarmNumAndTimeForMonth(String beginDate, String endDate) {
		return alarmLogDao.getAlarmNumAndTimeForMonth(beginDate,endDate);

	}

	@Override
	public Object getAlarmNumAndTimeForDay(String beginDate, String endDate) {
		return alarmLogDao.getAlarmNumAndTimeForDay(beginDate,endDate);

	}

	@Override
	public List<AlarmLog> getAlarmLog() {
		
		
		return alarmLogDao.getAlarmLog();
	}

}
