package com.agama.pemm.dao;

import com.agama.common.dao.IBaseDao;
import com.agama.pemm.domain.AlarmLevel;

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

}
