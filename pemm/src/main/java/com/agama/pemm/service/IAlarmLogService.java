package com.agama.pemm.service;

import java.util.List;

import com.agama.common.dao.utils.Page;
import com.agama.common.service.IBaseService;
import com.agama.pemm.domain.AlarmLog;

public interface IAlarmLogService extends IBaseService<AlarmLog, Integer> {
	Object getAlarmNumAndTime(String areaInfoStr, String beginDate, String endDate);

	/**
	 * 获取年报警统计信息
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	Object getAlarmNumAndTimeForYear(String beginDate, String endDate);

	Object getAlarmNumAndTimeForMonth(String beginDate, String endDate);

	Object getAlarmNumAndTimeForDay(String beginDate, String endDate);

	List<AlarmLog> getAlarmLog();
}
