package com.agama.authority.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.agama.common.enumbean.EnabledStateEnum;
import com.agama.common.enumbean.StatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 角色entity
 * 
 * @author ty
 * @date 2015年1月13日
 */
@Entity
@Table(name = "role")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
// 参考：http://stackoverflow.com/questions/24994440/no-serializer-found-for-class-org-hibernate-proxy-pojo-javassist-javassist
public class Role implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private String roleCode;
	private String description;
	private Short sort;
	@JsonIgnore
	private Set<UserRole> userRoles = new HashSet<UserRole>(0);
	@JsonIgnore
	private Set<RolePermission> rolePermissions = new HashSet<RolePermission>(0);
	//2016年2月2日，将删除与启用2个字段与目前已有的项目统一处理，命名；
	private EnabledStateEnum enable;	// 是否启用
	private StatusEnum status;			// 是否删除

	// Constructors

	/** default constructor */
	public Role() {
		this.enable = EnabledStateEnum.ENABLED;
		this.status = StatusEnum.NORMAL;
	}

	public Role(Integer id) {
		this.id = id;
	}

	/** minimal constructor */
	public Role(String name, String roleCode) {
		this.name = name;
		this.roleCode = roleCode;
	}

	/** full constructor */
	public Role(String name, String roleCode, String description, Short sort, String delFlag, Set<UserRole> userRoles, Set<RolePermission> rolePermissions, EnabledStateEnum enable,
			StatusEnum status) {
		this.name = name;
		this.roleCode = roleCode;
		this.description = description;
		this.sort = sort;
		this.userRoles = userRoles;
		this.rolePermissions = rolePermissions;
		this.enable = enable;
		this.status = status;
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

	@Column(name = "NAME", nullable = false, length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "ROLE_CODE", nullable = false, length = 20)
	public String getRoleCode() {
		return this.roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	@Column(name = "DESCRIPTION", length = 65535)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "SORT")
	public Short getSort() {
		return this.sort;
	}

	public void setSort(Short sort) {
		this.sort = sort;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "role")
	public Set<UserRole> getUserRoles() {
		return this.userRoles;
	}

	public void setUserRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "role")
	public Set<RolePermission> getRolePermissions() {
		return this.rolePermissions;
	}

	public void setRolePermissions(Set<RolePermission> rolePermissions) {
		this.rolePermissions = rolePermissions;
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
		return "Role [id=" + id + ", enable=" + enable + ", status=" + status + "]";
	}
}