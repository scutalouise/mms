package com.agama.itam.service.impl;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import com.agama.aws.dao.MongoDao;
import com.agama.common.domain.StateEnum;
import com.agama.common.enumbean.AlarmDeviceType;
import com.agama.common.enumbean.AlarmOptionType;
import com.agama.common.enumbean.AlarmRuleType;
import com.agama.common.enumbean.OperationType;
import com.agama.common.enumbean.StatusEnum;
import com.agama.device.dao.IAlarmConditionDao;
import com.agama.device.dao.IAlarmLogDao;
import com.agama.device.dao.IAlarmRuleDao;
import com.agama.device.dao.ICollectionDeviceDao;
import com.agama.device.dao.IPeDeviceDao;
import com.agama.device.domain.AlarmCondition;
import com.agama.device.domain.AlarmLog;
import com.agama.device.domain.AlarmRule;
import com.agama.device.domain.PeDevice;
import com.agama.device.service.IAlarmConditionService;
import com.agama.device.service.IAlarmRuleService;
import com.agama.device.utils.SNMPUtil;
import com.agama.itam.dao.IDischargeStatisticDao;
import com.agama.itam.domain.DischargeStatistic;
import com.agama.itam.mongo.dao.IUpsStatusDao;
import com.agama.itam.mongo.domain.UpsStatus;
import com.agama.itam.protocol.snmp.UpsOidInfo;
import com.agama.itam.service.IUpsStatusService;
import com.agama.tool.utils.ConverToBitUtils;
import com.agama.tool.utils.date.DateUtils;
@Service
public class UpsStatusServiceImpl implements IUpsStatusService {
	private static final Integer MAX_INTERVAL = 20000;
	@Autowired
	private IUpsStatusDao upsStatusDao;
	@Autowired
	private IDischargeStatisticDao dischargeStatisticDao;
	@Autowired
	private IAlarmConditionService alarmConditionService;
	@Autowired
	private IAlarmRuleService alarmRuleService;
	@Autowired
	private IPeDeviceDao peDeviceDao;
	@Autowired
	private IAlarmLogDao alarmLogDao;
	@Autowired
	private IAlarmConditionDao alarmConditionDao;
	
	@Autowired
	private ICollectionDeviceDao collectionDeviceDao;
	
	@Autowired
	private MongoDao mongoDao;
	
	@Autowired
	
	private IAlarmRuleDao alarmRuleDao;
	
	@Override
	public UpsStatus findNewData(Integer peDeviceId) {
		List<UpsStatus> upsStatusList=mongoDao.queryListByCriteriaAndSortForPage(new Criteria().is(peDeviceId).andOperator(new Criteria().is(StatusEnum.NORMAL)),new Sort(Direction.DESC, "collectTime"), UpsStatus.class, 1, 1);
		if(upsStatusList.size()>0){
			return upsStatusList.get(0);
		}
		return null;
	}
	
	public void upsAlarmCondition(UpsStatus upsStatus){
		PeDevice peDevice=peDeviceDao.findByIdentifier(upsStatus.getIdentifier());
		// 链接丢失
		if (-1 == upsStatus.getLinkState()) {
			AlarmLog alarmLog = new AlarmLog();
			alarmLog.setCollectTime(new Date());
			alarmLog.setCurrentState(StateEnum.error);
			alarmLog.setContent("UPS链接丢失");
			alarmLog.setAlarmOptionType(AlarmOptionType.UPS);
			alarmLog.setIdentifier(upsStatus.getIdentifier());
			alarmLog.setStatus(StatusEnum.NORMAL);
			alarmLogDao.save(alarmLog);
			
			peDeviceDao.updateCurrentStateByIdentifier(upsStatus.getIdentifier(),StateEnum.error);
			collectionDeviceDao.updateCurrentStateById(peDevice.getCollectDeviceId(),StateEnum.error);
			
		} else {
			List<AlarmCondition> alarmConditions = alarmConditionDao
					.getListByTemplateId(peDevice.getAlarmTemplateId(), AlarmDeviceType.PEDEVICE,AlarmOptionType.UPS);
			StateEnum currentState = StateEnum.good;
			for (AlarmCondition alarmCondition : alarmConditions) {
				Map<String,Object> alarmMap=upsDeviceAlarm(upsStatus,alarmCondition);
				currentState = alarmConditionService.alarmConditionHandle(peDevice.getManagerId(),upsStatus.getIdentifier(), currentState,
						alarmCondition, alarmMap);
				
			}
			
			peDeviceDao.updateCurrentStateByIdentifier(upsStatus.getIdentifier(),currentState);
			
			StateEnum state=peDeviceDao.findSeverityStateByCollectId(peDevice.getCollectDeviceId());
			collectionDeviceDao.updateCurrentStateById(peDevice.getCollectDeviceId(),state);
		}
	} 
	
