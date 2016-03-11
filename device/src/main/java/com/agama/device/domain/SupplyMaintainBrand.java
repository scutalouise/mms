package com.agama.device.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Description:供应商(运维)机构与品牌对应的关系；
 * @Author:杨远高
 * @Since :2016年2月29日 下午5:49:56
 */
@Entity
@Table(name = "supply_maintain_brand")
public class SupplyMaintainBrand implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer supplyMaintainOrgId;
	private Integer brandId;

	// Constructors

	/** default constructor */
	public SupplyMaintainBrand() {
	}

	/** full constructor */
	public SupplyMaintainBrand(Integer supplyMaintainOrgId, Integer brandId) {
		this.supplyMaintainOrgId = supplyMaintainOrgId;
		this.brandId = brandId;
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

	@Column(name = "supply_maintain_org_id",nullable = false)
	public Integer getSupplyMaintainOrgId() {
		return supplyMaintainOrgId;
	}

	public void setSupplyMaintainOrgId(Integer supplyMaintainOrgId) {
		this.supplyMaintainOrgId = supplyMaintainOrgId;
	}

	@Column(name = "barnd_id", nullable = false)
	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}


}