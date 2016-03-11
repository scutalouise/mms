package com.agama.itam.bean;

import java.util.Date;

public class DeviceInspectChartBean {
	private int orgId;
	private Date inspectTime;
	private String inspectStatus;
	private int deviceTotal;
	private int inspectedTotal;
	private int inexistendTotal;
	private int uncheckedTotal;
	private int userId;

	public int getOrgId() {
		return orgId;
	}

	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}

	public Date getInspectTime() {
		return inspectTime;
	}

	public void setInspectTime(Date inspectTime) {
		this.inspectTime = inspectTime;
	}

	public String getInspectStatus() {
		return inspectStatus;
	}

	public void setInspectStatus(String inspectStatus) {
		this.inspectStatus = inspectStatus;
	}

	public int getDeviceTotal() {
		return deviceTotal;
	}

	public void setDeviceTotal(int deviceTotal) {
		this.deviceTotal = deviceTotal;
	}

	public int getInspectedTotal() {
		return inspectedTotal;
	}

	public void setInspectedTotal(int inspectedTotal) {
		this.inspectedTotal = inspectedTotal;
	}

	public int getInexistendTotal() {
		return inexistendTotal;
	}

	public void setInexistendTotal(int inexistendTotal) {
		this.inexistendTotal = inexistendTotal;
	}

	public int getUncheckedTotal() {
		return uncheckedTotal;
	}

	public void setUncheckedTotal(int uncheckedTotal) {
		this.uncheckedTotal = uncheckedTotal;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
}
