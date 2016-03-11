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

/**
 * 权限 entity
 * 
 * @author ty
 * @date 2015年1月13日
 */
@Entity
@Table(name = "permission")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class Permission implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer pid;
	private String name;
	private String type;
	private Integer sort;
	private String url;
	private String icon;
	private String permCode;
	private String description;
	private String code;
	@JsonIgnore
	private Set<RolePermission> rolePermissions = new HashSet<RolePermission>(0);
	//2016年2月2日，将删除与启用2个字段与目前已有的项目统一处理，命名；
	private EnabledStateEnum enable;	// 是否启用
	private StatusEnum status;			// 是否删除

	// Constructors

	/** default constructor */
	public Permission() {
		this.enable = EnabledStateEnum.ENABLED;
		this.status = StatusEnum.NORMAL;
	}

	/** minimal constructor */
	public Permission(String name) {
		this.name = name;
	}

	public Permission(Integer id) {
		this.id = id;
	}

	public Permission(Integer id, Integer pid, String name) {
		this.id = id;
		this.pid = pid;
		this.name = name;
	}

	public Permission(Integer pid, String name, String type, String url, String permCode) {
		this.pid = pid;
		this.name = name;
		this.type = type;
		this.url = url;
		this.permCode = permCode;
	}

	/** full constructor */
	public Permission(Integer pid, String name, String type, Integer sort, String url, String icon, String permCode, String description, String state, Set<RolePermission> rolePermissions,
			EnabledStateEnum enable, StatusEnum status) {
		this.pid = pid;
		this.name = name;
		this.type = type;
		this.sort = sort;
		this.url = url;
		this.icon = icon;
		this.permCode = permCode;
		this.description = description;
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

	@Column(name = "PID")
	public Integer getPid() {
		return this.pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	@Column(name = "NAME", nullable = false, length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "TYPE", length = 20)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "SORT")
	public Integer getSort() {
		return this.sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	@Column(name = "URL")
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "ICON")
	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Column(name = "PERM_CODE", length = 50)
	public String getPermCode() {
		return this.permCode;
	}

	public void setPermCode(String permCode) {
		this.permCode = permCode;
	}

	@Column(name = "DESCRIPTION", length = 65535)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "permission")
	public Set<RolePermission> getRolePermissions() {
		return this.rolePermissions;
	}

	public void setRolePermissions(Set<RolePermission> rolePermissions) {
		this.rolePermissions = rolePermissions;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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
		return "Permission [id=" + id + ", pid=" + pid + ", enable=" + enable + ", status=" + status + "]";
	}
}