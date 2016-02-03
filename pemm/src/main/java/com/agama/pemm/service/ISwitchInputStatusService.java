package com.agama.pemm.service;

import java.util.List;
import java.util.Map;

import com.agama.common.enumbean.DeviceInterfaceType;
import com.agama.common.service.IBaseService;
import com.agama.pemm.domain.AlarmRule;
import com.agama.pemm.domain.Device;
import com.agama.pemm.domain.SwitchInputStatus;

/**
 * @Description:开关量输入状态信息业务逻辑接口
 * @Author:ranjunfeng
 * @Since :2015年10月15日 下午3:47:10
 */
public interface ISwitchInputStatusService extends
		IBaseService<SwitchInputStatus, Integer> {
	/**
	 * @Description:检测开关量输出报警状态
	 * @param switchInputStatus
	 * @param id
	 * @return
	 * @Since :2015年10月15日 下午5:15:03
	 */
	public Map<String, Object> checkAlarm(SwitchInputStatus switchInputStatus,
			Integer conditionId, Device device);

	/**
	 * @Description:根据采集的信息和报警规则检测开关量输出的运行状态
	 * @param switchInputStatus
	 * @param alarmRule
	 * @return
	 * @Since :2015年10月19日 上午9:17:09
	 */
	public Map<String, Object> checkSwitchInputState(
			SwitchInputStatus switchInputStatus, AlarmRule alarmRule);

	/**
	 * @Description:根据上位机ID和连接设备的类型查询最新的开关量输入的状态信息
	 * @param gitInfoId 上位机ID
	 * @param deviceInterfaceType 连接设备的类型
	 * @return
	 * @Since :2015年10月19日 上午11:33:00
	 */
	public List<SwitchInputStatus> findLatestData(Integer gitInfoId,
			DeviceInterfaceType deviceInterfaceType);

}
