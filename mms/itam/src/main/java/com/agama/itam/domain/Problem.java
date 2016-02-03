package com.agama.itam.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import com.agama.common.enumbean.ProblemStatusEnum;
import com.agama.common.enumbean.ReportWayEnum;
import com.agama.common.enumbean.StatusEnum;

/**
 * @Description:问题记录实体
 * @Author:佘朝军
 * @Since :2016年1月18日 下午5:58:18
 */
@Entity
@DynamicInsert
@DynamicUpdate
public class Problem {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date recordTime;
	private String reportUser; // 问题上报人，可能为非系统用户
	private String reportUserContact;
	private Integer recordUserId;
	@Transient
	private String recordUserName;
	private Integer resolveUserId;
	@Transient
	private String resolveUserName;
	private Integer problemTypeId;
	@Transient
	private String problemType;
	private String identifier; // 设备唯一编号
	@Column(length = 2000)
	private String description;
	private boolean enableKnowledge; // 是否加入知识库
	private ReportWayEnum reportWay; // （1.电话 2.口头报备 3. 知识库新增 4.手持机)
	private ProblemStatusEnum enable; // （0：新问题，1:已分配，2：已响应，3：处理中，4，已解决，5：已关闭）
	private StatusEnum status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}

	public String getReportUser() {
		return reportUser;
	}

	public void setReportUser(String reportUser) {
		this.reportUser = reportUser;
	}

	public String getReportUserContact() {
		return reportUserContact;
	}

	public void setReportUserContact(String reportUserContact) {
		this.reportUserContact = reportUserContact;
	}

	public Integer getRecordUserId() {
		return recordUserId;
	}

	public void setRecordUserId(Integer recordUserId) {
		this.recordUserId = recordUserId;
	}

	public Integer getResolveUserId() {
		return resolveUserId;
	}

	public void setResolveUserId(Integer resolveUserId) {
		this.resolveUserId = resolveUserId;
	}

	public Integer getProblemTypeId() {
		return problemTypeId;
	}

	public void setProblemTypeId(Integer problemTypeId) {
		this.problemTypeId = problemTypeId;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isEnableKnowledge() {
		return enableKnowledge;
	}

	public void setEnableKnowledge(boolean enableKnowledge) {
		this.enableKnowledge = enableKnowledge;
	}

	public ReportWayEnum getReportWay() {
		return reportWay;
	}

	public void setReportWay(ReportWayEnum reportWay) {
		this.reportWay = reportWay;
	}

	public ProblemStatusEnum getEnable() {
		return enable;
	}

	public void setEnable(ProblemStatusEnum enable) {
		this.enable = enable;
	}

	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}

	public String getRecordUserName() {
		return recordUserName;
	}

	public void setRecordUserName(String recordUserName) {
		this.recordUserName = recordUserName;
	}

	public String getResolveUserName() {
		return resolveUserName;
	}

	public void setResolveUserName(String resolveUserName) {
		this.resolveUserName = resolveUserName;
	}

	public String getProblemType() {
		return problemType;
	}

	public void setProblemType(String problemType) {
		this.problemType = problemType;
	}

}
