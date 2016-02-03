package com.agama.device.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.agama.authority.entity.BaseDomain;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@DynamicInsert
@DynamicUpdate
public class TwoDimentionCode extends BaseDomain {

	private static final long serialVersionUID = 7491171331066323553L;
	private String identifier;
	private int isPrint;
	private int printQuantity;
	private int printUserId;
	private Date lastPrintTime;
	private String addr;

	@Column(nullable = false, length = 32)
	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public int getIsPrint() {
		return isPrint;
	}

	public void setIsPrint(int isPrint) {
		this.isPrint = isPrint;
	}

	public int getPrintQuantity() {
		return printQuantity;
	}

	public void setPrintQuantity(int printQuantity) {
		this.printQuantity = printQuantity;
	}

	public int getPrintUserId() {
		return printUserId;
	}

	public void setPrintUserId(int printUserId) {
		this.printUserId = printUserId;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public Date getLastPrintTime() {
		return lastPrintTime;
	}

	public void setLastPrintTime(Date lastPrintTime) {
		this.lastPrintTime = lastPrintTime;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

}
