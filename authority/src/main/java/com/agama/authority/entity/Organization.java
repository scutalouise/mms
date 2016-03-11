package com.agama.authority.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.agama.common.enumbean.EnabledStateEnum;
import com.agama.common.enumbean.StatusEnum;

/**
 * 机构entity
 * @author kkomge
 * @date 2015年5月9日 
 */
@Entity
@Table(name = "organization")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate @DynamicInsert
public class Organization implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String orgName;
	private Integer pid;
	private String orgType;
	private Integer orgSort;
	private Integer orgLevel;
	private String orgCode;
	private Integer areaId;
	private Integer currentState;
	private Double longitude;
	private Double latitude;
	private String address;
	private String contact;
	//2016年2月2日，将删除与启用2个字段与目前已有的项目统一处理，命名；
	private EnabledStateEnum enable;	//是否启用
	private StatusEnum status;			//是否删除

	
	// Constructors

	/** default constructor */
	public Organization() {
		this.enable = EnabledStateEnum.ENABLED;
		this.status = StatusEnum.NORMAL;
	}

	/** minimal constructor */
	public Organization(Integer id, String orgName) {
		this.id = id;
		this.orgName = orgName;
	}

	/** full constructor */
	public Organization(Integer id, String orgName, Integer pid, String orgType, Integer orgSort, Integer orgLevel, String orgCode, Integer areaId, Integer currentState, Double longitude,
			Double latitude, String address, String contact, EnabledStateEnum enable, StatusEnum status) {
		super();
		this.id = id;
		this.orgName = orgName;
		this.pid = pid;
		this.orgType = orgType;
		this.orgSort = orgSort;
		this.orgLevel = orgLevel;
		this.orgCode = orgCode;
		this.areaId = areaId;
		this.currentState = currentState;
		this.longitude = longitude;
		this.latitude = latitude;
		this.address = address;
		this.contact = contact;
		this.enable = enable;
		this.status = status;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}


	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "org_name", nullable = false)
	public String getOrgName() {
		return this.orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	@Column(name = "pid")
	public Integer getPid() {
		return this.pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	@Column(name = "org_type")
	public String getOrgType() {
		return this.orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	@Column(name = "org_sort")
	public Integer getOrgSort() {
		return this.orgSort;
	}

	public void setOrgSort(Integer orgSort) {
		this.orgSort = orgSort;
	}

	@Column(name = "org_level")
	public Integer getOrgLevel() {
		return this.orgLevel;
	}

	public void setOrgLevel(Integer orgLevel) {
		this.orgLevel = orgLevel;
	}
	@Column(name = "org_code")
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	@Column(name = "area_id")
	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}
	@Transient
	public Integer getCurrentState() {
		return currentState;
	}

	public void setCurrentState(Integer currentState) {
		this.currentState = currentState;
	}
	@Column(name = "longitude")
	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	@Column(name = "latitude")
	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	@Column(name = "enable")
	@Enumerated(EnumType.STRING)
	public EnabledStateEnum getEnable() {
		return enable;
	}

	public void setEnable(EnabledStateEnum enable) {
		this.enable = enable;
	}
	
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Organization [id=" + id + ", enable=" + enable + ", status=" + status + "]";
	}
}