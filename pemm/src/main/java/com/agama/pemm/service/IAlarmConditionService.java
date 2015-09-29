package com.agama.pemm.service;

import com.agama.common.service.IBaseService;
import com.agama.pemm.domain.AlarmCondition;

public interface IAlarmConditionService extends
		IBaseService<AlarmCondition, Integer> {

	void updateStatusByIds(String ids);

}
