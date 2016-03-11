package com.agama.device.domain;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.agama.common.enumbean.EnabledStateEnum;
import com.agama.common.enumbean.StatusEnum;

/**
 * @Description:供应商与运维单位机构表，entity
 * @Author:杨远高
 * @Since :2016年2月29日 上午10:34:03
 */
@Entity
@Table(name = "supply_maintain_org")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate @DynamicInsert
public class SupplyMaintainOrg implements java.io.Serializable {

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
	private String director;//负责人(联系人)
	private String telephone;//联系电话
	private String email;//联系电邮
	private String fax;//传真
	private String serviceContent;//服务内容(条件)
	private Integer supplyOrg;
 	private EnabledStateEnum enable;	//是否启用
	private StatusEnum status;			//是否删除

	
	// Constructors

	/** default constructor */
	public SupplyMaintainOrg() {
		this.enable = EnabledStateEnum.ENABLED;
		this.status = StatusEnum.NORMAL;
	}

	/** minimal constructor */
	public SupplyMaintainOrg(Integer id, String orgName) {
		this.id = id;
		this.orgName = orgName;
	}

	/** full constructor */
	public SupplyMaintainOrg(Integer id, String orgName, Integer pid, String orgType, Integer orgSort, Integer orgLevel, String orgCode, Integer areaId, String director, String telephone,
			String email, String fax, String serviceContent, Integer supplyOrg, EnabledStateEnum enable, StatusEnum status) {
		super();
		this.id = id;
		this.orgName = orgName;
		this.pid = pid;
		this.orgType = orgType;
		this.orgSort = orgSort;
		this.orgLevel = orgLevel;
		this.orgCode = orgCode;
		this.areaId = areaId;
		this.director = director;
		this.telephone = telephone;
		this.email = email;
		this.fax = fax;
		this.serviceContent = serviceContent;
		this.supplyOrg = supplyOrg;
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

	@Column(name = "director")
	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	@Column(name = "telephone")
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	@Column(name = "email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "fax")
	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	@Column(name = "service_content")
	public String getServiceContent() {
		return serviceContent;
	}

	public void setServiceContent(String serviceContent) {
		this.serviceContent = serviceContent;
	}

	public Integer getSupplyOrg() {
		return supplyOrg;
	}

	public void setSupplyOrg(Integer supplyOrg) {
		this.supplyOrg = supplyOrg;
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