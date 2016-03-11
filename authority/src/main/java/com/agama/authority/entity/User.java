package com.agama.authority.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;
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
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.agama.common.enumbean.EnabledStateEnum;
import com.agama.common.enumbean.InternalEnum;
import com.agama.common.enumbean.StatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 用户entity
 * 
 * @author ty
 * @date 2015年1月13日
 */
@Entity
@Table(name = "user")
@DynamicUpdate
@DynamicInsert
public class User implements java.io.Serializable {
	// Fields
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String loginName;
	private String name;
	private String password;
	private String plainPassword;
	private String salt;
	private Timestamp birthday;
	private String genderString;
	private Short gender;
	private String email;
	private String phone;
	private String icon;
	private Timestamp createDate;
	private String description;
	private Integer loginCount;
	private Timestamp previousVisit;
	private Timestamp lastVisit;
	private InternalEnum belong;//2016年3月1日新增是否内部字段，判断员工是否属于内部人员；
	//2016年2月2日，将删除与启用2个字段与目前已有的项目统一处理，命名；
	private EnabledStateEnum enable;	// 是否启用
	private StatusEnum status;			// 是否删除
	@JsonIgnore
	private Set<UserRole> userRoles = new HashSet<UserRole>(0);

	// Constructors

	/** default constructor */
	public User() {
		this.enable = EnabledStateEnum.ENABLED;
		this.status = StatusEnum.NORMAL;
	}

	public User(Integer id) {
		this.id = id;
	}

	/** minimal constructor */
	public User(String loginName, String name, String password) {
		this.loginName = loginName;
		this.name = name;
		this.password = password;
	}
	
	/** full constructor */
	public User(Integer id, String loginName, String name, String password, String plainPassword, String salt, Timestamp birthday, Short gender, String email, String phone,
			String icon, Timestamp createDate, String description, Integer loginCount, Timestamp previousVisit, Timestamp lastVisit, InternalEnum belong, EnabledStateEnum enable, StatusEnum status) {
		this.id = id;
		this.loginName = loginName;
		this.name = name;
		this.password = password;
		this.plainPassword = plainPassword;
		this.salt = salt;
		this.birthday = birthday;
		this.gender = gender;
		this.email = email;
		this.phone = phone;
		this.icon = icon;
		this.createDate = createDate;
		this.description = description;
		this.loginCount = loginCount;
		this.previousVisit = previousVisit;
		this.lastVisit = lastVisit;
		this.belong = belong;
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

	@Column(name = "LOGIN_NAME", nullable = false, length = 20)
	public String getLoginName() {
		return this.loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	@Column(name = "NAME", nullable = false, length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "PASSWORD", nullable = false)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "SALT")
	public String getSalt() {
		return this.salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	@Column(name = "BIRTHDAY", length = 19)
	public Timestamp getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Timestamp birthday) {
		this.birthday = birthday;
	}

	@Transient
	public String getGenderString() {
		return genderString;
	}

	public void setGenderString(String genderString) {
		this.genderString = genderString;
		if(gender == 1){
			this.genderString = "男";
		}else if(gender == 0){
			this.genderString = "女";
		}
	}

	@Column(name = "GENDER")
	public Short getGender() {
		return this.gender;
	}

	public void setGender(Short gender) {
		if(gender == 1){
			genderString = "男";
		}else if( gender == 0){
			genderString = "女";
		}
		this.gender = gender;
	}

	@Column(name = "EMAIL", length = 50)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "PHONE", length = 20)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "ICON", length = 500)
	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Column(name = "CREATE_DATE", length = 19)
	public Timestamp getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	/*
	 * @Column(name = "STATE", length = 1) public String getState() { return
	 * this.state; }
	 * 
	 * public void setState(String state) { this.state = state; }
	 */

	@Column(name = "DESCRIPTION")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "LOGIN_COUNT")
	public Integer getLoginCount() {
		return this.loginCount;
	}

	public void setLoginCount(Integer loginCount) {
		this.loginCount = loginCount;
	}

	@Column(name = "PREVIOUS_VISIT", length = 19)
	public Timestamp getPreviousVisit() {
		return this.previousVisit;
	}

	public void setPreviousVisit(Timestamp previousVisit) {
		this.previousVisit = previousVisit;
	}

	@Column(name = "LAST_VISIT", length = 19)
	public Timestamp getLastVisit() {
		return this.lastVisit;
	}

	public void setLastVisit(Timestamp lastVisit) {
		this.lastVisit = lastVisit;
	}

	/*
	 * @Column(name = "DEL_FLAG", length = 1) public String getDelFlag() {
	 * return this.delFlag; }
	 * 
	 * public void setDelFlag(String delFlag) { this.delFlag = delFlag; }
	 */

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
	public Set<UserRole> getUserRoles() {
		return this.userRoles;
	}

	public void setUserRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	// 不持久化到数据库，也不显示在Restful接口的属性.
	@Transient
	@JsonIgnore
	public String getPlainPassword() {
		return plainPassword;
	}

	public void setPlainPassword(String plainPassword) {
		this.plainPassword = plainPassword;
	}

	@Column(name = "BELONG")
	@Enumerated(EnumType.STRING)
	public InternalEnum getBelong() {
		return belong;
	}

	public void setBelong(InternalEnum belong) {
		this.belong = belong;
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
		return "User [id=" + id + ", enable=" + enable + ", status=" + status + "]";
	}
}