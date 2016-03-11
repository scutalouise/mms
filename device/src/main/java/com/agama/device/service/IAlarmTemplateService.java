package com.agama.device.service;

import com.agama.common.service.IBaseService;
import com.agama.device.domain.AlarmTemplate;
import com.agama.device.domain.OrgAlarmTemplate;

public interface IAlarmTemplateService extends IBaseService<AlarmTemplate, Integer>{

	public void updateStatusByIds(String ids);
	/**
	 * 根据模板进行告警处理
	 */
	public void alarmHandle(OrgAlarmTemplate orgAlarmTemplate);

}
