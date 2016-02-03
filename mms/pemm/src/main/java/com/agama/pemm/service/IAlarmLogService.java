package com.agama.pemm.service;

import java.util.List;

import com.agama.common.enumbean.DeviceInterfaceType;
import com.agama.common.service.IBaseService;
import com.agama.pemm.domain.AlarmLog;

public interface IAlarmLogService extends IBaseService<AlarmLog, Integer> {
	Object getAlarmNumAndTime(String organizationIdIdStr,DeviceInterfaceType deviceInterfaceType, String beginDate, String endDate);

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
	/**
	 * @Description:获取组织机构(网点)报警次数topN
	 * @return
	 * @Since :2015年10月23日 下午5:39:57
	 */
	public Object getAlarmNum(String organizationIdIdStr,Integer top,String beginDate,String endDate);
	/**
	 * @Description:查询最新的告警信息
	 * @param top
	 * @return
	 * @Since :2015年12月18日 下午2:15:01
	 */
	public List<AlarmLog> getAlarmLogforTop(Integer top);
}
