package com.agama.device.domain;

import javax.persistence.Entity;
import javax.persistence.Transient;

import com.agama.authority.entity.BaseDomain;


@Entity
public class OrgAlarmTemplate extends BaseDomain{
	/**
	 * long
	 */
	private static final long serialVersionUID = 1L;
	private Integer orgId;
	private Integer alarmTemplateId;
	@Transient
	private String alarmTemplateName;
	public Integer getOrgId() {
		return orgId;
	}
	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}
	public Integer getAlarmTemplateId() {
		return alarmTemplateId;
	}
	public void setAlarmTemplateId(Integer alarmTemplateId) {
		this.alarmTemplateId = alarmTemplateId;
	}
	public String getAlarmTemplateName() {
		return alarmTemplateName;
	}
	public void setAlarmTemplateName(String alarmTemplateName) {
		this.alarmTemplateName = alarmTemplateName;
	}
	
	
	
}
