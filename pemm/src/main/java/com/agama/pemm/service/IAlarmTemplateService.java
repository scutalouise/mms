package com.agama.pemm.service;

import com.agama.common.service.IBaseService;
import com.agama.pemm.domain.AlarmTemplate;

public interface IAlarmTemplateService extends IBaseService<AlarmTemplate, Integer>{

	public void updateStatusByIds(String ids);

}
