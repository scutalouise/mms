package com.agama.device.service;

import com.agama.common.service.IBaseService;
import com.agama.device.domain.OrgAlarmTemplate;

public interface IOrgAlarmTemplateService extends
		IBaseService<OrgAlarmTemplate, Integer> {

	public OrgAlarmTemplate findByOrgId(Integer orgId);

	public void saveOrgAlarmTemplateId(OrgAlarmTemplate orgAlarmTemplate);

}
