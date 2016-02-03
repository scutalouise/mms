package com.agama.itam.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agama.aws.dao.MongoDao;
import com.agama.common.domain.StateEnum;
import com.agama.common.enumbean.AlarmDeviceType;
import com.agama.common.enumbean.AlarmOptionType;
import com.agama.common.enumbean.AlarmRuleType;
import com.agama.common.enumbean.LinkStateEnum;
import com.agama.common.enumbean.OperationType;
import com.agama.common.enumbean.StatusEnum;
import com.agama.device.dao.IAlarmConditionDao;
import com.agama.device.dao.IAlarmRuleDao;
import com.agama.device.dao.ICollectionDeviceDao;
import com.agama.device.dao.IPeDeviceDao;
import com.agama.device.domain.AlarmCondition;
import com.agama.device.domain.AlarmRule;
import com.agama.device.domain.PeDevice;
import com.agama.device.service.IAlarmConditionService;
import com.agama.device.service.IAlarmRuleService;
import com.agama.device.utils.SNMPUtil;
import com.agama.itam.mongo.domain.ThStatus;
import com.agama.itam.protocol.snmp.ThOidInfo;
import com.agama.itam.service.IThStatusService;
import com.agama.tool.utils.ConvertUtils;
import com.agama.tool.utils.date.DateUtils;
@Service
public class ThStatusServiceImpl implements IThStatusService{
	private static final Integer MAX_INTERVAL = 20000;
	@Autowired
	private MongoDao mongoDao;
	
	@Autowired
	private IPeDeviceDao peDeviceDao;
	@Autowired
	private IAlarmConditionDao alarmConditionDao;
	@Autowired
	private IAlarmConditionService alarmConditionService;
	@Autowired
	private IAlarmRuleService alarmRuleService;
	
