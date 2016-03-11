package com.agama.device.service;

import java.util.List;

import com.agama.common.dao.utils.Page;
import com.agama.common.dao.utils.PropertyFilter;
import com.agama.common.domain.StateEnum;
import com.agama.common.enumbean.OperationType;
import com.agama.common.enumbean.StatusEnum;
import com.agama.common.service.IBaseService;
import com.agama.device.domain.DeviceAlarmRule;

/**
 * @Description:设备告警规则业务接口
 * @Author:ranjunfeng
 * @Since :2016年3月3日 下午1:28:45
 */
public interface IDeviceAlarmRuleService extends
		IBaseService<DeviceAlarmRule, Integer> {

	Page<DeviceAlarmRule> searchByAlarmTemplateId(Page<DeviceAlarmRule> page,
			 Integer alarmTemplateId,StatusEnum status);
	/**
	 * @Description:根据告警规则的id集合删除对象
	 * @param deviceAlarmRuleIdList
	 * @Since :2016年3月3日 下午6:53:17
	 */
	public void delete(List<Integer> deviceAlarmRuleIdList);
	
	/**
	 * @Description: 规则比较
	 * @param operationType 操作类型
	 * @param compareValue 待比较的值
	 * @param value 正常值
	 * @param minValue 最小值
	 * @param maxValue 最大值
	 * @return
	 * @Since :2015年10月8日 上午9:43:27
	 */
	public StateEnum ruleCompare(OperationType operationType,
			Double compareValue, Double value, Double minValue,
			Double maxValue,StateEnum state);

}
