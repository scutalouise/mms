package com.agama.pemm.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.agama.authority.dao.IUserDao;
import com.agama.authority.entity.User;
import com.agama.common.dao.utils.Page;
import com.agama.common.dao.utils.PropertyFilter;
import com.agama.common.domain.StateEnum;
import com.agama.common.enumbean.AlarmType;
import com.agama.common.enumbean.DeviceInterfaceType;
import com.agama.common.enumbean.StatusEnum;
import com.agama.common.service.impl.BaseServiceImpl;
import com.agama.pemm.bean.SoundBean;
import com.agama.pemm.dao.IAlarmConditionDao;
import com.agama.pemm.dao.IAlarmLogDao;
import com.agama.pemm.dao.IDeviceDao;
import com.agama.pemm.dao.IGitInfoDao;
import com.agama.pemm.dao.ISendConfigDao;
import com.agama.pemm.dao.IUpsStatusDao;
import com.agama.pemm.domain.AlarmCondition;
import com.agama.pemm.domain.AlarmLog;
import com.agama.pemm.domain.Device;
import com.agama.pemm.domain.SendConfig;
import com.agama.pemm.domain.SwitchInputStatus;
import com.agama.pemm.domain.ThStatus;
import com.agama.pemm.domain.UpsStatus;
import com.agama.pemm.service.IAlarmConditionService;
import com.agama.pemm.service.ISwitchInputStatusService;
import com.agama.pemm.service.IThStatusService;
import com.agama.pemm.service.IUpsStatusService;
import com.agama.pemm.sms.SendMessage;
import com.agama.pemm.task.DataCollectionService;
import com.agama.pemm.utils.SNMPUtil;
import com.alibaba.fastjson.JSON;

/**
 * @Description:报警条件业务逻辑
 * @Author:ranjunfeng
 * @Since :2015年10月22日 上午9:21:51
 */
