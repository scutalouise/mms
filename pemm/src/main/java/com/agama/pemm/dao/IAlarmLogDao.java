package com.agama.pemm.dao;

import java.util.List;

import com.agama.common.dao.IBaseDao;
import com.agama.pemm.domain.AlarmLog;

public interface IAlarmLogDao extends IBaseDao<AlarmLog, Integer> {

	public Object getAlarmNumAndTime(String areaInfoStr,String beginDate,String endDate);

	public Object getAlarmNumAndTimeForYear(String beginDate, String endDate);

	public Object getAlarmNumAndTimeForMonth(String beginDate, String endDate);
	public Object getAlarmNumAndTimeForDay(String beginDate, String endDate);

	public List<AlarmLog> getAlarmLog();

}
