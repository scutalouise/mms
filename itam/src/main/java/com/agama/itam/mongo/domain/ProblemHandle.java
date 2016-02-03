package com.agama.itam.mongo.domain;

import java.util.Date;

import org.bson.types.ObjectId;

import com.agama.common.enumbean.ProblemStatusEnum;

/**
 * @Description:问题处理过程记录实体
 * @Author:佘朝军
 * @Since :2016年1月27日 下午3:57:43
 */
public class ProblemHandle {

	private ObjectId id;
	private Date handleTime;
	private Integer handleUserId;
	private String handleUserName;
	private ProblemStatusEnum enable;
	private String description;
	private String attachment; // 附件地址
	private Long problemId;

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public Date getHandleTime() {
		return handleTime;
	}

	public void setHandleTime(Date handleTime) {
		this.handleTime = handleTime;
	}

	public Integer getHandleUserId() {
		return handleUserId;
	}

	public void setHandleUserId(Integer handleUserId) {
		this.handleUserId = handleUserId;
	}

	public String getHandleUserName() {
		return handleUserName;
	}

	public void setHandleUserName(String handleUserName) {
		this.handleUserName = handleUserName;
	}

	public ProblemStatusEnum getEnable() {
		return enable;
	}

	public void setEnable(ProblemStatusEnum enable) {
		this.enable = enable;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public Long getProblemId() {
		return problemId;
	}

	public void setProblemId(Long problemId) {
		this.problemId = problemId;
	}

}
