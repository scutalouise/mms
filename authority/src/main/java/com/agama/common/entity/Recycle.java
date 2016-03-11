package com.agama.common.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.agama.common.enumbean.RecycleEnum;

@Entity
public class Recycle implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Column(nullable = false)
	private Integer opUserId;
	@Transient
	private String opUserName;
	@Column(nullable = false)
	private Date opTime;
	@Column(nullable = false)
	private String content;
	@Column(nullable = false)
	private String tableName;
	@Column(nullable = false)
	private String tableRecordId;
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private RecycleEnum isRecovery;
	@Transient
	private String recoveryString;
	private Integer recoveryUserId;
	@Transient
	private String recoveryUserName;
	private Date recoveryTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOpUserId() {
		return opUserId;
	}

	public void setOpUserId(Integer opUserId) {
		this.opUserId = opUserId;
	}

	public String getOpUserName() {
		return opUserName;
	}

	public void setOpUserName(String opUserName) {
		this.opUserName = opUserName;
	}

	public Date getOpTime() {
		return opTime;
	}

	public void setOpTime(Date opTime) {
		this.opTime = opTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableRecordId() {
		return tableRecordId;
	}

	public void setTableRecordId(String tableRecordId) {
		this.tableRecordId = tableRecordId;
	}

	public RecycleEnum getIsRecovery() {
		return isRecovery;
	}

	public void setIsRecovery(RecycleEnum isRecovery) {
		this.isRecovery = isRecovery;
	}

	public Integer getRecoveryUserId() {
		return recoveryUserId;
	}

	public void setRecoveryUserId(Integer recoveryUserId) {
		this.recoveryUserId = recoveryUserId;
	}

	public Date getRecoveryTime() {
		return recoveryTime;
	}

	public void setRecoveryTime(Date recoveryTime) {
		this.recoveryTime = recoveryTime;
	}


	public String getRecoveryUserName() {
		return recoveryUserName;
	}

	public void setRecoveryUserName(String recoveryUserName) {
		this.recoveryUserName = recoveryUserName;
	}

	public String getRecoveryString() {
		return recoveryString;
	}

	public void setRecoveryString(String recoveryString) {
		this.recoveryString = recoveryString;
	}

}