	@Autowired
	private ICollectionDeviceDao collectionDeviceDao;
	@Autowired
	private IAlarmRuleDao alarmRuleDao;
	@Override
	public void collectThStatus(String ip, Integer index,
			PeDevice peDevice) {
		
		SNMPUtil snmpUtil=new SNMPUtil();
		try {
			Map<String,String> resultMap=snmpUtil.walkByGetNext(ip,
					"1.3.6.1.4.1.34651.2.2.1");
			if(resultMap.size()>0){
				String lastCollectIntervalStr = resultMap
						.get(ThOidInfo.lastCollectInterval.getOid() + "."
								+ index);
				if (lastCollectIntervalStr != null) {
					//距离最后异常采集的时间间隔
					Long lastCollectInterval = Long
							.parseLong(lastCollectIntervalStr.trim());
					ThStatus thStatus=new ThStatus();
					thStatus.setIdentifier(peDevice.getIdentifier());
					if (lastCollectInterval < MAX_INTERVAL&&lastCollectInterval>=0) {
						thStatus.setName(ConvertUtils.change2Chinese(resultMap
								.get(ThOidInfo.thName.getOid() + "." + index)));
						thStatus.setModelNumber(resultMap
								.get(ThOidInfo.modelNumber.getOid() + "."
										+ index));
						thStatus.setInterfaceType(resultMap
								.get(ThOidInfo.interfaceType.getOid() + "."
										+ index));
						thStatus.setTemperature(Double.parseDouble(resultMap
								.get(ThOidInfo.temperature.getOid() + "."
										+ index)) / 10);
						thStatus.setHumidity(Double.parseDouble(resultMap
								.get(ThOidInfo.humidity.getOid() + "." + index)) / 10);
						thStatus.setCollectTime(DateUtils.parseDate(resultMap
								.get("collectTime")));
						thStatus.setStatus(StatusEnum.NORMAL);
						thStatus.setLinkState(LinkStateEnum.NORMAL);
						thStatus.setTemperatureState(StateEnum.good.toString());
						thStatus.setHumidityState(StateEnum.good.toString());
						mongoDao.save(thStatus);
						
						thAlarmCondition(thStatus);
					}else{
						thStatus.setLinkState(LinkStateEnum.LOSE);
						thStatus.setCollectTime(new Date());
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	@Override
	public void thAlarmCondition(ThStatus thStatus) {
		PeDevice peDevice=peDeviceDao.findByIdentifier(thStatus.getIdentifier());
		
		if(thStatus.getLinkState()==LinkStateEnum.NORMAL){
			List<AlarmCondition> alarmConditions=alarmConditionDao
					.getListByTemplateId(peDevice.getAlarmTemplateId(), AlarmDeviceType.PEDEVICE,AlarmOptionType.TH);
			StateEnum currentState=StateEnum.good;
			for (AlarmCondition alarmCondition : alarmConditions) {
				Map<String, Object> alarmMap = thDevieAlarm(thStatus,
						alarmCondition);
				currentState = alarmConditionService.alarmConditionHandle(peDevice.getManagerId(),peDevice.getIdentifier(), currentState,
						alarmCondition, alarmMap);
			}
			peDeviceDao.updateCurrentStateByIdentifier(thStatus.getIdentifier(),currentState);;
			
			StateEnum state=peDeviceDao.findSeverityStateByCollectId(peDevice.getCollectDeviceId());
			collectionDeviceDao.updateCurrentStateById(peDevice.getCollectDeviceId(),state);

			
		}
	}
	@Override
	public Map<String, Object> checkState(ThStatus thStatus,
			AlarmRule alarmRule,Double compareValue) {
		Map<String, Object> map = new HashMap<String, Object>();
		StateEnum runState = StateEnum.good;
		StringBuffer alarmContent = new StringBuffer();
		OperationType operationType = alarmRule.getOperationType();
		Integer value = alarmRule.getValue();
		Integer minValue = alarmRule.getMinValue();
		Integer maxValue = alarmRule.getMaxValue();
		runState = alarmRuleService.ruleCompare(operationType,
				compareValue, value,
				minValue, maxValue,alarmRule.getState());
		if (runState != StateEnum.good) {
			alarmContent.append("").append(alarmRule.getRemark()).append("、");
		}
		map.put("runState", runState);
		map.put("alarmContent", alarmContent.toString());
		return map;
	}
	@Override
	public Map<String, Object> thDevieAlarm(ThStatus thStatus,
			AlarmCondition alarmCondition) {
		Map<String, Object> alarmResultMap = new HashMap<String, Object>();
		StateEnum runState = StateEnum.good;
		StateEnum temperatureState=StateEnum.good; //温度状态
		StateEnum humidityState=StateEnum.good;   //湿度状态
		List<AlarmRule> alarmRules =  alarmRuleDao
				.getListByConditionIdAndAlarmOptionType(alarmCondition.getId(),
						AlarmOptionType.TH);
		// 报警内容
		StringBuffer alarmContent = new StringBuffer();
		for (AlarmRule alarmRule : alarmRules) {
			
				Double compareValue=null;
				if (alarmRule.getAlarmRuleType() == AlarmRuleType.TEMPERATURE) {
					compareValue=thStatus.getTemperature();
				}
				if(alarmRule.getAlarmRuleType() == AlarmRuleType.HUMIDITY){
					compareValue=thStatus.getHumidity();
				}
				Map<String, Object> map = checkState(thStatus,
						alarmRule,compareValue);
				if (runState.ordinal() < StateEnum.valueOf(
						map.get("runState").toString()).ordinal()) {
					runState = StateEnum.valueOf(map.get("runState")
							.toString());
					
				}
				if (alarmRule.getAlarmRuleType() == AlarmRuleType.TEMPERATURE) {
					if(temperatureState.ordinal()<StateEnum.valueOf(
							map.get("runState").toString()).ordinal()){
						temperatureState=StateEnum.valueOf(map.get("runState").toString());
					}
					thStatus.setTemperatureState(temperatureState.toString());
				}
				if(alarmRule.getAlarmRuleType() == AlarmRuleType.HUMIDITY){
					if(humidityState.ordinal()<StateEnum.valueOf(
							map.get("runState").toString()).ordinal()){
						humidityState=StateEnum.valueOf(map.get("runState").toString());
					}
					thStatus.setHumidityState(humidityState.toString());
				}
				
				
				alarmContent.append(map.get("alarmContent")
						.toString());
			
		}
		
		if(alarmContent.indexOf("、")>0){
			alarmContent=new StringBuffer(alarmContent.substring(0,alarmContent.length()-1));
		}
		ObjectId objectId=new ObjectId(thStatus.getId());
	
		mongoDao.updateBeanByObjectId(objectId, thStatus,ThStatus.class);
		alarmResultMap.put("runState", runState);
		alarmResultMap.put("alarmContent", alarmContent.toString());
		return alarmResultMap;
	}

}
