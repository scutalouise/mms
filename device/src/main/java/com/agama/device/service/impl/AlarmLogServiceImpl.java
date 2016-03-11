package com.agama.device.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agama.authority.dao.IUserDao;
import com.agama.authority.entity.User;
import com.agama.common.domain.StateEnum;
import com.agama.common.enumbean.DeviceInterfaceType;
import com.agama.common.enumbean.FirstDeviceType;
import com.agama.common.enumbean.StatusEnum;
import com.agama.common.enumbean.UsingStateEnum;
import com.agama.common.service.impl.BaseServiceImpl;
import com.agama.device.dao.IAlarmLogDao;
import com.agama.device.dao.ICollectionDeviceDao;
import com.agama.device.dao.IDeviceInventoryDao;
import com.agama.device.dao.IHostDeviceDao;
import com.agama.device.dao.INetworkDeviceDao;
import com.agama.device.dao.IPeDeviceDao;
import com.agama.device.dao.ISendConfigDao;
import com.agama.device.domain.AlarmCondition;
import com.agama.device.domain.AlarmLog;
import com.agama.device.domain.CollectionDevice;
import com.agama.device.domain.DeviceInventory;
import com.agama.device.domain.HostDevice;
import com.agama.device.domain.NetworkDevice;
import com.agama.device.domain.PeDevice;
import com.agama.device.service.IAlarmLogService;
import com.agama.device.service.IDeviceInventoryService;
import com.agama.common.domain.SoundBean;
import com.agama.device.domain.SendConfig;
import com.agama.device.sms.SendMessage;
import com.agama.device.utils.SNMPUtil;
import com.alibaba.fastjson.JSON;
@Service
@Transactional
public class AlarmLogServiceImpl extends BaseServiceImpl<AlarmLog, Integer>
		implements IAlarmLogService {
	@Autowired
	private IAlarmLogDao alarmLogDao;
	@Autowired
	private ISendConfigDao sendConfigDao;
	@Autowired
	private IHostDeviceDao hostDeviceDao;
	@Autowired
	private IUserDao userDao;
	@Autowired
	private INetworkDeviceDao networkDeviceDao;
	@Autowired
	private IDeviceInventoryDao deviceInventoryDao;
	@Autowired
	private IPeDeviceDao peDeviceDao;
	@Autowired
	private ICollectionDeviceDao collectionDeviceDao;
	
	/**
	 * @Description:警类方式处理业务
	 * @param alarmCondition
	 * @Since :2015年10月21日 下午4:54:51
	 */

	@SuppressWarnings("unused")
	public void alarmMessage(Integer managerId,String identifier, String alarmContent,
			AlarmCondition alarmCondition) {
	
		StringBuffer content =new StringBuffer();
		String ip="";
		if (FirstDeviceType.HOSTDEVICE.getValue().equals(
				identifier.substring(0, 2))) {
			HostDevice hostDevice = hostDeviceDao.findUnique(Restrictions.eq("identifier",
							identifier));
			content.append("主机设备【").append(hostDevice.getName()).append("】出现如下异常:"+alarmContent);
			ip=hostDevice.getIp();
		}else if(FirstDeviceType.NETWORKDEVICE.getValue().equals(identifier.substring(0,2))){
			NetworkDevice networkDevice=networkDeviceDao.findUnique(Restrictions.eq("identifier",
							identifier));
			//获取设备会汇总信息
			DeviceInventory deviceInventory=deviceInventoryDao.getDeviceInventoryByPurchaseId(networkDevice.getPurchaseId());
			content.append(deviceInventory.getBrandName()+deviceInventory.getSecondDeviceType().getName()+"【"+networkDevice.getName()+"】出现如下异常:").append(alarmContent);
			ip=networkDevice.getIp();
			
		}else if(FirstDeviceType.PEDEVICE.getValue().endsWith(identifier.substring(0,2))){
			PeDevice peDevice=peDeviceDao.findByIdentifier(identifier);
			CollectionDevice collectionDevice=collectionDeviceDao.find(peDevice.getCollectDeviceId());
			content.append("动环设备【").append(peDevice.getName()).append("】出现如下异常:"+alarmContent);
			ip=collectionDevice.getIp();
		}
		
		// 声音报警 
		if (alarmCondition.getAlarmLevel().getIsSound() == UsingStateEnum.YES) {
			try {
				List<SendConfig> sendConfigs = sendConfigDao.findAll();
				SendConfig sendConfig;
				if (sendConfigs != null && sendConfigs.size() > 0) {
					sendConfig = sendConfigs.get(0);

					try {
						SNMPUtil snmpUtil=new SNMPUtil();
						long start = System.currentTimeMillis();
						snmpUtil.sendSetCommand(
								ip,
								"1.3.6.1.4.1.34651.3.1."
										+ JSON.parseObject(sendConfig.getContent(),
												SoundBean.class).getInterfaceType()
										+ ".0", 0);
						long end = System.currentTimeMillis();
						System.out.println("报警所用时间：" + (end - start) + "ms");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				SNMPUtil snmp = new SNMPUtil();
				snmp.sendSetCommand(ip, "1.3.6.1.4.1.34651.3.1.21.0", 1);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
		if(alarmCondition.getAlarmLevel().getIsSms() == UsingStateEnum.YES) {
			if(managerId!=null){
				User user = (User) userDao.find("from User where id=?0",
					managerId).get(0);
				SendMessage sendMessage = new SendMessage();

				sendMessage.doIt(user.getPhone(), content.toString());
			}
		}
	}
	
	@Override
	public void saveAlarmLog(String identifier, Map<String, Object> alarmMap) {
		AlarmLog alarmLog=new AlarmLog();
		alarmLog.setCollectTime(new Date());
		alarmLog.setCurrentState(StateEnum.valueOf(alarmMap.get("runState").toString()));
		alarmLog.setContent(alarmMap.get("alarmContent").toString());
		alarmLog.setIdentifier(identifier);
		alarmLog.setStatus(StatusEnum.NORMAL);
		alarmLogDao.save(alarmLog);
		
	}

	@Override
	public Object getAlarmNumAndTimeForYear(String beginDate, String endDate) {
		// TODO Auto-generated method stub
		return alarmLogDao.getAlarmNumAndTimeForYear(beginDate,endDate);
	}

	@Override
	public Object getAlarmNumAndTimeForMonth(String beginDate, String endDate) {
		// TODO Auto-generated method stub
		return alarmLogDao.getAlarmNumAndTimeForMonth(beginDate,endDate);
	}

	@Override
	public Object getAlarmNumAndTimeForDay(String beginDate, String endDate) {
		// TODO Auto-generated method stub
		return alarmLogDao.getAlarmNumAndTimeForDay(beginDate,endDate);
	}

	@Override
	public List<AlarmLog> getAlarmLog() {
		return alarmLogDao.getAlarmLog(); 
	}

	
	

}
