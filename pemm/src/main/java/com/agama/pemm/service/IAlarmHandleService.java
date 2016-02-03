package com.agama.pemm.service;

import com.agama.pemm.domain.UpsStatus;

/**
 * @Description:告警处理业务接口
 * @Author:ranjunfeng
 * @Since :2016年1月6日 上午10:36:15
 */
public interface IAlarmHandleService {
	/**
	 * @Description:UPS报警条件处理业务接口方法
	 * @param upsStatus
	 * @Since :2016年1月6日 上午10:39:04
	 */
	public void upsAlarmCondition(UpsStatus upsStatus);

}
