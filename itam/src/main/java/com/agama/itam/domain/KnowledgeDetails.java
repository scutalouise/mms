package com.agama.itam.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.agama.common.enumbean.EnabledStateEnum;
import com.agama.common.enumbean.StatusEnum;

@Entity
@DynamicInsert
@DynamicUpdate
public class KnowledgeDetails implements Serializable {

	private static final long serialVersionUID = -4014798785976633618L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable = false, nullable = false)
	private Integer id;
	private String title;
	private String keyword;
	@Column(length = 1024)
	private String content;
	private String attachmentAddress;
	private Integer downloadCount;
	private Integer classificationId;
	private Integer updateUserId;
	private Date updateTime;
	private Integer commitUserId;
	private Date commitTime;
	private Long problemId;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private StatusEnum status;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private EnabledStateEnum enable;

	public KnowledgeDetails() {
		super();
		this.enable = EnabledStateEnum.ENABLED;
		this.status = StatusEnum.NORMAL;
	}

	public KnowledgeDetails(Integer id,String title, String keyword, String content, String attachmentAddress, Integer downloadCount, Integer classificationId, Integer updateUserId, Date updateTime,
			Integer commitUserId, Date commitTime, Long problemId, StatusEnum status, EnabledStateEnum enable) {
		super();
		this.id = id;
		this.title = title;
		this.keyword = keyword;
		this.content = content;
		this.attachmentAddress = attachmentAddress;
		this.downloadCount = downloadCount;
		this.classificationId = classificationId;
		this.updateUserId = updateUserId;
		this.updateTime = updateTime;
		this.commitUserId = commitUserId;
		this.commitTime = commitTime;
		this.problemId = problemId;
		this.status = status;
		this.enable = enable;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAttachmentAddress() {
		return attachmentAddress;
	}

	public void setAttachmentAddress(String attachmentAddress) {
		this.attachmentAddress = attachmentAddress;
	}

	public Integer getDownloadCount() {
		return downloadCount;
	}

	public void setDownloadCount(Integer downloadCount) {
		this.downloadCount = downloadCount;
	}

	public Integer getClassificationId() {
		return classificationId;
	}

	public void setClassificationId(Integer classificationId) {
		this.classificationId = classificationId;
	}

	public Integer getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(Integer updateUserId) {
		this.updateUserId = updateUserId;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getCommitUserId() {
		return commitUserId;
	}

	public void setCommitUserId(Integer commitUserId) {
		this.commitUserId = commitUserId;
	}

	public Date getCommitTime() {
		return commitTime;
	}

	public void setCommitTime(Date commitTime) {
		this.commitTime = commitTime;
	}

	public Long getProblemId() {
		return problemId;
	}

	public void setProblemId(Long problemId) {
		this.problemId = problemId;
	}

	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}

	public EnabledStateEnum getEnable() {
		return enable;
	}

	public void setEnable(EnabledStateEnum enable) {
		this.enable = enable;
	}

}
