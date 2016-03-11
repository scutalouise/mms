package com.agama.device.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Description:用户负责管理的机构（内部人员与内部机构）
 * @Author:杨远高
 * @Since :2016年2月29日 上午10:31:56
 */
@Entity
@Table(name = "user_admin_org")
public class UserAdminOrg implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer userId;
	private Integer orgId;

	// Constructors

	/** default constructor */
	public UserAdminOrg() {
	}

	/** full constructor */
	public UserAdminOrg(Integer userId, Integer orgId) {
		this.userId = userId;
		this.orgId = orgId;
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

	@Column(name = "user_id", nullable = false)
	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(name = "org_id", nullable = false)
	public Integer getOrgId() {
		return this.orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

}