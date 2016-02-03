package com.agama.pemm.dao;

import com.agama.common.dao.IBaseDao;
import com.agama.pemm.domain.AlarmTemplate;

public interface IAlarmTemplateDao extends IBaseDao<AlarmTemplate, Integer> {

	void updateStatusByIds(String ids);

}
