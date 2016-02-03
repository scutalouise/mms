package com.agama.authority.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "authority_transmit")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class AuthorityTransmit implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer transmitUserId;
	private Integer acceptUserId;
	private Integer isTransmit;
	private String transmitRoles;
	private Date transmitTime;
	private Date cancelTransmitTime;

	public AuthorityTransmit() {
		super();
	}

	public AuthorityTransmit(Integer id, Integer transmitUserId, Integer acceptUserId, Integer isTransmit,
			String transmitRoles, Date transmitTime, Date cancelTransmitTime) {
		super();
		this.id = id;
		this.transmitUserId = transmitUserId;
		this.acceptUserId = acceptUserId;
		this.isTransmit = isTransmit;
		this.transmitRoles = transmitRoles;
		this.transmitTime = transmitTime;
		this.cancelTransmitTime = cancelTransmitTime;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
	@Column(nullable=false)
	public Integer getTransmitUserId() {
		return transmitUserId;
	}

	public void setTransmitUserId(Integer transmitUserId) {
		this.transmitUserId = transmitUserId;
	}

	@Column(nullable=false)
	public Integer getAcceptUserId() {
		return acceptUserId;
	}

	public void setAcceptUserId(Integer acceptUserId) {
		this.acceptUserId = acceptUserId;
	}

	@Column(nullable=false)
	public Integer getIsTransmit() {
		return isTransmit;
	}

	public void setIsTransmit(Integer isTransmit) {
		this.isTransmit = isTransmit;
	}

	public String getTransmitRoles() {
		return transmitRoles;
	}

	public void setTransmitRoles(String transmitRoles) {
		this.transmitRoles = transmitRoles;
	}

	@Column(nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getTransmitTime() {
		return transmitTime;
	}

	public void setTransmitTime(Date transmitTime) {
		this.transmitTime = transmitTime;
	}

	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=true)
	public Date getCancelTransmitTime() {
		return cancelTransmitTime;
	}

	public void setCancelTransmitTime(Date cancelTransmitTime) {
		this.cancelTransmitTime = cancelTransmitTime;
	}

}
