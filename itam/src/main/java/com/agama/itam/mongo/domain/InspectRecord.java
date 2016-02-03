package com.agama.itam.mongo.domain;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @Description:巡检设备记录
 * @Author:佘朝军
 * @Since :2016年1月7日 下午3:24:54
 */
public class InspectRecord {

	private ObjectId id;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date inspectTime;

	private String inspectStatus;

	private int orgId;

	private int deviceTotal;

	private int inspectedTotal;
	
	private int uncheckedTotal;

	private int inexistendTotal;

	private int userId;

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
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

	public int getOrgId() {
		return orgId;
	}

	public void setOrgId(int orgId) {
		this.orgId = orgId;
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

	public int getUncheckedTotal() {
		return uncheckedTotal;
	}

	public void setUncheckedTotal(int uncheckedTotal) {
		this.uncheckedTotal = uncheckedTotal;
	}

	public int getInexistendTotal() {
		return inexistendTotal;
	}

	public void setInexistendTotal(int inexistendTotal) {
		this.inexistendTotal = inexistendTotal;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

}
