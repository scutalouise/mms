package com.agama.device.service;

import java.util.List;

import com.agama.common.enumbean.StatusEnum;
import com.agama.common.service.IBaseService;
import com.agama.device.domain.AlarmLevel;

public interface IAlarmLevelService extends IBaseService<AlarmLevel, Integer> {

	public void updateStatusByIds(String ids);

	public List<AlarmLevel> getListByStatus(StatusEnum status);

}
