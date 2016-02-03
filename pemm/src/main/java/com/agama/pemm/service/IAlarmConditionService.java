package com.agama.pemm.service;

import java.util.List;

import com.agama.common.dao.utils.Page;
import com.agama.common.dao.utils.PropertyFilter;
import com.agama.common.domain.StateEnum;
import com.agama.common.enumbean.StatusEnum;
import com.agama.common.service.IBaseService;
import com.agama.pemm.domain.AlarmCondition;
import com.agama.pemm.domain.SwitchInputStatus;
import com.agama.pemm.domain.ThStatus;
import com.agama.pemm.domain.UpsStatus;

/**
 * @Description:报警条件业务接口
 * @Author:ranjunfeng
 * @Since :2015年10月13日 下午2:47:28
 */
public interface IAlarmConditionService extends
		IBaseService<AlarmCondition, Integer> {

	void updateStatusByIds(String ids);
	/**
	 * @Description:UPS报警条件
	 * @param upsStatus
	 * @Since :2015年10月13日 下午2:47:56
	 */
	public void upsAlarmCondition(UpsStatus upsStatus);
	/**
	 * @Description:温湿度报警条件
	 * @param thStatus
	 * @Since :2015年10月13日 下午2:48:18
	 */
	public void thAlarmCondition(ThStatus thStatus);
	/**
	 * @Description:开关量输入报警条件处理
	 * @param inputStatus 开关量输入状态信息
	 * @return 状态
	 * @Since :2015年10月15日 下午4:57:51
	 */
	public StateEnum switchInputAlarmCondition(SwitchInputStatus switchInputStatus);
	
	public void cleanAlarmConditionMap() ;
}
