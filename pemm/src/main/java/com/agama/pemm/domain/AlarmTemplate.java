package com.agama.pemm.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.agama.authority.entity.BaseDomain;

@Entity
public class AlarmTemplate extends BaseDomain {
	/**
	 * long
	 */
	private static final long serialVersionUID = -8702455687112791473L;
	/**
	 * 模板名称
	 */
	private String name;
	/**
	 * 是否启用
	 */
	private Integer enabled;
	private Integer status;
	@Column(length=5000)
	private String remark;
	
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	
	public Integer getEnabled() {
		return enabled;
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	

	
	
	
	

}
