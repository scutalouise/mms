package com.agama.itam.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agama.aws.dao.MongoDao;
import com.agama.common.domain.StateEnum;
import com.agama.common.enumbean.AlarmDeviceType;
import com.agama.common.enumbean.AlarmOptionType;
import com.agama.common.enumbean.DeviceType;
import com.agama.common.enumbean.FirstDeviceType;
import com.agama.common.enumbean.StatusEnum;
import com.agama.device.dao.IAlarmConditionDao;
import com.agama.device.dao.IAlarmRuleDao;
import com.agama.device.dao.IHostDeviceDao;
import com.agama.device.dao.INetworkDeviceDao;
import com.agama.device.dao.IPeDeviceDao;
import com.agama.device.domain.AlarmCondition;
import com.agama.device.domain.AlarmRule;
import com.agama.device.domain.CollectionDevice;
import com.agama.device.domain.HostDevice;
import com.agama.device.domain.NetworkDevice;
import com.agama.device.domain.PeDevice;
import com.agama.device.service.IAlarmConditionService;
import com.agama.device.service.ICollectionDeviceService;
import com.agama.device.service.IHostDeviceService;
import com.agama.itam.mongo.dao.IDeviceStatusDao;
import com.agama.itam.mongo.domain.DeviceStatus;
import com.agama.itam.service.ICollectAlarmHandleService;
import com.agama.itam.service.IDeviceCheckService;
import com.agama.itam.service.IThStatusService;
import com.agama.itam.service.IUpsStatusService;
import com.agama.tool.utils.PingUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Service
@Transactional
public class CollectAlarmHandleServiceImpl implements
		ICollectAlarmHandleService {
	
	
	@Autowired
	private IHostDeviceDao hostDeviceDao;
	@Autowired
	private IAlarmConditionDao alarmConditionDao;
	
	@Autowired
	private IDeviceStatusDao deviceStatusDao;
	@Autowired
	private IUpsStatusService upsStatusService;
	
	@Autowired
	private IAlarmConditionService alarmConditionService;
	@Autowired
	private IHostDeviceService hostDeviceService;
	
	@Autowired
	private INetworkDeviceDao networkDeviceDao;
	@Autowired
	private IPeDeviceDao peDeviceDao;
	@Autowired
	private IThStatusService thStatusService;
	@Autowired
	private ICollectionDeviceService collectionDeviceService;
	@Autowired
	private MongoDao mongoDao;
	
	@Autowired
	private IAlarmRuleDao alarmRuleDao;
	@Resource(name="hostCheckService")
	private IDeviceCheckService hostCheckService;
	
	@Resource(name="switchBoardCheckService")
	private IDeviceCheckService switchBoardCheckService;
	@SuppressWarnings("unchecked")
	@Override
	public void alarmConditionHandle(String jsonStr) {
		JSONArray jsonArray = JSONObject.parseArray(jsonStr);
		for (Object object : jsonArray) {
			JSONObject jsonObject = JSONObject.parseObject(object.toString());
			boolean dataStatus = jsonObject.getBooleanValue("dataStatus");
			//正常数据
			if (dataStatus) {
				String identifier = jsonObject.getString("identifier");
				DeviceStatus deviceStatus=new DeviceStatus();
				deviceStatus.setCheckTime(jsonObject.getDate("checkTime"));
				deviceStatus.setIdentifier(identifier);
				deviceStatus.setDeviceType(jsonObject.getString("deviceType"));
				deviceStatus.setDataInfo(jsonObject.getObject("dataInfo", Map.class));
				mongoDao.save(deviceStatus);
				
				
				if (FirstDeviceType.HOSTDEVICE.getValue().equals(
						identifier.substring(0, 2))) {
					// 获取主机设备对象
					HostDevice hostDevice = hostDeviceDao.findUnique(Restrictions.eq("identifier",identifier));
					if (hostDevice != null) {
						AlarmOptionType alarmOptionType=AlarmOptionType.valueOf(hostDevice.getoSType().toString());
						//获取主机告警条件
						List<AlarmCondition> alarmConditions = alarmConditionDao
								.getListByTemplateId(
										hostDevice.getAlarmTemplateId(),
										AlarmDeviceType.HOSTDEVICE,alarmOptionType);
						StateEnum currentState = StateEnum.good;
						for (AlarmCondition alarmCondition : alarmConditions) {
							Map<String,Object> alarmMap=deviceAlarm(deviceStatus,alarmCondition);
							currentState = alarmConditionService.alarmConditionHandle(hostDevice.getManagerId(),identifier, currentState,
									alarmCondition, alarmMap);
						}
						
						hostDeviceService.updateCurrentStatus(identifier,currentState);
						
						
					}

				}else if (FirstDeviceType.NETWORKDEVICE.getValue().equals(
						identifier.substring(0, 2))){
					// 获取主机设备对象
					NetworkDevice networkDevice = networkDeviceDao.findUnique(Restrictions.eq("identifier",identifier));
					if(networkDevice!=null){
						//TODO 此处需要后期修改
						//获取交换机告警条件
						List<AlarmCondition> alarmConditionForSwitchBoards= alarmConditionDao
								.getListByTemplateId(
										networkDevice.getAlarmTemplateId(),
										AlarmDeviceType.SWITCHBOARD,AlarmOptionType.SWITCHBOARD_HW);
						StateEnum currentState = StateEnum.good;
						for (AlarmCondition alarmCondition : alarmConditionForSwitchBoards) {
							Map<String,Object> alarmMap=deviceAlarm(deviceStatus,alarmCondition);
							currentState = alarmConditionService.alarmConditionHandle(networkDevice.getManagerId(),identifier, currentState,
									alarmCondition, alarmMap);
						}
						
						hostDeviceService.updateCurrentStatus(identifier,currentState);
						
					}
				}
				
				
			}
		}

	}
	public Map<String, Object> deviceAlarm(DeviceStatus deviceStatus,
			AlarmCondition alarmCondition) {
		Map<String,Object> alarmResultMap=new HashMap<String, Object>();
		StateEnum runState = StateEnum.good;
		//主机设备告警规则集合
		List<AlarmRule> alarmRules=alarmRuleDao.getListByConditionIdAndAlarmOptionType(alarmCondition.getId(),alarmCondition.getAlarmOptionType());
		// 报警内容
		StringBuffer alarmContent = new StringBuffer();
		Map<String,String> alarmItemMap=new HashMap<String, String>();
		Map<String,String> alarmContentMap=new HashMap<String, String>();
		for (AlarmRule alarmRule : alarmRules) {
			Map<String,Object> deviceMap=new HashMap<String, Object>();
			IDeviceCheckService deviceCheckService=null;
			//设备为主机设备
			if(deviceStatus.getDeviceType().equals("HOSTDEVICE")){
				deviceCheckService=hostCheckService;
			}else if(deviceStatus.getDeviceType().equals("SWITCHBOARD")){
				if(alarmCondition.getAlarmOptionType()==AlarmOptionType.SWITCHBOARD_HW){
				
					deviceCheckService=switchBoardCheckService;
				}
			}
			deviceMap=deviceCheckService.checkDevice(deviceStatus, alarmRule);
			if(alarmItemMap.get(alarmRule.getAlarmRuleType().toString())!=null){
				if(StateEnum.valueOf(alarmItemMap.get(alarmRule.getAlarmRuleType().toString())).ordinal()<StateEnum.valueOf(
						deviceMap.get("runState").toString()).ordinal()){
					alarmItemMap.put(alarmRule.getAlarmRuleType().toString(), deviceMap.get("runState").toString());
					alarmContentMap.put(alarmRule.getAlarmRuleType().toString(), deviceMap.get("alarmContent").toString());
				}
			}else{
				alarmItemMap.put(alarmRule.getAlarmRuleType().toString(), deviceMap.get("runState").toString());
				alarmContentMap.put(alarmRule.getAlarmRuleType().toString(), deviceMap.get("alarmContent").toString());
			}
			if (runState.ordinal() < StateEnum.valueOf(
					deviceMap.get("runState").toString()).ordinal()) {
				runState = StateEnum.valueOf(deviceMap.get("runState")
						.toString());
			}
			
		}
		
		
		for (Map.Entry<String, String> contentMap : alarmContentMap.entrySet()) {
			alarmContent.append(contentMap.getValue());
		}
		if(alarmContent.indexOf("、")>0){
			alarmContent=new StringBuffer(alarmContent.substring(0,alarmContent.length()-1));
		}
		alarmResultMap.put("runState", runState);
		alarmResultMap.put("alarmContent", alarmContent.toString());
		return alarmResultMap;
	}
	/**
	 * @Description:动环设备数据采集定时器
	 * @Since :2016年1月20日 下午12:58:36
	 */
	@Scheduled(fixedRateString="${interval}")
	public void peDeviceCollect(){
		//获取采集设备集合
		List<CollectionDevice> collectionDevices=collectionDeviceService.getListByStatus(StatusEnum.NORMAL);
		for (CollectionDevice collectionDevice : collectionDevices) {
			dataHelper(collectionDevice);
		}
		
		
	}
	public void dataHelper(CollectionDevice collectionDevice){
		//检测IP是否可达
		boolean connect=PingUtils.pingByIp(collectionDevice.getIp());
		//ip可达
		if(connect){
			List<PeDevice> peDevices=peDeviceDao.getListByCollectionDeviceIdAndStatus(collectionDevice.getId(),StatusEnum.NORMAL);
			for (PeDevice peDevice : peDevices) {
				//UPS设备
				if(peDevice.getDhDeviceType()==DeviceType.UPS){
					upsStatusService.collectUpsStatus(collectionDevice.getIp(), peDevice.getDhDeviceIndex(),peDevice);
				}else if(peDevice.getDhDeviceType()==DeviceType.TH){
					thStatusService.collectThStatus(collectionDevice.getIp(),peDevice.getDhDeviceIndex(),peDevice);
				}
				
			}
		}
		
	}

}
