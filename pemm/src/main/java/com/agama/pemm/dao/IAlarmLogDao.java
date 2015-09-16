package com.agama.pemm.dao;

import com.agama.common.dao.IBaseDao;
import com.agama.pemm.domain.AlarmLog;

public interface IAlarmLogDao extends IBaseDao<AlarmLog, Integer> {

	Object getAlarmNumAndTime(String areaInfoStr,String beginDate,String endDate);

}
