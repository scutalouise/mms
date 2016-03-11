package com.agama.device.service.impl;

import java.util.List;

import org.apache.xmlbeans.impl.xb.xsdschema.RestrictionDocument.Restriction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agama.common.service.impl.BaseServiceImpl;
import com.agama.device.dao.IAlarmTemplateDao;
import com.agama.device.dao.IOrgAlarmTemplateDao;
import com.agama.device.domain.AlarmTemplate;
import com.agama.device.domain.OrgAlarmTemplate;
import com.agama.device.service.IOrgAlarmTemplateService;
@Service
@Transactional
public class OrgAlarmTemplateServiceImpl extends
		BaseServiceImpl<OrgAlarmTemplate, Integer> implements
		IOrgAlarmTemplateService {
	@Autowired
	private IOrgAlarmTemplateDao orgAlarmTemplateDao;
	@Autowired
	private IAlarmTemplateDao alarmTemplateDao;

	@Override
	public OrgAlarmTemplate findByOrgId(Integer orgId) {
		// TODO Auto-generated method stub
		List<OrgAlarmTemplate> orgAlarmTemplates=orgAlarmTemplateDao.find(Restrictions.eq("orgId", orgId));
		OrgAlarmTemplate orgAlarmTemplate=null;
		if(orgAlarmTemplates.size()>0){
			orgAlarmTemplate=orgAlarmTemplates.get(0);
			AlarmTemplate alarmTemplate=alarmTemplateDao.find(orgAlarmTemplate.getAlarmTemplateId());
			orgAlarmTemplate.setAlarmTemplateName(alarmTemplate.getName());
			
		}
		return orgAlarmTemplate;
	}

	@Override
	public void saveOrgAlarmTemplateId(OrgAlarmTemplate orgAlarmTemplate) {
		OrgAlarmTemplate oat=orgAlarmTemplateDao.findUnique(Restrictions.eq("orgId", orgAlarmTemplate.getOrgId()));
		if(oat!=null){
			orgAlarmTemplateDao.delete(oat.getId());
		}
		orgAlarmTemplateDao.save(orgAlarmTemplate);
		
	}

}
