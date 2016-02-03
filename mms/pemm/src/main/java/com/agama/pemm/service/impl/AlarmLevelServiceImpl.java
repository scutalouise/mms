package com.agama.pemm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agama.common.service.impl.BaseServiceImpl;
import com.agama.pemm.dao.IAlarmLevelDao;
import com.agama.pemm.domain.AlarmLevel;
import com.agama.pemm.service.IAlarmLevelService;

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
	public List<AlarmLevel> getListByStatus(Integer status) {
		StringBuffer hql=new StringBuffer("from AlarmLevel where 1=1");
		if(status!=null){
			hql.append(" and status=").append(status);
		}
		return alarmLevelDao.find(hql.toString());
	}

	
}
