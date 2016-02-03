package com.agama.pemm.task;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;





import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.agama.common.domain.ServerStateEnum;
import com.agama.common.domain.StateEnum;
import com.agama.common.enumbean.DeviceType;
import com.agama.pemm.dao.IAlarmLogDao;
import com.agama.pemm.dao.IDeviceDao;
import com.agama.pemm.dao.IDischargeStatisticDao;
import com.agama.pemm.dao.IGitInfoDao;
import com.agama.pemm.dao.ISwitchInputStatusDao;
import com.agama.pemm.dao.IThStatusDao;
import com.agama.pemm.dao.IUpsStatusDao;
import com.agama.pemm.domain.Device;
import com.agama.pemm.domain.DischargeStatistic;
import com.agama.pemm.domain.GitInfo;
import com.agama.pemm.domain.SwitchInputStatus;
import com.agama.pemm.domain.ThStatus;
import com.agama.pemm.domain.UpsStatus;
import com.agama.pemm.protocol.snmp.SwitchInputOidInfo;
import com.agama.pemm.protocol.snmp.ThOidInfo;
import com.agama.pemm.protocol.snmp.UpsOidInfo;
import com.agama.pemm.service.IAcStatusService;
import com.agama.pemm.service.IAlarmConditionService;
import com.agama.pemm.service.IGitInfoService;
import com.agama.pemm.service.impl.UpsStatusServiceImpl;
import com.agama.pemm.utils.CheckServerConnectUtils;
import com.agama.pemm.utils.ConverToBitUtils;
import com.agama.pemm.utils.SNMPUtil;
import com.agama.tool.utils.ConvertUtils;
import com.agama.tool.utils.date.DateUtils;

@Component
@Transactional
public class DataCollectionService {

	private Logger logger = LoggerFactory.getLogger(UpsStatusServiceImpl.class);

	private static final Integer MAX_INTERVAL = 20000;
	public static SNMPUtil snmp = new SNMPUtil();
	public static StateEnum deviceCurrentState=StateEnum.good;
	@Autowired
	private IGitInfoService gitInfoService;
	@Autowired
	private IDeviceDao deviceDao;

	@Autowired
	private IUpsStatusDao upsStatusDao;
	@Autowired
	private IThStatusDao thStatusDao;
	@Autowired
	private ISwitchInputStatusDao switchInputStatusDao;

	@Autowired
	private IAlarmConditionService alarmConditionService;
	@Autowired
	private IAlarmLogDao alarmLogDao;
	@Autowired
	private IDischargeStatisticDao dischargeStatisticDao;
	
	@Autowired
	private IAcStatusService acStatusService;