@Service
public class AlarmConditionServiceImpl extends
		BaseServiceImpl<AlarmCondition, Integer> implements
		IAlarmConditionService {
	private static Map<String, Object> alarmConditionMap = new HashMap<String, Object>();
	
	@Autowired
	private IAlarmConditionDao alarmConditionDao;
	@Autowired
	private IGitInfoDao gitInfoDao;
	@Autowired
	private IDeviceDao deviceDao;
	@Autowired
	private IAlarmLogDao alarmLogDao;
	@Autowired
	private IUpsStatusDao upsStatusDao;
	@Autowired
	private IUserDao userDao;

	@Autowired
	private IUpsStatusService upsStatusService;
	@Autowired
	private IThStatusService thStatusService;
	@Autowired
	private ISwitchInputStatusService switchInputStatusService;
	@Autowired
	private ISendConfigDao sendConfigDao;

	@Override
	public void updateStatusByIds(String ids) {
		alarmConditionDao.updateStatusByIds(ids);

	}

	
	@Transactional
	public void upsAlarmCondition(UpsStatus upsStatus) {
		StateEnum currentState = StateEnum.good;
		// 链接丢失
		if (-1 == upsStatus.getLinkState()) {
			AlarmLog alarmLog = new AlarmLog();
			alarmLog.setCollectTime(new Date());
			alarmLog.setAlarmType(StateEnum.error);
			alarmLog.setContent("UPS链接丢失");
			alarmLog.setDeviceInterfaceType(DeviceInterfaceType.UPS);
			alarmLog.setDevice(upsStatus.getDevice());
			alarmLog.setStatus(0);
			alarmLogDao.save(alarmLog);
			currentState=StateEnum.error;
			deviceDao.updateCurrentStateById(upsStatus.getDevice().getId(),
					currentState);
			if(upsStatus.getDevice().getGitInfo().getCurrentState().ordinal()<StateEnum.error.ordinal()){
				gitInfoDao.updateCurrentStateById(upsStatus.getDevice().getGitInfo().getId(),StateEnum.error);
			}
		} else {
			List<AlarmCondition> alarmConditions = alarmConditionDao
					.getListByTemplateId(upsStatus.getDevice()
							.getAlarmTemplateId(), DeviceInterfaceType.UPS);
		
			for (AlarmCondition alarmCondition : alarmConditions) {
				Map<String, Object> alarmMap = upsStatusService.checkAlarm(upsStatus, alarmCondition.getId());
				currentState = conditionHelper(upsStatus.getDevice(),currentState, alarmCondition, alarmMap);
				
			}
			/*if(upsStatus.getDevice().getGitInfo().getCurrentState().ordinal()<currentState.ordinal()){
				gitInfoDao.updateCurrentStateById(upsStatus.getDevice().getGitInfo().getId(),currentState);
			}*/
			updateDeviceCurrentStatus(upsStatus.getDevice(), currentState);
			
			StateEnum state=deviceDao.findSeverityStateByGitInfoId(upsStatus.getDevice().getGitInfo().getId());
			gitInfoDao.updateCurrentStateById(upsStatus.getDevice().getGitInfo().getId(),state);
		}
	

	}

	@Transactional
	public void thAlarmCondition(ThStatus thStatus) {
		Device device = deviceDao.find(thStatus.getDeviceId());
		// 报警条件集合
		List<AlarmCondition> alarmConditions = alarmConditionDao
				.getListByTemplateId(device.getAlarmTemplateId(),
						DeviceInterfaceType.TH);
		StateEnum currentState = StateEnum.good;
		for (AlarmCondition alarmCondition : alarmConditions) {
			Map<String, Object> alarmMap = thStatusService.checkAlarm(thStatus,
					alarmCondition.getId());

			currentState = conditionHelper(device, currentState,
					alarmCondition, alarmMap);
		}
		/*if(device.getGitInfo().getCurrentState().ordinal()<currentState.ordinal()){
			gitInfoDao.updateCurrentStateById(device.getGitInfo().getId(),currentState);
		}*/
		updateDeviceCurrentStatus(device, currentState);
		StateEnum state=deviceDao.findSeverityStateByGitInfoId(device.getGitInfo().getId());
		gitInfoDao.updateCurrentStateById(device.getGitInfo().getId(),state);
	}

	@Transactional
	public StateEnum switchInputAlarmCondition(
			SwitchInputStatus switchInputStatus) {
		Device device = deviceDao.find(switchInputStatus.getDeviceId());
		// 报警条件集合
		StateEnum currentState = StateEnum.good;
		StateEnum runState = StateEnum.good;
		List<AlarmCondition> alarmConditions = alarmConditionDao
				.getListByTemplateId(device.getAlarmTemplateId(),
						device.getDeviceInterfaceType());
		for (AlarmCondition alarmCondition : alarmConditions) {
			Map<String, Object> alarmMap = switchInputStatusService.checkAlarm(
					switchInputStatus, alarmCondition.getId(), device);
			currentState = conditionHelper(device, currentState,
					alarmCondition, alarmMap);
			runState = StateEnum.valueOf(alarmMap.get("runState").toString());
		}
		if (alarmConditionMap.get("currentState_" + device.getId()) != null
				&& device.getCurrentState() != alarmConditionMap
						.get("currentState_" + device.getId())) {
			deviceDao.updateCurrentStateById(
					device.getId(),
					StateEnum.valueOf(alarmConditionMap.get(
							"currentState_" + device.getId()).toString()));
		} else {
			if (device.getCurrentState() != currentState) {
				deviceDao.updateCurrentStateById(device.getId(), currentState);
			}
		}
		StateEnum state=deviceDao.findSeverityStateByGitInfoId(device.getGitInfo().getId());
		gitInfoDao.updateCurrentStateById(device.getGitInfo().getId(),state);
		return runState;

	}

	/**
	 * @Description:修改设备当前状态
	 * @param device
	 * @param currenState
	 * @Since :2015年10月13日 下午4:09:58
	 */
	private void updateDeviceCurrentStatus(Device device, StateEnum currentState) {
		if(currentState.ordinal()>DataCollectionService.deviceCurrentState.ordinal()){
			DataCollectionService.deviceCurrentState=currentState;
		}
		if (alarmConditionMap.get("currentState_" + device.getId()) != null
				&& device.getCurrentState() != alarmConditionMap
						.get("currentState_" + device.getId())) {
			deviceDao.updateCurrentStateById(
					device.getId(),
					StateEnum.valueOf(alarmConditionMap.get(
							"currentState_" + device.getId()).toString()));
		} else {
			if (device.getCurrentState() != currentState) {
				deviceDao.updateCurrentStateById(device.getId(), currentState);
			}
		}
	}

	/**
	 * @Description:报警条件处理业务
	 * @param device
	 * @param currenState
	 * @param alarmCondition
	 * @param alarmMap
	 * @return
	 * @Since :2015年10月13日 下午4:10:48
	 */
	private StateEnum conditionHelper(Device device, StateEnum currenState,
			AlarmCondition alarmCondition, Map<String, Object> alarmMap) {
		// 改变当前设备的状态
		StateEnum runState = StateEnum.valueOf(alarmMap.get("runState")
				.toString());
		if (runState != StateEnum.good) {
			// 发生异常的当前时间毫秒
			Long abnormalTime = System.currentTimeMillis();
			if (alarmConditionMap.get("abnormalTime_" + device.getId() + "_"
					+ alarmCondition.getId()) != null) {
				// 异常持续时间
				Long stayTime = abnormalTime
						- Long.parseLong(alarmConditionMap.get(
								"abnormalTime_" + device.getId() + "_"
										+ alarmCondition.getId()).toString());

				if (alarmCondition.getStayTime() > 0) {
					// 判断发生异常的当前时间是否为空

					// 开始报警
					if (stayTime > (alarmCondition.getStayTime() * 1000 * 60)) {
						// 第一次报警
						if (alarmConditionMap
								.get("noticeAfter_" + device.getId() + "_"
										+ alarmCondition.getId()) == null) {
							alarmConditionMap.put(
									"noticeAfter_" + device.getId() + "_"
											+ alarmCondition.getId(), 0);
							alarmConditionMap.put(
									"currentState_" + device.getId(), runState);
							currenState = runState;
							saveAlarmLog(device, alarmMap, alarmCondition);
							// 报警方式处理
							alarmMessage(device, alarmMap.get("alarmContent").toString(), alarmCondition);

						} else {
							if (stayTime > (alarmCondition.getIntervalTime() * 1000 * 60 * 60 * (Integer
									.parseInt(alarmConditionMap.get(
											"noticeAfter_" + device.getId()
													+ "_"
													+ alarmCondition.getId())
											.toString()) + 1))) {
								if (Integer.parseInt(alarmConditionMap.get(
										"noticeAfter_" + device.getId() + "_"
												+ alarmCondition.getId())
										.toString()) < alarmCondition
										.getRepeatCount()) {
									saveAlarmLog(device, alarmMap,
											alarmCondition);
									// 报警方式处理
									alarmMessage(device, alarmMap.get("alarmContent").toString(), alarmCondition);
									//报警次数+1
									alarmConditionMap.put("noticeAfter_"
											+ device.getId() + "_"+ alarmCondition.getId(), Integer
											.parseInt(alarmConditionMap.get(
													"noticeAfter_"+ device.getId()
															+"_"+ alarmCondition
																	.getId())
													.toString()) + 1);
								}
							}else{
								currenState=runState;
								saveAlarmLog(device, alarmMap, alarmCondition);
							}
						}

					}

				} else {
					//立刻报警
					//第一次报警
					if (alarmConditionMap.get("noticeAfter_" + device.getId()
							+ "_" + alarmCondition.getId()) == null) {

						alarmConditionMap.put("noticeAfter_" + device.getId()
								+ "_" + alarmCondition.getId(), 0);
						alarmConditionMap.put("currentState_" + device.getId(),
								runState);
						currenState = runState;
						saveAlarmLog(device, alarmMap, alarmCondition);
						// 报警方式处理
						alarmMessage(device, alarmMap.get("alarmContent").toString(), alarmCondition);

					} else {
						if (stayTime > (alarmCondition.getIntervalTime() * 1000 * 60 * 60 * (Integer
								.parseInt(alarmConditionMap.get(
										"noticeAfter_" + device.getId() + "_"
												+ alarmCondition.getId())
										.toString()) + 1))) {
							if (Integer.parseInt(alarmConditionMap.get(
									"noticeAfter_" + device.getId() + "_"
											+ alarmCondition.getId())
									.toString()) < alarmCondition
									.getRepeatCount()) {
								saveAlarmLog(device, alarmMap, alarmCondition);
								// 报警方式处理
								alarmMessage(device, alarmMap.get("alarmContent").toString(), alarmCondition);
								alarmConditionMap.put(
										"noticeAfter_" + device.getId() + "_"
												+ alarmCondition.getId(),
										Integer.parseInt(alarmConditionMap.get(
												"noticeAfter_"
														+ device.getId()
														+ "_"
														+ alarmCondition
																.getId())
												.toString()) + 1);
							}
						}else {
							currenState=runState;
							saveAlarmLog(device, alarmMap, alarmCondition);
						}
					}

				}
			} else {
				alarmConditionMap.put("currentState_" + device.getId(),
						runState);
				alarmConditionMap.put("abnormalTime_" + device.getId() + "_"
						+ alarmCondition.getId(), abnormalTime);
				currenState=runState;
				if (alarmCondition.getStayTime() <= 0){
					saveAlarmLog(device, alarmMap, alarmCondition);
					// 报警方式处理
					alarmMessage(device, alarmMap.get("alarmContent").toString(), alarmCondition);
				}

			}

		} else {
			// 异常解决后是否发短信提示
			if (alarmConditionMap.get("abnormalTime_" + device.getId() + "_"
					+ alarmCondition.getId()) != null) {
				
				alarmDestroy(device, alarmCondition);
				

				alarmConditionMap.clear();
			}
		}
		return currenState;
	}

	/**
	 * @Description:处理完报警信息后根据报警条件判断是否需要发消息提示，并且停止报警器报警
	 * @param device
	 * @param alarmCondition
	 * @Since :2015年10月22日 上午9:22:26
	 */
	private void alarmDestroy(Device device, AlarmCondition alarmCondition) {
		if (alarmCondition.getNoticeAfter() == 0) {
			User user = (User) userDao.find("from User where id=?0",
					device.getManagerId()).get(0);
			SendMessage sendMessage = new SendMessage();
			StringBuffer sb=new StringBuffer();
			sb.append("网点:【"+device.getGitInfo().getOrganizationName()).append("】");
			sb.append(device.getDeviceInterfaceType().getName());
			sb.append("问题已解决");
			
			sendMessage.doIt(user.getPhone(), sb.toString());
		}
		if (alarmCondition.getAlarmLevel().getIsSound() == 0) {
			List<SendConfig> sendConfigs = sendConfigDao.findAll();
			SendConfig sendConfig;
			if (sendConfigs != null && sendConfigs.size() > 0) {
				sendConfig = sendConfigs.get(0);

				try {
					SNMPUtil snmpUtil=new SNMPUtil();
					snmpUtil.sendSetCommand(
							device.getGitInfo().getIp(),
							"1.3.6.1.4.1.34651.3.1."
									+ JSON.parseObject(sendConfig.getContent(),
											SoundBean.class).getInterfaceType()
									+ ".0", 0);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * @Description:警类方式处理业务
	 * @param alarmCondition
	 * @Since :2015年10月21日 下午4:54:51
	 */

	private void alarmMessage(Device device, String alarmContent,
			AlarmCondition alarmCondition) {
		// 声音报警
		if (alarmCondition.getAlarmLevel().getIsSound() == 0) {
			try {
				List<SendConfig> sendConfigs = sendConfigDao.findAll();
				SendConfig sendConfig;
				if (sendConfigs != null && sendConfigs.size() > 0) {
					sendConfig = sendConfigs.get(0);

					try {
						SNMPUtil snmpUtil=new SNMPUtil();
						long start = System.currentTimeMillis();
						snmpUtil.sendSetCommand(
								device.getGitInfo().getIp(),
								"1.3.6.1.4.1.34651.3.1."
										+ JSON.parseObject(sendConfig.getContent(),
												SoundBean.class).getInterfaceType()
										+ ".0", 1);
						long end = System.currentTimeMillis();
						System.out.println("报警所用时间：" + (end - start) + "ms");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} 
		if(alarmCondition.getAlarmLevel().getIsSms() == 0) {
			if(device.getManagerId()!=0){
			User user = (User) userDao.find("from User where id=?0",
					device.getManagerId()).get(0);
			SendMessage sendMessage = new SendMessage();

			StringBuffer sb=new StringBuffer();
			sb.append("网点:【"+device.getGitInfo().getOrganizationName()).append("】");
			sb.append(device.getDeviceInterfaceType().getName());
			sb.append("设备出现如下问题:");
			
			sb.append(alarmContent);
			
			sendMessage.doIt(user.getPhone(),sb.toString());
			}
		}
	}

	// 保存报警日志
	@SuppressWarnings("unchecked")
	private void saveAlarmLog(Device device, Map<String, Object> alarmMap,
			AlarmCondition alarmCondition) {
		if(alarmMap.get("alarmMapList")!=null){
			List<Map<String,Object>> alarmMapList=(List<Map<String, Object>>) alarmMap.get("alarmMapList");
			for (Map<String, Object> map : alarmMapList) {
				String alarmContentStr=map.get("alarmContent").toString();
				AlarmLog alarmLog = new AlarmLog();
				alarmLog.setCollectTime(new Date());
				alarmLog.setAlarmType(StateEnum.valueOf(map.get("runState").toString()));
				alarmLog.setContent(alarmContentStr.indexOf("、")>0?alarmContentStr.substring(0,alarmContentStr.length()-1):alarmContentStr);
				alarmLog.setDeviceInterfaceType(device.getDeviceInterfaceType());
				alarmLog.setDevice(device);
				alarmLog.setStatus(0);
				alarmLogDao.save(alarmLog);
			}
		
		}else{
			AlarmLog alarmLog = new AlarmLog();
			alarmLog.setCollectTime(new Date());
			alarmLog.setAlarmType(StateEnum.valueOf(alarmMap.get("runState").toString()));
			alarmLog.setContent(alarmMap.get("alarmContent").toString());
			alarmLog.setDeviceInterfaceType(device.getDeviceInterfaceType());
			alarmLog.setDevice(device);
			alarmLog.setStatus(0);
			alarmLogDao.save(alarmLog);
		}
		
		// 报警方式处理
		//alarmMessage(device, alarmLog.getContent(), alarmCondition);
	}

	/**
	 * @Description:每天24点清除报警条件的相关记录
	 * @Since :2015年10月10日 下午12:50:40
	 */
	@Scheduled(cron = "0 0 0 * * ?")
	public void cleanAlarmConditionMap() {
		alarmConditionMap.clear();
	}


	

}
