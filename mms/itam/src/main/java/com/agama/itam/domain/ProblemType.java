package com.agama.itam.domain;

import javax.persistence.Entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.agama.authority.entity.BaseDomain;
import com.agama.common.enumbean.FirstDeviceType;
import com.agama.common.enumbean.StatusEnum;

/**
 * @Description:问题类型实体对象
 * @Author:佘朝军
 * @Since :2016年1月19日 上午9:25:24
 */
@Entity
@DynamicInsert
@DynamicUpdate
public class ProblemType extends BaseDomain {

	private static final long serialVersionUID = 264077730290912661L;
	private String name;
	private boolean Initial;
	private String otherNote;
	private FirstDeviceType deviceType;
	private StatusEnum status;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isInitial() {
		return Initial;
	}

	public void setInitial(boolean initial) {
		Initial = initial;
	}

	public String getOtherNote() {
		return otherNote;
	}

	public void setOtherNote(String otherNote) {
		this.otherNote = otherNote;
	}

	public FirstDeviceType getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(FirstDeviceType deviceType) {
		this.deviceType = deviceType;
	}

	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}


}
