package com.agama.itam.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.agama.common.enumbean.StatusEnum;

/**
 * @Description:设备外修记录
 * @Author:佘朝军
 * @Since :2016年3月2日 上午10:02:29
 */
@Entity
@DynamicInsert
@DynamicUpdate
public class OutsideRepair {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String identifier;
	@Transient
	private String deviceName;
	private int firm; 					// 返修厂家
	@Transient
	private String firmName;
	private int repairOpertor; 			// 返修操作人
	@Transient
	private String repairOpertorName;
	private int repairReceiver;			// 返修收件人
	@Transient
	private String repairReceiverName;
	private Date repairTime; 			// 返修时间
	@Column(length = 1000)
	private String repairRemark; 		// 返修标注
	private String repairResult; 		// 返修结果(0：外修失败、设备报废；1：外修完成、设备入库)
	private String returnTime; 			// 返回时间
	private Integer returnReceiver; 	// 返回接收人
	@Transient
	private String returnReceiverName;
	@Column(length = 1000)
	private String returnRemark; 		// 返修结果描述
	@Enumerated(EnumType.STRING)
	private StatusEnum status;

	
	
	public OutsideRepair() {}

	public OutsideRepair(int id, String identifier, int firm, String firmName, int repairOpertor, String repairOpertorName, int repairReceiver, String repairReceiverName,
			Date repairTime, String repairRemark, String repairResult, String returnTime, Integer returnReceiver, String returnRemark) {
		this.id = id;
		this.identifier = identifier;
		this.firm = firm;
		this.firmName = firmName;
		this.repairOpertor = repairOpertor;
		this.repairOpertorName = repairOpertorName;
		this.repairReceiver = repairReceiver;
		this.repairReceiverName = repairReceiverName;
		this.repairTime = repairTime;
		this.repairRemark = repairRemark;
		this.repairResult = repairResult;
		this.returnTime = returnTime;
		this.returnReceiver = returnReceiver;
		this.returnRemark = returnRemark;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public int getFirm() {
		return firm;
	}

	public void setFirm(int firm) {
		this.firm = firm;
	}

	public String getFirmName() {
		return firmName;
	}

	public void setFirmName(String firmName) {
		this.firmName = firmName;
	}

	public int getRepairOpertor() {
		return repairOpertor;
	}

	public void setRepairOpertor(int repairOpertor) {
		this.repairOpertor = repairOpertor;
	}

	public String getRepairOpertorName() {
		return repairOpertorName;
	}

	public void setRepairOpertorName(String repairOpertorName) {
		this.repairOpertorName = repairOpertorName;
	}

	public int getRepairReceiver() {
		return repairReceiver;
	}

	public void setRepairReceiver(int repairReceiver) {
		this.repairReceiver = repairReceiver;
	}

	public String getRepairReceiverName() {
		return repairReceiverName;
	}

	public void setRepairReceiverName(String repairReceiverName) {
		this.repairReceiverName = repairReceiverName;
	}

	public Date getRepairTime() {
		return repairTime;
	}

	public void setRepairTime(Date repairTime) {
		this.repairTime = repairTime;
	}

	public String getRepairRemark() {
		return repairRemark;
	}

	public void setRepairRemark(String repairRemark) {
		this.repairRemark = repairRemark;
	}

	public String getRepairResult() {
		return repairResult;
	}

	public void setRepairResult(String repairResult) {
		this.repairResult = repairResult;
	}

	public String getReturnTime() {
		return returnTime;
	}

	public void setReturnTime(String returnTime) {
		this.returnTime = returnTime;
	}

	public Integer getReturnReceiver() {
		return returnReceiver;
	}

	public void setReturnReceiver(Integer returnReceiver) {
		this.returnReceiver = returnReceiver;
	}

	public String getReturnRemark() {
		return returnRemark;
	}

	public void setReturnRemark(String returnRemark) {
		this.returnRemark = returnRemark;
	}

	public String getReturnReceiverName() {
		return returnReceiverName;
	}

	public void setReturnReceiverName(String returnReceiverName) {
		this.returnReceiverName = returnReceiverName;
	}

	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}

}
