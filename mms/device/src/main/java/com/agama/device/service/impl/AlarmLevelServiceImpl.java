package com.agama.device.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agama.common.enumbean.StatusEnum;
import com.agama.common.service.impl.BaseServiceImpl;
import com.agama.device.dao.IAlarmLevelDao;
import com.agama.device.domain.AlarmLevel;
import com.agama.device.service.IAlarmLevelService;

/**
 * @Author:ranjunfeng
 * @Since :2015年9月18日 上午10:31:31
 * @Description:告警等级业务处理实体
 */
@Service
public class AlarmLevelServiceImpl extends BaseServiceImpl<AlarmLevel, Integer>
		implements IAlarmLevelService {
	@Autowired
	private IAlarmLevelDao alarmLevelDao;

	@Override
	public void updateStatusByIds(String ids) {
		alarmLevelDao.updateStatusByIds(ids);
		
	}

	@Override
	public List<AlarmLevel> getListByStatus(StatusEnum status) {
		// TODO Auto-generated method stub
		return alarmLevelDao.getListByStatus(status);
	}
	

	
}