	@Override
	public void collectUpsStatus(String ipAaddress, Integer index,PeDevice peDevice) {
		
		
		SNMPUtil snmpUtil=new SNMPUtil();
		//获取UPS采集的数据
		Map<String, String> resultMap=new HashMap<String, String>();
		try {
			resultMap = snmpUtil.walkByGetNext(ipAaddress,
					"1.3.6.1.4.1.34651.2.1.1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(resultMap.size()>0){
			String lastCollectIntervalStr= resultMap
					.get(UpsOidInfo.lastCollectTnterval.getOid() + "."
							+ index);
			if(lastCollectIntervalStr!=null){
				Long lastCollectInterval=Long.parseLong(lastCollectIntervalStr.trim());
				UpsStatus upsStatus=new UpsStatus();
				if (lastCollectInterval < MAX_INTERVAL&&lastCollectInterval>=0) {
					
					upsStatus.setName(resultMap.get(UpsOidInfo.upsName
							.getOid() + "." + index));
					upsStatus.setInterfaceType(Integer.parseInt(resultMap
							.get(UpsOidInfo.interfaceType.getOid() + "."
									+ index).trim()));
					upsStatus.setCommunicationStatus(Integer
							.parseInt(resultMap.get(
									UpsOidInfo.communicationStatus.getOid()
											+ "." + index).trim()));
					upsStatus.setDischargePatterns(Integer
							.parseInt(resultMap.get(
									UpsOidInfo.dischargePatterns.getOid()
											+ "." + index).trim()));
					upsStatus.setUpsType(Integer.parseInt(resultMap.get(
							UpsOidInfo.upsType.getOid() + "." + index)
							.trim()));
					upsStatus.setModelNumber(resultMap
							.get(UpsOidInfo.modelNumber));
					upsStatus.setBrand(resultMap.get(UpsOidInfo.brand));
					upsStatus.setVersionNumber(resultMap
							.get(UpsOidInfo.versionNumber));
					upsStatus.setRateVoltage(Double.parseDouble(resultMap
							.get(UpsOidInfo.ratedVoltage.getOid() + "."
									+ index).trim()) / 10);
					upsStatus.setRatedCurrent(Double.parseDouble(resultMap
							.get(UpsOidInfo.ratedCurrent.getOid() + "."
									+ index).trim()) / 10);
					upsStatus.setBatteryVoltage(Double
							.parseDouble(resultMap.get(
									UpsOidInfo.batteryVoltage.getOid()
											+ "." + index).trim()) / 10);
					upsStatus.setRatedFrequency(Double
							.parseDouble(resultMap.get(
									UpsOidInfo.ratedFrequency.getOid()
											+ "." + index).trim()) / 10);
					upsStatus.setPower(Double
							.parseDouble(resultMap
									.get(UpsOidInfo.power.getOid() + "."
											+ index).trim()) / 10);

					Integer upsStatusResult = Integer.parseInt(resultMap
							.get(UpsOidInfo.upsStatus.getOid() + "."
									+ index).trim());
					if (upsStatus.getRateVoltage() == 220) {
						byte singlePhaseByte = (byte) (upsStatusResult & 0xff);// 最低位
						// 单项电低8位
						String singlePhase = ConverToBitUtils
								.byteToBit(singlePhaseByte);
						upsStatus.setCityVoltageStatus(singlePhase
								.substring(0, 1));
						upsStatus.setBatteryVoltageStatus(singlePhase
								.substring(1, 2));
						upsStatus.setRunningStatus(singlePhase.substring(2,
								3));
						upsStatus.setUpsStatus(singlePhase.substring(3, 4));
						upsStatus.setPatternsStatus(singlePhase.substring(
								4, 5));
						upsStatus
								.setTestStatus(singlePhase.substring(5, 6));
						upsStatus.setShutdownStatus(singlePhase.substring(
								6, 7));
						upsStatus.setBuzzerStatus(singlePhase.substring(7,
								8));

					} else {
						upsStatus.setUpsStatus(resultMap
								.get(UpsOidInfo.upsStatus.getOid() + "."
										+ index).trim());
					}
					upsStatus.setFrequency(Double.parseDouble(resultMap
							.get(UpsOidInfo.frequency.getOid() + "."
									+ index).trim()) / 10);
					upsStatus.setInternalTemperature(Double
							.parseDouble(resultMap.get(
									UpsOidInfo.internalTemperature.getOid()
											+ "." + index).trim()) / 10);
					String byPassVoltage = null;
					String byPassVolage_v = resultMap
							.get(UpsOidInfo.bypassVoltage.getOid() + "."
									+ index).trim();
					if (null != byPassVolage_v) {
						long v = Long.parseLong(resultMap.get(
								UpsOidInfo.bypassVoltage.getOid() + "."
										+ index).trim());
						if (v >= 0) {
							int a = (int) v & 0xffff;
							int b = (int) (v >> 16) & 0xffff;
							int c = (int) (v >> 32) & 0xffff;
							byPassVoltage = ((double) a / 10 + "V/"
									+ (double) b / 10 + "V/" + (double) c
									/ 10 + "V");
						}
					}
					upsStatus.setBypassVoltage(byPassVoltage);
					upsStatus.setBypassFrequency(Double
							.parseDouble(resultMap.get(
									UpsOidInfo.bypassFrequency.getOid()
											+ "." + index).trim()) / 10);

					String inputVoltage_v = resultMap.get(
							UpsOidInfo.inputVoltage.getOid() + "." + index)
							.trim();
					String inputVoltage = null;
					if (null != inputVoltage_v) {
						long v = Long.parseLong(inputVoltage_v.trim()
								.trim());
						int a = (int) v & 0xffff;
						int b = (int) (v >> 16) & 0xffff;
						int c = (int) (v >> 32) & 0xffff;
						inputVoltage = ((double) a / 10 + "V/" + (double) b
								/ 10 + "V/" + (double) c / 10 + "V");
					}

					upsStatus.setInputVoltage(inputVoltage);
					String outputVoltage_v = resultMap
							.get(UpsOidInfo.outputVoltage.getOid() + "."
									+ index).trim();
					String outputVoltage = null;
					if (null != inputVoltage_v) {
						long v = Long.parseLong(outputVoltage_v.trim()
								.trim());
						int a = (int) v & 0xffff;
						int b = (int) (v >> 16) & 0xffff;
						int c = (int) (v >> 32) & 0xffff;
						outputVoltage = ((double) a / 10 + "V/"
								+ (double) b / 10 + "V/" + (double) c / 10 + "V");
					}
					upsStatus.setOutputVoltage(outputVoltage);
					upsStatus.setErrorVoltage(Double.parseDouble(resultMap
							.get(UpsOidInfo.errorVoltage.getOid() + "."
									+ index).trim()) / 10);
					String load_v = resultMap.get(
							UpsOidInfo.load.getOid() + "." + index).trim();
					String load = null;
					if (null != load_v) {
						long v = Long.parseLong(load_v.trim().trim());
						int a = (int) v & 0xffff;
						int b = (int) (v >> 16) & 0xffff;
						int c = (int) (v >> 32) & 0xffff;
						load = ((double) a / 10 + "%/" + (double) b / 10
								+ "%/" + (double) c / 10 + "%");
					}
					upsStatus.setUpsLoad(load);
					upsStatus.setOutputFrenquency(Double
							.parseDouble(resultMap.get(
									UpsOidInfo.outputFrenquency.getOid()
											+ "." + index).trim()) / 10);
					upsStatus.setSingleVoltage(Double.parseDouble(resultMap
							.get(UpsOidInfo.singleVoltage.getOid() + "."
									+ index).trim()) / 100);
					upsStatus.setTotalVoltage(Double.parseDouble(resultMap
							.get(UpsOidInfo.totalVoltage.getOid() + "."
									+ index).trim()) / 10);
					upsStatus.setElectricQuantity(Double
							.parseDouble(resultMap.get(
									UpsOidInfo.electricQuantity.getOid()
											+ "." + index).trim()));
					upsStatus.setPassCurrent(Double.parseDouble(resultMap
							.get(UpsOidInfo.passCurrent.getOid() + "."
									+ index).trim()) / 10);
					upsStatus.setRemainingTime(Integer.parseInt(resultMap
							.get(UpsOidInfo.remainingTime.getOid() + "."
									+ index).trim()));
					//testStatus为1表示没有放电
					upsStatus.setDischargeStatus(Integer.parseInt(resultMap.get(UpsOidInfo.dischargeTest.getOid()+"."+index).trim()));
					UpsStatus newUpsStatus=findNewData(peDevice.getId());
					upsStatus.setCollectTime(DateUtils.parseDate(resultMap
							.get("collectTime")));
					//如果采集到的当前ups处于放电中，并且上次采集的数据没有处于放电中,进行放电统计数据的保存
					if(newUpsStatus!=null){
					if(upsStatus.getDischargeStatus()!=0&&upsStatus.getUpsType()!=2){
						
						DischargeStatistic dischargeStatistic=new DischargeStatistic();
						if(newUpsStatus.getDischargeStatus()==0){
							dischargeStatistic.setBatch(System.currentTimeMillis());
						}else{
							DischargeStatistic d=dischargeStatisticDao.findBeginDateForNewData(peDevice.getId(),null,"desc");
							if(d!=null){
							dischargeStatistic.setBatch(d.getBatch());
							}else{
								dischargeStatistic.setBatch(System.currentTimeMillis());
							}
						}
						dischargeStatistic.setCollectTime(upsStatus.getCollectTime());
						//放电开始的容量
						dischargeStatistic.setCapacity(upsStatus.getElectricQuantity());
						dischargeStatistic.setDeviceId(peDevice.getId());
						dischargeStatistic.setDeviceLoad(Double.parseDouble(upsStatus.getUpsLoad().split("%/")[0]));
						
						dischargeStatisticDao.save(dischargeStatistic);
						
					}
					if(upsStatus.getRemainingTime()==0&&upsStatus.getUpsType()!=2){
						DischargeStatistic newData=null;
						if(upsStatus.getDischargeStatus()!=0){
							DischargeStatistic d=dischargeStatisticDao.findBeginDateForNewData(peDevice.getId(),null,"desc");
							newData=dischargeStatisticDao.findBeforeDataByDeviceIdAndBatch(peDevice.getId(),d.getBatch(),"desc");
						}else{
						newData=dischargeStatisticDao.findBeginDateForNewData(peDevice.getId(),null,"desc");
						}
						if(newData!=null){
							//计算剩余时间
							DischargeStatistic oldData=dischargeStatisticDao.findBeginDateForNewData(peDevice.getId(),newData.getBatch(), "asc");
							//容量差
							Double differenceCapacity=oldData.getCapacity()-newData.getCapacity();
							//时间差，按照分钟计算
							Long newTime=newData.getCollectTime().getTime();
							Long oldTime=oldData.getCollectTime().getTime();
							//时间差
							Long differenceTime=(newTime-oldTime)/1000/60;
							Double avg=dischargeStatisticDao.findAvgLoadByDeviceIdAndBatch(peDevice.getId(),newData.getBatch());
							//消耗1%的容量需要的分钟
							Double unitTime=0.0;
							if(differenceCapacity!=0L){
							 unitTime=differenceTime/differenceCapacity;
							}
							Double remainingTime=upsStatus.getElectricQuantity()*unitTime*avg/(Double.parseDouble(upsStatus.getUpsLoad().split("%/")[0]));
							upsStatus.setRemainingTime(Integer.parseInt(new DecimalFormat("0").format(remainingTime)));
							
						}

					}
					
					}
					upsStatus.setStatus(StatusEnum.NORMAL);
					upsStatus.setLinkState(1);
				
					
					upsStatus.setIdentifier(peDevice.getIdentifier());
				
					mongoDao.save(upsStatus);
					upsAlarmCondition(upsStatus);
					
				} else {
					upsStatus.setLinkState(-1);
					upsStatus.setCollectTime(new Date());
				
					upsStatus.setIdentifier(peDevice.getIdentifier());
				    upsAlarmCondition(upsStatus);
				}
			}
		}
		
		
		
	}
	public Map<String, Object> checkCommunicationStatus(UpsStatus upsStatus,
			AlarmRule alarmRule) {
		Map<String, Object> communicationStatusMap = new HashMap<String, Object>();
		StateEnum runState = StateEnum.good;
		StringBuffer alarmContent = new StringBuffer();
		OperationType operationType = alarmRule.getOperationType();
		Integer value = alarmRule.getValue();
		Integer minValue = alarmRule.getMinValue();
		Integer maxValue = alarmRule.getMaxValue();
		runState = alarmRuleService.ruleCompare(operationType,
				Double.parseDouble(upsStatus.getCommunicationStatus() + ""),
				value, minValue, maxValue,alarmRule.getState());
		if (runState != StateEnum.good) {
			alarmContent.append("").append(alarmRule.getRemark()).append("、");
		}
		communicationStatusMap.put("runState", runState);
		communicationStatusMap.put("alarmContent", alarmContent.toString());
		return communicationStatusMap;
	}

	public Map<String, Object> checkByPassVoltage(UpsStatus upsStatus,
			AlarmRule alarmRule) {
		Map<String, Object> byPassVoltageMap = new HashMap<String, Object>();
		StateEnum runState = StateEnum.good;
		StringBuffer alarmContent = new StringBuffer();
		OperationType operationType = alarmRule.getOperationType();
		Integer value = alarmRule.getValue();
		Integer minValue = alarmRule.getMinValue();
		Integer maxValue = alarmRule.getMaxValue();
		if (upsStatus.getBypassVoltage() != null) {
			String byPassVoltage = upsStatus.getBypassVoltage();
			// 旁路电压一
			Double byPassVoltage_1 = Double.parseDouble(byPassVoltage
					.substring(0, byPassVoltage.indexOf("V")));
			// 判断UPS是三相电
			if (upsStatus.getUpsType() == 2 || upsStatus.getUpsType() == 3) {
				// 旁路电压二
				Double byPassVoltage_2 = Double.parseDouble(byPassVoltage
						.substring(byPassVoltage.indexOf("/") + 1,
								byPassVoltage.lastIndexOf("/") - 1));
				// 旁路电压三
				Double byPassVoltage_3 = Double.parseDouble(byPassVoltage
						.substring(byPassVoltage.lastIndexOf("/") + 1,
								byPassVoltage.length() - 1));
				runState = alarmRuleService.ruleCompare(operationType,
						byPassVoltage_2, value, minValue, maxValue,alarmRule.getState());
				runState = alarmRuleService.ruleCompare(operationType,
						byPassVoltage_3, value, minValue, maxValue,alarmRule.getState());
			}

			runState = alarmRuleService.ruleCompare(operationType,
					byPassVoltage_1, value, minValue, maxValue,alarmRule.getState());
		}
		if (runState != StateEnum.good) {
			alarmContent.append("").append(alarmRule.getRemark()).append("、");
		}
		byPassVoltageMap.put("runState", runState);
		byPassVoltageMap.put("alarmContent", alarmContent.toString());
		return byPassVoltageMap;
	}

	@Override
	public Map<String, Object> checkInputVoltage(UpsStatus upsStatus,
			AlarmRule alarmRule) {
		Map<String, Object> inputVoltageMap = new HashMap<String, Object>();
		StateEnum runState = StateEnum.good;
		StringBuffer alarmContent = new StringBuffer();
		OperationType operationType = alarmRule.getOperationType();
		Integer value = alarmRule.getValue();
		Integer minValue = alarmRule.getMinValue();
		Integer maxValue = alarmRule.getMaxValue();
		if (upsStatus.getInputVoltage() != null) {
			String inputVoltage = upsStatus.getInputVoltage();
			String[] inputVoltages = inputVoltage.split("V/");
			// 输入电压一
			Double inputVoltage_1 = Double.parseDouble(inputVoltages[0]);
			// 判断UPS是三相电
			if (upsStatus.getUpsType() == 2 || upsStatus.getUpsType() == 3) {
				// 输入电压二
				Double inputVoltage_2 = Double.parseDouble(inputVoltages[1]);
				// 输入电压三
				Double inputVoltage_3 = Double.parseDouble(inputVoltages[2]
						.substring(0, inputVoltages[2].length() - 1));
				runState = alarmRuleService.ruleCompare(operationType,
						inputVoltage_2, value, minValue, maxValue,alarmRule.getState());
				runState = alarmRuleService.ruleCompare(operationType,
						inputVoltage_3, value, minValue, maxValue,alarmRule.getState());
			}

			runState = alarmRuleService.ruleCompare(operationType,
					inputVoltage_1, value, minValue, maxValue,alarmRule.getState());
		}
		if (runState != StateEnum.good) {
			alarmContent.append("").append(alarmRule.getRemark()).append("、");
		}
		inputVoltageMap.put("runState", runState);
		inputVoltageMap.put("alarmContent", alarmContent.toString());
		return inputVoltageMap;

	}

	@Override
	public Map<String, Object> checkOutputVoltage(UpsStatus upsStatus,
			AlarmRule alarmRule) {
		Map<String, Object> outputVoltageMap = new HashMap<String, Object>();
		StateEnum runState = StateEnum.good;
		StringBuffer alarmContent = new StringBuffer();
		OperationType operationType = alarmRule.getOperationType();
		Integer value = alarmRule.getValue();
		Integer minValue = alarmRule.getMinValue();
		Integer maxValue = alarmRule.getMaxValue();
		if (upsStatus.getOutputVoltage() != null) {
			String outputVoltage = upsStatus.getOutputVoltage();
			// 输出电压一
			Double outputVoltage_1 = Double.parseDouble(outputVoltage
					.substring(0, outputVoltage.indexOf("V")));
			// 判断UPS是三相电
			if (upsStatus.getUpsType() == 2 || upsStatus.getUpsType() == 3) {
				// 输出电压二
				Double outputVoltage_2 = Double.parseDouble(outputVoltage
						.substring(outputVoltage.indexOf("/") + 1,
								outputVoltage.lastIndexOf("/") - 1));
				// 输出电压三
				Double outputVoltage_3 = Double.parseDouble(outputVoltage
						.substring(outputVoltage.lastIndexOf("/") + 1,
								outputVoltage.length() - 1));
				runState = alarmRuleService.ruleCompare(operationType,
						outputVoltage_2, value, minValue, maxValue,alarmRule.getState());
				runState = alarmRuleService.ruleCompare(operationType,
						outputVoltage_3, value, minValue, maxValue,alarmRule.getState());
			}

			runState = alarmRuleService.ruleCompare(operationType,
					outputVoltage_1, value, minValue, maxValue,alarmRule.getState());
		}
		if (runState != StateEnum.good) {
			alarmContent.append("").append(alarmRule.getRemark()).append("、");
		}
		outputVoltageMap.put("runState", runState);
		outputVoltageMap.put("alarmContent", alarmContent.toString());
		return outputVoltageMap;

	}

	@Override
	public Map<String, Object> checkInternalTemperature(UpsStatus upsStatus,
			AlarmRule alarmRule) {
		Map<String, Object> internalTemperatureMap = new HashMap<String, Object>();
		StateEnum runState = StateEnum.good;
		StringBuffer alarmContent = new StringBuffer();
		OperationType operationType = alarmRule.getOperationType();
		Integer value = alarmRule.getValue();
		Integer minValue = alarmRule.getMinValue();
		Integer maxValue = alarmRule.getMaxValue();
		runState = alarmRuleService.ruleCompare(operationType,
				upsStatus.getInternalTemperature(), value, minValue, maxValue,alarmRule.getState());
		if (runState != StateEnum.good) {
			alarmContent.append("").append(alarmRule.getRemark()).append("、");
		}
		internalTemperatureMap.put("runState", runState);
		internalTemperatureMap.put("alarmContent", alarmContent.toString());
		return internalTemperatureMap;
	}

	@Override
	public Map<String, Object> checkBatteryVoltage(UpsStatus upsStatus,
			AlarmRule alarmRule) {
		Map<String, Object> batteryVoltageMap = new HashMap<String, Object>();
		StateEnum runState = StateEnum.good;
		StringBuffer alarmContent = new StringBuffer();
		OperationType operationType = alarmRule.getOperationType();
		Integer value = alarmRule.getValue();
		Integer minValue = alarmRule.getMinValue();
		Integer maxValue = alarmRule.getMaxValue();
		runState = alarmRuleService.ruleCompare(operationType,
				Double.parseDouble(upsStatus.getBatteryVoltage() + ""), value,
				minValue, maxValue,alarmRule.getState());
		if (runState == StateEnum.error) {
			alarmContent.append("").append(alarmRule.getRemark()).append("、");
		}
		batteryVoltageMap.put("runState", runState);
		batteryVoltageMap.put("alarmContent", alarmContent.toString());
		return batteryVoltageMap;
	}

	public Map<String, Object> checkElectricQuantity(UpsStatus upsStatus,
			AlarmRule alarmRule) {
		Map<String, Object> electricQuantityMap = new HashMap<String, Object>();
		StateEnum runState = StateEnum.good;
		StringBuffer alarmContent = new StringBuffer();
		OperationType operationType = alarmRule.getOperationType();
		Integer value = alarmRule.getValue();
		Integer minValue = alarmRule.getMinValue();
		Integer maxValue = alarmRule.getMaxValue();
		runState = alarmRuleService.ruleCompare(operationType,
				upsStatus.getElectricQuantity(), value, minValue, maxValue,alarmRule.getState());
		if (runState != StateEnum.good) {
			alarmContent.append("").append(alarmRule.getRemark()).append("、");
		}
		electricQuantityMap.put("runState", runState);
		electricQuantityMap.put("alarmContent", alarmContent.toString());
		return electricQuantityMap;
	}
	public Map<String, Object> checkLoad(UpsStatus upsStatus,
			AlarmRule alarmRule) {
		Map<String, Object> upsLoadMap = new HashMap<String, Object>();
		StateEnum runState = StateEnum.good;
		StringBuffer alarmContent = new StringBuffer();
		OperationType operationType = alarmRule.getOperationType();
		Integer value = alarmRule.getValue();
		Integer minValue = alarmRule.getMinValue();
		Integer maxValue = alarmRule.getMaxValue();
		if (upsStatus.getUpsLoad() != null) {
			String upsLoad = upsStatus.getUpsLoad();
			String[] upsLoads=upsLoad.split("%/");
			// 负载一
			Double upsLoad_1 = Double.parseDouble(upsLoads[0]);
			// 判断UPS是三相电
			if (upsStatus.getUpsType() == 2 || upsStatus.getUpsType() == 3) {
				// 负载二
				Double upsLoad_2 = Double.parseDouble(upsLoads[1]);
				// 负载三
				Double upsLoad_3 = Double.parseDouble(upsLoads[2]);
				runState = alarmRuleService.ruleCompare(operationType,
						upsLoad_2, value, minValue, maxValue,alarmRule.getState());
				runState = alarmRuleService.ruleCompare(operationType,
						upsLoad_3, value, minValue, maxValue,alarmRule.getState());
			}

			runState = alarmRuleService.ruleCompare(operationType,
					upsLoad_1, value, minValue, maxValue,alarmRule.getState());
		}
		if (runState != StateEnum.good) {
			alarmContent.append("").append(alarmRule.getRemark()).append("、");
		}
		
		upsLoadMap.put("runState", runState);
		upsLoadMap.put("alarmContent", alarmContent.toString());
		return upsLoadMap;
	}
	@Override
	public Map<String, Object> upsDeviceAlarm(UpsStatus upsStatus,
			AlarmCondition alarmCondition) {
		Map<String, Object> alarmResultMap = new HashMap<String, Object>();
		StateEnum runState = StateEnum.good;
		List<AlarmRule> alarmRules = alarmRuleDao
				.getListByConditionIdAndAlarmOptionType(alarmCondition.getId(),
						AlarmOptionType.UPS);

		StringBuffer alarmContent = new StringBuffer();
		for (AlarmRule alarmRule : alarmRules) {
			// 通讯状态规则
			if (alarmRule.getAlarmRuleType() == AlarmRuleType.COMMUNICATIONSTATUS) {
				Map<String, Object> communicationStatusMap = checkCommunicationStatus(
						upsStatus, alarmRule);
				if (runState.ordinal() < StateEnum.valueOf(
						communicationStatusMap.get("runState").toString())
						.ordinal()) {
					runState = StateEnum.valueOf(communicationStatusMap.get(
							"runState").toString());
				}
				alarmContent.append(communicationStatusMap.get("alarmContent")
						.toString());
			}
			if (alarmRule.getAlarmRuleType() == AlarmRuleType.BYPASSVOLTAGE) { // UPS旁路电压
				if (upsStatus.getBypassVoltage() != null) {
					Map<String, Object> byPassVoltageMap = checkByPassVoltage(
							upsStatus, alarmRule);
					if (runState.ordinal() < StateEnum.valueOf(
							byPassVoltageMap.get("runState").toString())
							.ordinal()) {
						runState = StateEnum.valueOf(byPassVoltageMap.get(
								"runState").toString());
					}
					alarmContent.append(byPassVoltageMap.get("alarmContent")
							.toString());
				}
			}
			if (alarmRule.getAlarmRuleType() == AlarmRuleType.INPUTVOLTAGE) {
				if (upsStatus.getInputVoltage() != null) {
					Map<String, Object> inputVoltageMap = checkInputVoltage(
							upsStatus, alarmRule);
					if (runState.ordinal() < StateEnum.valueOf(
							inputVoltageMap.get("runState").toString())
							.ordinal()) {
						runState = StateEnum.valueOf(inputVoltageMap.get(
								"runState").toString());
					}
					alarmContent.append(inputVoltageMap.get("alarmContent")
							.toString());
				}
			}
			if (alarmRule.getAlarmRuleType() == AlarmRuleType.OUTPUTVOLTAGE) {
				if (upsStatus.getOutputVoltage() != null) {
					Map<String, Object> outputVoltageMap = checkOutputVoltage(
							upsStatus, alarmRule);
					if (runState.ordinal() < StateEnum.valueOf(
							outputVoltageMap.get("runState").toString())
							.ordinal()) {
						runState = StateEnum.valueOf(outputVoltageMap.get(
								"runState").toString());
					}
					alarmContent.append(outputVoltageMap.get("alarmContent")
							.toString());
				}
			}
			// 机内温度规则
			if (alarmRule.getAlarmRuleType() == AlarmRuleType.INTERNALTEMPERATURE) {
				Map<String, Object> internalTemperatureMap = checkInternalTemperature(
						upsStatus, alarmRule);
				if (runState.ordinal() < StateEnum.valueOf(
						internalTemperatureMap.get("runState").toString())
						.ordinal()) {
					runState = StateEnum.valueOf(internalTemperatureMap.get(
							"runState").toString());
				}
				alarmContent.append(internalTemperatureMap.get("alarmContent")
						.toString());
			}
			// 电池电压
			if (alarmRule.getAlarmRuleType() == AlarmRuleType.BATTERYVOLTAGE) {
				Map<String, Object> batteryVoltageMap = checkBatteryVoltage(
						upsStatus, alarmRule);
				if (runState.ordinal() < StateEnum.valueOf(
						batteryVoltageMap.get("runState").toString()).ordinal()) {
					runState = StateEnum.valueOf(batteryVoltageMap.get(
							"runState").toString());
				}
				alarmContent.append(batteryVoltageMap.get("alarmContent")
						.toString());
			}
			if (upsStatus.getUpsType() != 2 && upsStatus.getUpsType() != 3) {
				// 电池电量
				if (alarmRule.getAlarmRuleType() == AlarmRuleType.ELECTRICQUANTITY) {
					Map<String, Object> electricQuantityMap = checkElectricQuantity(
							upsStatus, alarmRule);
					if (runState.ordinal() < StateEnum.valueOf(
							electricQuantityMap.get("runState").toString())
							.ordinal()) {
						runState = StateEnum.valueOf(electricQuantityMap.get(
								"runState").toString());
					}
					alarmContent.append(electricQuantityMap.get("alarmContent")
							.toString());
				}
			}
			// 负载
			if (alarmRule.getAlarmRuleType() == AlarmRuleType.LOAD) {
				Map<String, Object> loadMap = checkLoad(
						upsStatus, alarmRule);
				if (runState.ordinal() < StateEnum.valueOf(
						loadMap.get("runState").toString()).ordinal()) {
					runState = StateEnum.valueOf(loadMap.get(
							"runState").toString());
				}
				alarmContent.append(loadMap.get("alarmContent")
						.toString());
			}

		}
		if(alarmContent.indexOf("、")>0){
			alarmContent=new StringBuffer(alarmContent.substring(0,alarmContent.length()-1));
		}
		alarmResultMap.put("runState", runState);
		alarmResultMap.put("alarmContent", alarmContent.toString());
		return alarmResultMap;
	}


}
