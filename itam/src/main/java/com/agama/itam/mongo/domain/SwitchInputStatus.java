package com.agama.itam.mongo.domain;

import java.util.Date;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

import com.agama.common.domain.StateEnum;
import com.agama.common.enumbean.StatusEnum;

@Document
public class SwitchInputStatus {
	@Id
	private String id;
	private String name; //名称
	private Integer inputSignal; //信号
	private Date collectTime; //获取数据时间
	private StateEnum currentState; //当前状态
	private StatusEnum status;//删除状态
	private String identifier; //动环设备唯一标识
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getInputSignal() {
		return inputSignal;
	}
	public void setInputSignal(Integer inputSignal) {
		this.inputSignal = inputSignal;
	}
	public Date getCollectTime() {
		return collectTime;
	}
	public void setCollectTime(Date collectTime) {
		this.collectTime = collectTime;
	}
	public StateEnum getCurrentState() {
		return currentState;
	}
	public void setCurrentState(StateEnum currentState) {
		this.currentState = currentState;
	}
	public StatusEnum getStatus() {
		return status;
	}
	public void setStatus(StatusEnum status) {
		this.status = status;
	}
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	
	
}
