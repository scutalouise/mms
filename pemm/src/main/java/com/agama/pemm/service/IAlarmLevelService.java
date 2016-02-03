package com.agama.pemm.service;

import java.util.List;

import com.agama.common.service.IBaseService;
import com.agama.pemm.domain.AlarmLevel;

public interface IAlarmLevelService extends IBaseService<AlarmLevel, Integer> {

	public void updateStatusByIds(String ids);

	public List<AlarmLevel> getListByStatus(Integer status);

}
