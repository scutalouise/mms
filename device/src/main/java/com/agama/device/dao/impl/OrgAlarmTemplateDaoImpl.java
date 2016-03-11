package com.agama.device.dao.impl;

import org.springframework.stereotype.Repository;

import com.agama.common.dao.impl.HibernateDaoImpl;
import com.agama.device.dao.IOrgAlarmTemplateDao;
import com.agama.device.domain.OrgAlarmTemplate;
@Repository

public class OrgAlarmTemplateDaoImpl extends
		HibernateDaoImpl<OrgAlarmTemplate, Integer> implements
		IOrgAlarmTemplateDao {

	

}
