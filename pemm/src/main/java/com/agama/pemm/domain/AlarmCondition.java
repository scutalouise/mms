package com.agama.pemm.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.agama.pemm.bean.DeviceType;

/**
 * @Author:ranjunfeng
 * @Since :2015年9月21日 上午11:35:33
 * @Description:告警条件实体
 */
@Entity
public class AlarmCondition extends BaseDomain{

	/**
	 * long
	 */
	private static final long serialVersionUID = 9108222651131531502L;
	private Integer stayTime;// 持续时间
	private Integer repeatCount;// 重复报警次数
	private Integer intervalTime;// 重复报警的间隔时间
	private Integer noticeAfter; // 报警消失后是否通知
	private DeviceType deviceType;//动环设备类型
	private Integer status; // 删除状态(0:正常,1:删除)
	private Integer enabled;
	private Integer alarmTemplateId;
	private String alarmTemplateName;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "alarmLevelId")
	private AlarmLevel alarmLevel;
	public Integer getStayTime() {
		return stayTime;
	}

	public void setStayTime(Integer stayTime) {
		this.stayTime = stayTime;
	}

	public Integer getRepeatCount() {
		return repeatCount;
	}

	public void setRepeatCount(Integer repeatCount) {
		this.repeatCount = repeatCount;
	}

	public Integer getIntervalTime() {
		return intervalTime;
	}

	public void setIntervalTime(Integer intervalTime) {
		this.intervalTime = intervalTime;
	}

	public Integer getNoticeAfter() {
		return noticeAfter;
	}

	public void setNoticeAfter(Integer noticeAfter) {
		this.noticeAfter = noticeAfter;
	}

	
	
	public DeviceType getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(DeviceType deviceType) {
		this.deviceType = deviceType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	

	public Integer getAlarmTemplateId() {
		return alarmTemplateId;
	}

	public void setAlarmTemplateId(Integer alarmTemplateId) {
		this.alarmTemplateId = alarmTemplateId;
	}

	public String getAlarmTemplateName() {
		return alarmTemplateName;
	}

	public void setAlarmTemplateName(String alarmTemplateName) {
		this.alarmTemplateName = alarmTemplateName;
	}

	public AlarmLevel getAlarmLevel() {
		return alarmLevel;
	}

	public void setAlarmLevel(AlarmLevel alarmLevel) {
		this.alarmLevel = alarmLevel;
	}

	public Integer getEnabled() {
		return enabled;
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}

}
