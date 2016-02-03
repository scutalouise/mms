package com.agama.pemm.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agama.common.domain.StateEnum;
import com.agama.common.enumbean.DeviceInterfaceType;
import com.agama.pemm.dao.IAlarmConditionDao;
import com.agama.pemm.dao.IAlarmLogDao;
import com.agama.pemm.dao.IDeviceDao;
import com.agama.pemm.domain.AlarmCondition;
import com.agama.pemm.domain.AlarmLog;
import com.agama.pemm.domain.UpsStatus;
import com.agama.pemm.service.IAlarmHandleService;
import com.agama.pemm.service.IUpsStatusService;

/**
 * @Description:告警处理业务逻辑实体类
 * @Author:ranjunfeng
 * @Since :2016年1月6日 上午10:40:35
 */
@Service
public class AlarmHandleServiceImpl implements IAlarmHandleService {
	@Autowired
	private IAlarmLogDao alarmLogDao;
	@Autowired
	private IDeviceDao deviceDao;
	@Autowired
	private IAlarmConditionDao alarmConditionDao;
	
	@Autowired
	private IUpsStatusService upsStatusService;
	
	@Transactional
	public void upsAlarmCondition(UpsStatus upsStatus) {
		if(-1==upsStatus.getLinkState()){
			AlarmLog alarmLog = new AlarmLog();
			alarmLog.setCollectTime(new Date());
			alarmLog.setAlarmType(StateEnum.error);
			alarmLog.setContent("UPS链接丢失");
			alarmLog.setDeviceInterfaceType(DeviceInterfaceType.UPS);
			alarmLog.setDevice(upsStatus.getDevice());
			alarmLog.setStatus(0);
			alarmLogDao.save(alarmLog);
			deviceDao.updateCurrentStateById(upsStatus.getDevice().getId(),
					StateEnum.error);
		} else {
			//TODO
		}

	}

}
