package com.agama.itam.mongo.domain;

import org.bson.types.ObjectId;

/**
 * @Description:设备巡检详情
 * @Author:佘朝军
 * @Since :2016年1月7日 下午3:25:16
 */
public class DeviceInspectStatus {

	private ObjectId id;

	private String identifier;

	private ObjectId inspectRecordId;

	private String inspectDeviceStatus;

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public ObjectId getInspectRecordId() {
		return inspectRecordId;
	}

	public void setInspectRecordId(ObjectId inspectRecordId) {
		this.inspectRecordId = inspectRecordId;
	}

	public String getInspectDeviceStatus() {
		return inspectDeviceStatus;
	}

	public void setInspectDeviceStatus(String inspectDeviceStatus) {
		this.inspectDeviceStatus = inspectDeviceStatus;
	}

}
