package com.agama.device.dao;

import java.util.List;

import com.agama.common.dao.IBaseDao;
import com.agama.common.enumbean.DeviceInterfaceType;
import com.agama.device.domain.AlarmLog;

public interface IAlarmLogDao extends IBaseDao<AlarmLog, Integer> {

	public Object getAlarmNumAndTime(String organizationIdIdStr,DeviceInterfaceType deviceInterfaceType,String beginDate,String endDate);

	public Object getAlarmNumAndTimeForYear(String beginDate, String endDate);

	public Object getAlarmNumAndTimeForMonth(String beginDate, String endDate);
	public Object getAlarmNumAndTimeForDay(String beginDate, String endDate);

	public List<AlarmLog> getAlarmLog();
	/**
	 * @Description:获取组织机构(网点)报警次数topN
	 * @return
	 * @Since :2015年10月23日 下午5:39:57
	 */
	public Object getAlarmNum(String organizationIdIdStr,Integer top,String beginDate,String endDate);
	/**
	 * @Description:根据git的id字符串修改报警日志状态信息
	 * @param gitInfoIds git的主键id字符串
	 * @param status 修改的状态
	 * @Since :2015年11月24日 下午5:47:38
	 */
	public void updateStatusByGitInfoIds(String gitInfoIds, int status);
	/**
	 * @Description:根据设备id字符串修改报警日志状态信息
	 * @param deviceIds 设备id字符串
	 * @param status 修改的状态
	 * @Since :2015年11月24日 下午5:55:31
	 */
	public void updateStatusDeviceIds(String deviceIds, int status);
	/**
	 * @Description:查询最新的告警日志信息
	 * @param top
	 * @return
	 * @Since :2015年12月18日 下午2:16:44
	 */
	public List<AlarmLog> getAlarmLogforTop(Integer top);

}
