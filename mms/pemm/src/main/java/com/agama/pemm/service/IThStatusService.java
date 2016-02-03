package com.agama.pemm.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.agama.common.dao.utils.Page;
import com.agama.common.enumbean.DeviceType;
import com.agama.common.service.IBaseService;
import com.agama.pemm.bean.ThChartBean;
import com.agama.pemm.domain.AlarmRule;
import com.agama.pemm.domain.ThStatus;

public interface IThStatusService extends IBaseService<ThStatus, Integer> {
	/**
	 * 根据网关ID查询最新的ThStatus状态信息
	 * 
	 * @param gitInfoId
	 * @return
	 */
	public List<ThStatus> findLatestDataByGitInfoId(Integer gitInfoId);

	/**
	 * @Description:检测温湿度报警状态
	 * @param thStatus
	 * @param id
	 * @return
	 * @Since :2015年10月13日 下午3:41:14
	 */
	public Map<String, Object> checkAlarm(ThStatus thStatus, Integer id);

	/**
	 * @Description:根据采集的信息和报警规则检测温度运行状态
	 * @param thStatus
	 * @param alarmRule
	 * @return
	 * @Since :2015年10月13日 下午4:24:37
	 */
	public Map<String, Object> checkTemperature(ThStatus thStatus,
			AlarmRule alarmRule);

	/**
	 * @Description:根据采集的信息和报警规则检测湿度运行状态
	 * @param thStatus
	 * @param alarmRule
	 * @return
	 * @Since :2015年10月13日 下午4:38:36
	 */
	public Map<String, Object> checkHumidity(ThStatus thStatus,
			AlarmRule alarmRule);

	/**
	 * @Description:根据设备ID和时间段查询温湿度状态信息集合
	 * @param deviceId
	 * @param beginDate
	 *            开始时间
	 * @param endDate
	 *            结束时间
	 * @return
	 * @Since :2015年10月14日 下午6:05:37
	 */
	public List<ThChartBean> getListByDeviceId(Integer deviceId,
			Date beginDate, Date endDate);

	public Page<ThStatus> searchByGitInfoIdAndDeviceTypeAndIndex(
			Page<ThStatus> page, Integer gitInfoId, DeviceType th,
			Integer index, String startDate, String endDate);

	public List<ThStatus> getListByGitInfoIdAndDeviceTypeAndIndex(
			Integer gitInfoId, DeviceType deviceType, Integer index, String startDate,
			String endDate);
}
