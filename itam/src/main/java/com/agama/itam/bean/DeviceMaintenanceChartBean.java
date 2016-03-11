package com.agama.itam.bean;

import java.util.Date;

public class DeviceMaintenanceChartBean {
	
	private Object problemId;
	private Integer orgId;
	private String orgName;
	private Date recordTime;
	private Date resolveTime;
	private String recordUserName;
	private String resolveUserName;
	private Integer problemTypeId;
	private String problemType;
	private String name;
	private String identifier;
	private String description;
	private String reportWay; 			// （1.电话报修  2.口头报备 3.手持机) 上报渠道
	private String enable; 			// （0：新问题，1：打回，2:已分配，3：处理中，4，已解决，5：已关闭） 问提状态
	
	public Object getProblemId() {
		return problemId;
	}
	public void setProblemId(Object problemId) {
		this.problemId = problemId;
	}
	public Integer getOrgId() {
		return orgId;
	}
	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public Date getRecordTime() {
		return recordTime;
	}
	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}
	public Date getResolveTime() {
		return resolveTime;
	}
	public void setResolveTime(Date resolveTime) {
		this.resolveTime = resolveTime;
	}
	public String getRecordUserName() {
		return recordUserName;
	}
	public void setRecordUserName(String recordUserName) {
		this.recordUserName = recordUserName;
	}
	public String getResolveUserName() {
		return resolveUserName;
	}
	public void setResolveUserName(String resolveUserName) {
		this.resolveUserName = resolveUserName;
	}
	public Integer getProblemTypeId() {
		return problemTypeId;
	}
	public void setProblemTypeId(Integer problemTypeId) {
		this.problemTypeId = problemTypeId;
	}
	public String getProblemType() {
		return problemType;
	}
	public void setProblemType(String problemType) {
		this.problemType = problemType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Object getReportWay() {
		return reportWay;
	}
	public String getEnable() {
		return enable;
	}
	public void setEnable(String enable) {
		this.enable = enable;
	}
	public void setReportWay(String reportWay) {
		this.reportWay = reportWay;
	}
	
}
