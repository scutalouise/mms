package com.agama.authority.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Transient;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.agama.common.enumbean.EnabledStateEnum;
import com.agama.common.enumbean.StatusEnum;

/**
 * 区域entity
 * 
 * @author kkomge
 * @date 2015年5月9日
 */
@Entity
@Table(name = "area_info")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class AreaInfo implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String areaCode;
	private String areaName;
	private Integer pid;
	private Integer sort;
	
	private List<Organization> goodOrganizations;
	private List<Organization> warningOrganizations;
	private List<Organization> errorOrganizations;
	//2016年2月2日，将删除与启用2个字段与目前已有的项目统一处理，命名；
	private EnabledStateEnum enable;	//是否启用
	private StatusEnum status;			//是否删除

	// Constructors
	
	/** default constructor */
	public AreaInfo() {
		this.enable = EnabledStateEnum.ENABLED;
		this.status = StatusEnum.NORMAL;
	}

	/** minimal constructor */
	public AreaInfo(String areaCode, String areaName, Integer pid) {
		this.areaCode = areaCode;
		this.areaName = areaName;
		this.pid = pid;
	}

	/** full constructor */
	public AreaInfo(String areaCode, String areaName, Integer pid, Integer sort,StatusEnum status,EnabledStateEnum enable) {
		this.areaCode = areaCode;
		this.areaName = areaName;
		this.pid = pid;
		this.sort = sort;
		this.status = status;
		this.enable = enable;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "AREA_CODE", nullable = false, length = 12)
	public String getAreaCode() {
		return this.areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	@Column(name = "AREA_NAME", nullable = false, length = 50)
	public String getAreaName() {
		return this.areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	@Column(name = "PID")
	public Integer getPid() {
		return this.pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	@Column(name = "SORT")
	public Integer getSort() {
		return this.sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	@Transient
	public List<Organization> getGoodOrganizations() {
		return goodOrganizations;
	}

	public void setGoodOrganizations(List<Organization> goodOrganizations) {
		this.goodOrganizations = goodOrganizations;
	}
	@Transient
	public List<Organization> getWarningOrganizations() {
		return warningOrganizations;
	}

	public void setWarningOrganizations(List<Organization> warningOrganizations) {
		this.warningOrganizations = warningOrganizations;
	}
	@Transient
	public List<Organization> getErrorOrganizations() {
		return errorOrganizations;
	}

	public void setErrorOrganizations(List<Organization> errorOrganizations) {
		this.errorOrganizations = errorOrganizations;
	}

	@Column(name = "ENABLE")
	@Enumerated(EnumType.STRING)
	public EnabledStateEnum getEnable() {
		return enable;
	}

	public void setEnable(EnabledStateEnum enable) {
		this.enable = enable;
	}

	@Column(name = "STATUS")
	@Enumerated(EnumType.STRING)
	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "AreaInfo [id=" + id + ", enable=" + enable + ", status=" + status + "]";
	}
}