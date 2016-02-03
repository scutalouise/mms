package com.agama.pemm.service.impl;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agama.common.service.impl.BaseServiceImpl;
import com.agama.pemm.bean.DischargeType;
import com.agama.pemm.dao.IDeviceDao;
import com.agama.pemm.dao.IDischargeLogDao;
import com.agama.pemm.dao.IDischargeTaskDao;
import com.agama.pemm.dao.IGitInfoDao;
import com.agama.pemm.domain.Device;
import com.agama.pemm.domain.DischargeLog;
import com.agama.pemm.domain.DischargeTask;
import com.agama.pemm.protocol.snmp.UpsOidInfo;
import com.agama.pemm.service.IDeviceService;
import com.agama.pemm.service.IDischargeTaskService;
import com.agama.pemm.utils.SNMPUtil;
@Service
@Transactional
public class DischargeTaskServiceImpl extends
		BaseServiceImpl<DischargeTask, Integer> implements
		IDischargeTaskService {
	@Autowired
	private IDischargeTaskDao dischargeTaskDao;
	@Autowired
	private Scheduler scheduler;
	@Autowired
	private IDeviceDao deviceDao;
	@Autowired
	private IDischargeLogDao dischargeLogDao;
	@Override
	public void add(String scheduleName, String scheduleGroup, List<Integer> deviceIds) {
		for (Integer deviceId : deviceIds) {
			DischargeTask dischargeTask=new DischargeTask();
			dischargeTask.setScheduleGroup(scheduleGroup);
			dischargeTask.setScheduleName(scheduleName);
			dischargeTask.setStatus(0);
			dischargeTask.setDeviceId(deviceId);
			dischargeTaskDao.save(dischargeTask);
		}
		
		
	}
	@Override
	public void updateByDeviceIds(String deviceIds) {
		String hql="update DischargeTask set status=1 where deviceId in ("+deviceIds+")";
		dischargeTaskDao.batchExecute(hql);
		
	}
	@Override
	public void delDischargeTask(String scheduleName, String scheduleGroup) {
		JobKey key = new JobKey(scheduleName,scheduleGroup);
		try {
			
			scheduler.deleteJob(key);
			dischargeTaskDao.updateStatusByScheduleNameAndScheduleGroup(scheduleName, scheduleGroup);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		
	}
	@Override
	public List<DischargeTask> getListByDischargeTask(
			DischargeTask dischargeTask) {
		
		return dischargeTaskDao.findListByDischargeTask(dischargeTask);
	}
	@Override
	public void discharge(Integer deviceId) {
		SNMPUtil snmpUtil=new SNMPUtil();
		try {
			Device device=deviceDao.find(deviceId);
			snmpUtil.sendSetCommand(device.getGitInfo().getIp(),UpsOidInfo.dischargeTest.getOid()+"."+device.getDeviceIndex(),2);
			DischargeLog dischargeLog=new DischargeLog();
			dischargeLog.setDevice(device);
			dischargeLog.setDischargeType(DischargeType.MANUAL);
			dischargeLog.setStatus(0);
			dischargeLog.setDischargeDate(new Date());
			Subject subject = SecurityUtils.getSubject();
			dischargeLog.setUserName(subject.getPrincipal().toString());
			dischargeLogDao.save(dischargeLog);
			
		} catch (Exception e) {	
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
}
