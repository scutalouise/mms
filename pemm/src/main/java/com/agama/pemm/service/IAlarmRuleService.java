package com.agama.pemm.service;

import com.agama.common.domain.StateEnum;
import com.agama.common.enumbean.OperationType;
import com.agama.common.service.IBaseService;
import com.agama.pemm.domain.AlarmRule;

public interface IAlarmRuleService extends IBaseService<AlarmRule, Integer> {

	/**
	 * @Description:根据ids字符串修改状态
	 * @param ids id字符串
	 * @Since :2015年9月30日 上午10:24:08
	 */
	public void updateStatusByIds(String ids);
	/**
	 * @Description: 规则比较
	 * @param operationType
	 *            操作类型
	 * @param compareValue
	 *            待比较的值
	 * @param value
	 *            正常值
	 * @param minValue
	 *            最小值
	 * @param maxValue
	 *            最大值
	 * @return
	 * @Since :2015年10月8日 上午9:43:27
	 */
	public StateEnum ruleCompare(OperationType operationType,
			Double compareValue, Integer value, Integer minValue,
			Integer maxValue,StateEnum state);
	

}
