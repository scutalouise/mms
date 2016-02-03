package com.agama.device.dao;

import java.util.List;

import com.agama.common.dao.IBaseDao;
import com.agama.common.enumbean.StatusEnum;
import com.agama.device.domain.AlarmLevel;

/**
 * @Author:ranjunfeng
 * @Since :2015年9月18日 下午3:02:57
 * @Description:
 */
public interface IAlarmLevelDao extends IBaseDao<AlarmLevel, Integer> {
	/**
	 * @param ids
	 * @Since :2015年9月18日 下午3:03:12
	 * @Description:根据ID字符串将状态改为1，删除状态
	 */
	void updateStatusByIds(String ids);

	/**
	 * @Description:根据状态获取告警等级列表
	 * @param status
	 * @return
	 * @Since :2016年1月4日 上午11:42:07
	 */
	List<AlarmLevel> getListByStatus(StatusEnum status);

}
