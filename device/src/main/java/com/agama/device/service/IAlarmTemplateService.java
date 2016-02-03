package com.agama.device.service;

import com.agama.common.service.IBaseService;
import com.agama.device.domain.AlarmTemplate;

public interface IAlarmTemplateService extends IBaseService<AlarmTemplate, Integer>{

	public void updateStatusByIds(String ids);

}
