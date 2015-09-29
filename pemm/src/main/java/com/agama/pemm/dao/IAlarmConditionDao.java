package com.agama.pemm.dao;

import com.agama.common.dao.IBaseDao;
import com.agama.pemm.domain.AlarmCondition;

public interface IAlarmConditionDao extends IBaseDao<AlarmCondition, Integer> {

	void updateStatusByIds(String ids);

}
