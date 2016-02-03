package com.agama.itam.service;

import java.util.Map;

import com.agama.device.domain.AlarmCondition;
import com.agama.device.domain.AlarmRule;
import com.agama.device.domain.PeDevice;
import com.agama.itam.mongo.domain.ThStatus;

/**
 * @Description:温湿度状态业务逻辑实体
 * @Author:ranjunfeng
 * @Since :2016年1月22日 下午4:18:09
 */
public interface IThStatusService {
	/**
	 * @Description:采集温湿度状态
	 * @param ip
	 * @param dhDeviceIndex
	 * @param peDevice
	 * @Since :2016年1月22日 下午4:17:58
	 */
	public void collectThStatus(String ip, Integer dhDeviceIndex, PeDevice peDevice);
	public void thAlarmCondition(ThStatus thStatus);
	/**
	 * @Description:根据采集的信息和报警规则检测检测温湿度状态
	 * @param thStatus
	 * @param alarmRule
	 * @return
	 * @Since :2016年1月22日 下午5:15:08
	 */
	public Map<String, Object> checkState(ThStatus thStatus,
			AlarmRule alarmRule,Double compareValue) ;
	
	public Map<String, Object> thDevieAlarm(ThStatus thStatus,
			AlarmCondition alarmCondition);
	
}