	@Scheduled(fixedRateString = "${interval}")
	public void saveUpsStatus() {
		deviceCurrentState=StateEnum.good;
		logger.info("采集开始...");
		long start = System.currentTimeMillis();
		try {
			List<GitInfo> gitInfos = gitInfoService.getListByStatus(0);
			for (GitInfo gitInfo : gitInfos) {
				dataHelper(gitInfo);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		long end = System.currentTimeMillis();
		logger.info("采集所用时间：" + (end - start) + "ms");

	}
	@Transactional(readOnly=false)
	public void dataHelper(GitInfo gitInfo){
		//检测IP是否可达
		boolean connect = CheckServerConnectUtils.pingByServer(gitInfo
				.getIp());
		if (connect) {
			List<Device> devices = deviceDao.getDeviceListByGitIdAndStatus(
					gitInfo.getId(), 0);
			for (Device device : devices) {
				if (device.getDeviceType() == DeviceType.UPS) {
					collectUspStatus(gitInfo.getIp(),
							device.getDeviceIndex(), device.getId());
				} else if (device.getDeviceType() == DeviceType.TH) {
					collectThStatus(gitInfo.getIp(),
							device.getDeviceIndex(), device.getId());
				} else if(device.getDeviceType()==DeviceType.AC){
					acStatusService.collectAcStatus(gitInfo.getIp(),device.getDeviceIndex(),device.getId());
				}
				
//				else if (device.getDeviceType() == DeviceType.SWITCHINPUT
//						&& device.getDeviceInterfaceType() != null) {

//					collectSwitchInputStatus(gitInfo.getIp(),
//							device.getDeviceIndex(), device.getId());
//				}

			}
			if (gitInfo.getServerState() != ServerStateEnum.connect) {
				gitInfo.setServerState(ServerStateEnum.connect);
				gitInfoService.update(gitInfo);
			}
		} else {
			gitInfo.setServerState(ServerStateEnum.unconnect);
			gitInfoService.update(gitInfo);

		}
	}
	/**
	 * 根据IP地址获取UPS的状态信息
	 * 
	 * @param ipAaddress
	 *            IP地址
	 */

	public void collectUspStatus(String ipAaddress, Integer index,
			Integer deviceId) {

		UpsStatus upsStatus = new UpsStatus();
		try {
			snmp = new SNMPUtil();
			//获取UPS采集的数据
			Map<String, String> resultMap = snmp.walkByGetNext(ipAaddress,
					"1.3.6.1.4.1.34651.2.1.1");
			//采集到数据
			if (resultMap.size() > 0) {
				//获取最后最后一次采集到的时间间隔
				String lastCollectIntervalStr = resultMap
						.get(UpsOidInfo.lastCollectTnterval.getOid() + "."
								+ index);
				if (lastCollectIntervalStr != null) {
					// 距离最后一次采集的时间间隔
					Long lastCollectInterval = Long
							.parseLong(lastCollectIntervalStr.trim());
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
						UpsStatus newUpsStatus=upsStatusDao.findNewData(deviceId);
						upsStatus.setCollectTime(DateUtils.parseDate(resultMap
								.get("collectTime")));
						//如果采集到的当前ups处于放电中，并且上次采集的数据没有处于放电中,进行放电统计数据的保存
						if(newUpsStatus!=null){
						if(upsStatus.getDischargeStatus()!=0&&upsStatus.getUpsType()!=2){
							
							DischargeStatistic dischargeStatistic=new DischargeStatistic();
							if(newUpsStatus.getDischargeStatus()==0){
								dischargeStatistic.setBatch(System.currentTimeMillis());
							}else{
								DischargeStatistic d=dischargeStatisticDao.findBeginDateForNewData(deviceId,null,"desc");
								if(d!=null){
								dischargeStatistic.setBatch(d.getBatch());
								}else{
									dischargeStatistic.setBatch(System.currentTimeMillis());
								}
							}
							dischargeStatistic.setCollectTime(upsStatus.getCollectTime());
							//放电开始的容量
							dischargeStatistic.setCapacity(upsStatus.getElectricQuantity());
							dischargeStatistic.setDeviceId(deviceId);
							dischargeStatistic.setDeviceLoad(Double.parseDouble(upsStatus.getUpsLoad().split("%/")[0]));
							
							dischargeStatisticDao.save(dischargeStatistic);
							
						}
						if(upsStatus.getRemainingTime()==0&&upsStatus.getUpsType()!=2){
							DischargeStatistic newData=null;
							if(upsStatus.getDischargeStatus()!=0){
								DischargeStatistic d=dischargeStatisticDao.findBeginDateForNewData(deviceId,null,"desc");
								newData=dischargeStatisticDao.findBeforeDataByDeviceIdAndBatch(deviceId,d.getBatch(),"desc");
							}else{
							newData=dischargeStatisticDao.findBeginDateForNewData(deviceId,null,"desc");
							}
							if(newData!=null){
								//计算剩余时间
								DischargeStatistic oldData=dischargeStatisticDao.findBeginDateForNewData(deviceId,newData.getBatch(), "asc");
								//容量差
								Double differenceCapacity=oldData.getCapacity()-newData.getCapacity();
								//时间差，按照分钟计算
								Long newTime=newData.getCollectTime().getTime();
								Long oldTime=oldData.getCollectTime().getTime();
								//时间差
								Long differenceTime=(newTime-oldTime)/1000/60;
								Double avg=dischargeStatisticDao.findAvgLoadByDeviceIdAndBatch(deviceId,newData.getBatch());
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
						upsStatus.setStatus(0);
						upsStatus.setLinkState(1);
					
						Device device = deviceDao.find(deviceId);
						upsStatus.setDevice(device);
						alarmConditionService.upsAlarmCondition(upsStatus);
						upsStatusDao.save(upsStatus);
					} else {
						upsStatus.setLinkState(-1);
						upsStatus.setCollectTime(new Date());
						Device device = deviceDao.find(deviceId);
						upsStatus.setDevice(device);
						alarmConditionService.upsAlarmCondition(upsStatus);
					}
				}

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public void collectThStatus(String ipAaddress, Integer index,
			Integer deviceId) {

		ThStatus thStatus = new ThStatus();

		try {
			snmp = new SNMPUtil();
			Map<String, String> resultMap = snmp.walkByGetNext(ipAaddress,
					"1.3.6.1.4.1.34651.2.2.1");
			if (resultMap.size() > 0) {
				String lastCollectIntervalStr = resultMap
						.get(ThOidInfo.lastCollectInterval.getOid() + "."
								+ index);
				if (lastCollectIntervalStr != null) {
					// 距离最后一次采集的时间间隔
					Long lastCollectInterval = Long
							.parseLong(lastCollectIntervalStr.trim());
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
						thStatus.setStatus(0);
						thStatus.setDeviceId(deviceId);
						thStatus.setTemperatureState(StateEnum.good.toString());
						thStatus.setHumidityState(StateEnum.good.toString());
						thStatusDao.save(thStatus);
						alarmConditionService.thAlarmCondition(thStatus);
						
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public void collectSwitchInputStatus(String ipAaddress, Integer index,
			Integer deviceId) {

		SwitchInputStatus inputStatus = new SwitchInputStatus();

		try {
			snmp = new SNMPUtil();
			Map<String, String> resultMap = snmp.walkByGetNext(ipAaddress,
					"1.3.6.1.4.1.34651.2.3.1");
			if (resultMap.size() > 0) {
				
				inputStatus.setName(ConvertUtils
						.change2Chinese(resultMap
								.get(SwitchInputOidInfo.NAME.getOid()
										+ "." + index)));

				inputStatus.setInputSignal(Integer.parseInt(resultMap
						.get(SwitchInputOidInfo.SIGNAL.getOid() + "."
								+ index).trim()));
				inputStatus.setCollectTime(new Date());
				inputStatus.setStatus(0);
				inputStatus.setDeviceId(deviceId);
				StateEnum stateEnum = alarmConditionService
						.switchInputAlarmCondition(inputStatus);
				inputStatus.setCurrentState(stateEnum.ordinal());
				switchInputStatusDao.save(inputStatus);
					
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
