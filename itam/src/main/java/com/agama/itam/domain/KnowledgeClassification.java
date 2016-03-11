package com.agama.itam.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.agama.common.enumbean.EnabledStateEnum;
import com.agama.common.enumbean.StatusEnum;

@Entity
@DynamicInsert
@DynamicUpdate
public class KnowledgeClassification implements Serializable{
	private static final long serialVersionUID = -8147736260981834522L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable = false, nullable = false)
	private Integer id;
	private String name;
	private String code;// 50
	private Integer pid;
	private Integer operator;
	private Date operateTime;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private StatusEnum status;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private EnabledStateEnum enable;

	public KnowledgeClassification() {
		super();
		this.enable = EnabledStateEnum.ENABLED;
		this.status = StatusEnum.NORMAL;
	}

	public KnowledgeClassification(String name, String code, Integer pid, Integer operator, Date operateTime, StatusEnum status, EnabledStateEnum enable) {
		super();
		this.name = name;
		this.code = code;
		this.pid = pid;
		this.operator = operator;
		this.operateTime = operateTime;
		this.status = status;
		this.enable = enable;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public Integer getOperator() {
		return operator;
	}

	public void setOperator(Integer operator) {
		this.operator = operator;
	}

	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}

	public EnabledStateEnum getEnable() {
		return enable;
	}

	public void setEnable(EnabledStateEnum enable) {
		this.enable = enable;
	}

}
