package com.agama.pemm.service;

import com.agama.common.service.IBaseService;
import com.agama.pemm.domain.AlarmLog;

public interface IAlarmLogService extends IBaseService<AlarmLog, Integer> {
	Object getAlarmNumAndTime(String areaInfoStr,String beginDate,String endDate);
}
