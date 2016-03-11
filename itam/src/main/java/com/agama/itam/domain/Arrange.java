package com.agama.itam.domain;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import com.agama.authority.entity.BaseDomain;
import com.agama.common.enumbean.StatusEnum;
import com.agama.common.enumbean.WeekEnum;

/**
 * @Description:运维排程
 * @Author:佘朝军
 * @Since :2016年2月1日 下午3:16:38
 */
@Entity
@DynamicInsert
@DynamicUpdate
public class Arrange extends BaseDomain {

	private static final long serialVersionUID = 6727047382634720839L;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;
	private Integer userId;
	private String userName;
	@Column(length = 9999)
	private String workDay;
	@Transient
	private String workDayString;
	@Column(length = 9999)
	private String workTime;
	@Column(length = 1000)
	private String remark;
	private boolean enable;
	private StatusEnum status;

	public boolean checkWorkTime(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		//比较工作日
		List<String> dayList = Arrays.asList(this.workDay.split(","));
		boolean inDay = dayList.contains(String.valueOf(c.get(Calendar.DAY_OF_WEEK)));
		
		//比较作息时间
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		String time = (hour < 10 ? "0" + hour : hour) + ":" + (minute < 10 ? "0" + minute : minute);
		String[] timeArray = this.workTime.split("-");
		boolean inTime = ((time.compareTo(timeArray[0])) >= 0  && (time.compareTo(timeArray[1])) <= 0);
		
		return (inDay && inTime);
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getWorkDay() {
		return workDay;
	}

	public void setWorkDay(String workDay) {
		this.workDay = workDay;
	}

	public String getWorkTime() {
		return workTime;
	}

	public void setWorkTime(String workTime) {
		this.workTime = workTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getWorkDayString() {
		String[] strArr = this.workDay.split(",");
		String timeString = "";
		for (String string : strArr) {
			if (!StringUtils.isBlank(string)) {
				timeString += WeekEnum.getTextByValue(Integer.parseInt(string)) + ",";
			}
		}
		return timeString.substring(0, timeString.length() -1);
	}

}
