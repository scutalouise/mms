package com.agama.itam.service;

import java.util.Map;

import com.agama.device.domain.AlarmCondition;
import com.agama.device.domain.AlarmRule;
import com.agama.device.domain.PeDevice;
import com.agama.itam.mongo.domain.UpsStatus;


public interface IUpsStatusService {
	
	public UpsStatus findNewData(Integer peDeviceId);
	
	public void collectUpsStatus(String ipAaddress, Integer index,PeDevice peDevice);
	
	public void upsAlarmCondition(UpsStatus upsStatus);
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
	/**
	 * @Description:根据采集的信息和报警规则检测电池负载
	 * @param upsStatus
	 * @param alarmRule
	 * @return
	 * @Since :2016年1月20日 下午5:00:45
	 */
	public Map<String, Object> checkLoad(UpsStatus upsStatus,
			AlarmRule alarmRule);
	
	public Map<String,Object> upsDeviceAlarm(UpsStatus upsStatus,AlarmCondition alarmCondition);

	
}
