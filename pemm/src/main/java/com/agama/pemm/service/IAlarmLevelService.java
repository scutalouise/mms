package com.agama.pemm.service;

import java.util.List;

import com.agama.common.service.IBaseService;
import com.agama.pemm.domain.AlarmLevel;

public interface IAlarmLevelService extends IBaseService<AlarmLevel, Integer> {

	void updateStatusByIds(String ids);

	List<AlarmLevel> getListByStatus(Integer status);

}
