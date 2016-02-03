package com.agama.pemm.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.agama.common.dao.utils.Page;
import com.agama.common.enumbean.DeviceType;
import com.agama.common.service.IBaseService;
import com.agama.pemm.bean.UpsChartBean;
import com.agama.pemm.domain.AlarmRule;
import com.agama.pemm.domain.UpsStatus;

public interface IUpsStatusService extends IBaseService<UpsStatus, Integer> {
	/**
	 * @Description:检测告警状态
	 * @param upsStatus
	 * @return
	 * @Since :2015年9月30日 上午10:23:03
	 */
	public Map<String, Object> checkAlarm(UpsStatus upsStatus,
			Integer alarmConditionId);
	/**
	 * 根据网关ID查询最新的UPS状态信息
	 * 
	 * @param gitInfoId
	 * @return
	 */

	public List<UpsStatus> findLatestDataByGitInfoId(Integer gitInfoId);

	

	public Page<UpsStatus> searchByGitInfoIdAndDeviceTypeAndIndex(
			Page<UpsStatus> page, Integer gitInfoId, DeviceType ups,
			Integer index,String startDate,String endDate);

	/**
	 * 根据ID批量更改状态/逻辑删除
	 * 
	 * @param ids
	 */
	public void updateStatusByIds(String ids);

	/**
	 * 根据设备ID查询UPS状态信息集合
	 * 
	 * @param deviceId
	 * @return
	 */
	public List<UpsChartBean> getListByDeviceId(Integer deviceId,
			Date beginDate, Date endDate);

	/**
	 * @Description:根据采集的信息和报警规则检测UPS通讯状态的运行状态
	 * @param upsStatus UPS状态信息
	 * @param alarmRule 告警规则
	 * @return
	 * @Since :2015年9月30日 上午11:09:07
	 */
	public Map<String, Object> checkCommunicationStatus(UpsStatus upsStatus,
			AlarmRule alarmRule);
	/**
	 * @Description:根据采集的信息和报警规则检测UPS旁路电压运行状态
	 * @param upsStatus UPS状态信息
	 * @param alarmRule 告警规则
	 * @return
	 * @Since :2015年9月30日 上午11:11:11
	 */
	public Map<String, Object> checkByPassVoltage(UpsStatus upsStatus,
			AlarmRule alarmRule);
	/**
	 * @Description:根据采集的信息和报警规则检测输入电压的运行状态
	 * @param upsStatus UPS状态信息
	 * @param alarmRule 报警规则
	 * @return
	 * @Since :2015年10月8日 上午9:34:30
	 */
	public Map<String,Object> checkInputVoltage(UpsStatus upsStatus,AlarmRule alarmRule);
	/**
	 * @Description:根据采集的信息和报警规则检测输出电压的运行状态
	 * @param upsStatus UPS状态信息
	 * @param alarmRule 报警规则
	 * @return
	 * @Since :2015年10月9日 下午4:12:45
	 */
	public Map<String,Object> checkOutputVoltage(UpsStatus upsStatus,AlarmRule alarmRule);
	/**
	 * @Description:根据采集的信息和报警规则检测机内温度的运行状态
	 * @param upsStatus UPS状态信息
	 * @param alarmRule 报警规则
	 * @return
	 * @Since :2015年10月9日 下午4:29:34
	 */
	public Map<String,Object> checkInternalTemperature(UpsStatus upsStatus,AlarmRule alarmRule);
	/**
	 * @Description:根据采集的信息和报警规则检测电池电压的运行状态
	 * @param upsStatus
	 * @param alarmRule
	 * @return
	 * @Since :2015年10月9日 下午5:40:31
	 */
	public Map<String,Object> checkBatteryVoltage(UpsStatus upsStatus,AlarmRule alarmRule);
	/**
	 * @Description:根据采集的信息和报警规则检测电池电量的运行状态
	 * @param upsStatus
	 * @param alarmRule
	 * @return
	 * @Since :2015年11月13日 下午4:02:22
	 */
	public Map<String, Object> checkElectricQuantity(UpsStatus upsStatus,
			AlarmRule alarmRule);
	public List<UpsChartBean> getUpsChartBeanList(Integer deviceId,
			Date beginDate, Date endDate);
	
	public List<UpsStatus> getListByGitInfoIdAndDeviceTypeAndIndex(
			 Integer gitInfoId, DeviceType ups,
			Integer index,String startDate,String endDate);
	
}
