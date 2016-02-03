package com.agama.device.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.agama.authority.dao.IUserDao;
import com.agama.authority.entity.User;
import com.agama.common.dao.utils.Page;
import com.agama.common.domain.SoundBean;
import com.agama.common.domain.StateEnum;
import com.agama.common.enumbean.FirstDeviceType;
import com.agama.common.enumbean.StatusEnum;
import com.agama.common.enumbean.UsingStateEnum;
import com.agama.common.service.impl.BaseServiceImpl;
import com.agama.device.dao.IAlarmConditionDao;
import com.agama.device.dao.IHostDeviceDao;
import com.agama.device.dao.ISendConfigDao;
import com.agama.device.domain.AlarmCondition;
import com.agama.device.domain.HostDevice;
import com.agama.device.domain.SendConfig;
import com.agama.device.service.IAlarmConditionService;
import com.agama.device.service.IAlarmLogService;
import com.agama.device.sms.SendMessage;
import com.agama.device.utils.SNMPUtil;
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
	public static Map<String, Object> alarmConditionMap = new HashMap<String, Object>();
	@Autowired
	private IAlarmConditionDao alarmConditionDao;
	@Autowired
	private IUserDao userDao;
	@Autowired
	private ISendConfigDao sendConfigDao;
	@Autowired
	private IHostDeviceDao hostDeviceDao;
	@Autowired
	private IAlarmLogService alarmLogService;
	
	@Override
	public void updateStatusByIds(String ids) {
		alarmConditionDao.updateStatusByIds(ids);
		
	}

	

	@Override
	public Page<AlarmCondition> searchByAlarmTemplateIdAndStatus(
			Page<AlarmCondition> page,Integer alarmTemplateId,StatusEnum status) {
		
		return alarmConditionDao.searchByAlarmTemplateIdAndStatus(page,alarmTemplateId,status);
	}


	@Override
	public StateEnum alarmConditionHandle(Integer managerId,String identifier,StateEnum currenState,
			AlarmCondition alarmCondition, Map<String, Object> alarmMap) {
		
		// 改变当前设备的状态
		StateEnum runState = StateEnum.valueOf(alarmMap.get("runState")
						.toString());
		if (runState != StateEnum.good) {
			// 发生异常的当前时间毫秒
			Long abnormalTime = System.currentTimeMillis();
			if (alarmConditionMap.get("abnormalTime_" + identifier + "_"+ alarmCondition.getId()) != null) {
				// 异常持续时间
				Long stayTime = abnormalTime- Long.parseLong(alarmConditionMap.get("abnormalTime_" + identifier + "_"+ alarmCondition.getId()).toString());
				//有告警持续时间的
				if (alarmCondition.getStayTime() > 0) {
					// 开始报警 
					if (stayTime > (alarmCondition.getStayTime() * 1000 * 60)) {
						// 第一次报警
						if (alarmConditionMap.get("noticeAfter_" + identifier + "_"+ alarmCondition.getId()) == null) {
							alarmConditionMap.put("noticeAfter_" + identifier + "_"+ alarmCondition.getId(), 0);
							alarmConditionMap.put("currentState_" + identifier, runState);
							alarmLogService.alarmMessage(managerId,identifier, alarmMap.get("alarmContent").toString(),alarmCondition);
	
						} else {
							//非第一报警
							if (stayTime > (alarmCondition.getIntervalTime() * 1000 * 60 * 60 * (Integer.parseInt(alarmConditionMap.get("noticeAfter_" + identifier+ "_"+ alarmCondition.getId()).toString()) + 1))) {
								//判断已告警的次数是否小于需要告警的次数
								if (Integer.parseInt(alarmConditionMap.get("noticeAfter_" + identifier + "_"+ alarmCondition.getId()).toString()) < alarmCondition.getRepeatCount()) {
									alarmLogService.alarmMessage(managerId,identifier, alarmMap.get("alarmContent").toString(),alarmCondition);
									alarmConditionMap.put("noticeAfter_"+ identifier + "_"+ alarmCondition.getId(), Integer.parseInt(alarmConditionMap.get("noticeAfter_"+ identifier+ "_"+ alarmCondition.getId()).toString()) + 1);
									
								}
							}
						}
						currenState=runState;
						alarmLogService.saveAlarmLog(identifier, alarmMap);
					}
	
				} else {
					//告警持续时间为0的（发现错误立刻报警的）
					if (alarmConditionMap.get("noticeAfter_" + identifier+ "_" + alarmCondition.getId()) == null) {
						alarmConditionMap.put("noticeAfter_" + identifier+ "_" + alarmCondition.getId(), 0);
						alarmConditionMap.put("currentState_" + identifier,runState);
						//发送告警消息
						alarmLogService.alarmMessage(managerId,identifier, alarmMap.get("alarmContent").toString(),alarmCondition);
	
					} else {
						if (stayTime > (alarmCondition.getIntervalTime() * 1000 * 60 * 60 * (Integer.parseInt(alarmConditionMap.get("noticeAfter_" + identifier + "_"+ alarmCondition.getId()).toString()) + 1))) {
							if (Integer.parseInt(alarmConditionMap.get("noticeAfter_" + identifier + "_"+ alarmCondition.getId()).toString()) < alarmCondition.getRepeatCount()) {
								//发送告警消息
								alarmLogService.alarmMessage(managerId,identifier, alarmMap.get("alarmContent").toString(),alarmCondition);
								alarmConditionMap.put("noticeAfter_" + identifier + "_"+ alarmCondition.getId(),Integer.parseInt(alarmConditionMap.get("noticeAfter_"+ identifier+ "_"+ alarmCondition.getId()).toString()) + 1);
							
							}
						}
					}
					currenState = runState;
					alarmLogService.saveAlarmLog(identifier, alarmMap);
	
				}
			} else {
				alarmConditionMap.put("currentState_" + identifier,runState);
				alarmConditionMap.put("abnormalTime_" + identifier + "_"+ alarmCondition.getId(), abnormalTime);
				currenState=runState;
				if (alarmCondition.getStayTime() <= 0){
					alarmLogService.saveAlarmLog(identifier, alarmMap);;
					// 报警方式处理
					alarmLogService.alarmMessage(managerId,identifier, alarmMap.get("alarmContent").toString(), alarmCondition);
				}
			}

		} else {
			// 异常解决后是否发短信提示
			if (alarmConditionMap.get("abnormalTime_" + identifier + "_"+ alarmCondition.getId()) != null) {
				alarmConditionMap.put("currentState_" + identifier,StateEnum.good);
				alarmDestroy(managerId,identifier, alarmCondition);
				alarmConditionMap.put("abnormalTime_" + identifier + "_"+ alarmCondition.getId(), null);

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
	private void alarmDestroy(Integer managerId,String identifier, AlarmCondition alarmCondition) {
		
		StringBuffer content =new StringBuffer();
		String ip="";
		if (FirstDeviceType.HOSTDEVICE.getValue().equals(
				identifier.substring(0, 2))) {
			HostDevice hostDevice = hostDeviceDao.findUnique(Restrictions.eq("identifier",
							identifier));
			content.append("主机设备【").append(hostDevice.getName()).append("】异常已解决");
			ip=hostDevice.getIp();
		}
		
		if (alarmCondition.getNoticeAfter() == 0) {
			User user = (User) userDao.find("from User where id=?0",
					managerId).get(0);
			SendMessage sendMessage = new SendMessage();

			sendMessage.doIt(user.getPhone(), 
					content.toString());
		}
		if (alarmCondition.getAlarmLevel().getIsSound() == UsingStateEnum.YES) {
			List<SendConfig> sendConfigs = sendConfigDao.findAll();
			SendConfig sendConfig;
			if (sendConfigs != null && sendConfigs.size() > 0) {
				sendConfig = sendConfigs.get(0);
				try {
					SNMPUtil snmpUtil=new SNMPUtil();
					snmpUtil.sendSetCommand(
							ip,"1.3.6.1.4.1.34651.3.1."
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
	 * @Description:每天24点清除报警条件的相关记录
	 * @Since :2015年10月10日 下午12:50:40
	 */
	@Scheduled(cron = "0 0 0 * * ?")
	public void cleanAlarmConditionMap() {
		alarmConditionMap.clear();
	}
}
