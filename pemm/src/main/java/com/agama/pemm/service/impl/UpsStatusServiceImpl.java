package com.agama.pemm.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agama.common.dao.utils.Page;
import com.agama.common.service.impl.BaseServiceImpl;
import com.agama.common.utils.DateUtils;
import com.agama.pemm.bean.AlarmType;
import com.agama.pemm.bean.DeviceType;
import com.agama.pemm.dao.IAlarmLogDao;
import com.agama.pemm.dao.IDeviceDao;
import com.agama.pemm.dao.IGitInfoDao;
import com.agama.pemm.dao.IUpsStatusDao;
import com.agama.pemm.domain.AlarmLog;
import com.agama.pemm.domain.Device;
import com.agama.pemm.domain.GitInfo;
import com.agama.pemm.domain.UpsStatus;
import com.agama.pemm.protocol.snmp.UpsOidInfo;
import com.agama.pemm.service.IUpsStatusService;
import com.agama.pemm.utils.ConverToBitUtils;
import com.agama.pemm.utils.SNMPUtil;

@Service
@Transactional
public class UpsStatusServiceImpl extends BaseServiceImpl<UpsStatus, Integer>
		implements IUpsStatusService {

	private Logger log = LoggerFactory.getLogger(UpsStatusServiceImpl.class);
	@Autowired
	private IUpsStatusDao upsStatusDao;
	@Autowired
	private IDeviceDao deviceDao;
	@Autowired
	private IGitInfoDao gitInfoDao;
	@Autowired
	private IAlarmLogDao alarmLogDao;

	@Scheduled(fixedRateString = "${interval}")
	public void saveUpsStatus() {
		List<GitInfo> gitInfos = gitInfoDao.findListByStatus(0);
		for (GitInfo gitInfo : gitInfos) {
			List<Device> devices = gitInfo.getDevices();
			for (Device device : devices) {
				UpsStatus upsStatus = collectUspStatus(gitInfo.getIp(),
						device.getDeviceIndex(), device.getId());
				upsStatusDao.save(upsStatus);
			}
		}

	}

	public UpsStatus collectUspStatus(String ipAaddress, Integer index,
			Integer deviceId) {
		log.info("开始采集UPS信息...");
		long start=System.currentTimeMillis();  
		UpsStatus upsStatus = new UpsStatus();
		try {
			SNMPUtil snmp = new SNMPUtil();
			Map<String, String> resultMap = snmp.walkByGetNext(ipAaddress,
					"1.3.6.1.4.1.34651.2.1.1");
			String lastCollectIntervalStr = resultMap.get(
					UpsOidInfo.lastCollectTnterval.getOid() + "." + index)
					;
			if (lastCollectIntervalStr != null) {
				Long lastCollectTnterval = Long
						.parseLong(lastCollectIntervalStr.trim());
				if (lastCollectTnterval < 20) {
					upsStatus.setName(resultMap.get(UpsOidInfo.upsName.getOid()
							+ "." + index));

					upsStatus.setInterfaceType(Integer.parseInt(resultMap.get(
							UpsOidInfo.interfaceType.getOid() + "." + index)
							.trim()));

					upsStatus.setCommunicationStatus(Integer.parseInt(resultMap
							.get(UpsOidInfo.communicationStatus.getOid() + "."
									+ index).trim()));
					upsStatus.setDischargePatterns(Integer.parseInt(resultMap
							.get(UpsOidInfo.dischargePatterns.getOid() + "."
									+ index).trim()));
					upsStatus.setUpsType(Integer.parseInt(resultMap.get(
							UpsOidInfo.upsType.getOid() + "." + index).trim()));
					upsStatus.setModelNumber(resultMap
							.get(UpsOidInfo.modelNumber));
					upsStatus.setBrand(resultMap.get(UpsOidInfo.brand));
					upsStatus.setVersionNumber(resultMap
							.get(UpsOidInfo.versionNumber));
					upsStatus.setRateVoltage(Double.parseDouble(resultMap.get(
							UpsOidInfo.ratedVoltage.getOid() + "." + index)
							.trim()) / 10);
					upsStatus.setRatedCurrent(Double.parseDouble(resultMap.get(
							UpsOidInfo.ratedCurrent.getOid() + "." + index)
							.trim()) / 10);
					upsStatus.setBatteryVoltage(Double.parseDouble(resultMap
							.get(UpsOidInfo.batteryVoltage.getOid() + "."
									+ index).trim()) / 10);
					upsStatus.setRatedFrequency(Double.parseDouble(resultMap
							.get(UpsOidInfo.ratedFrequency.getOid() + "."
									+ index).trim()) / 10);
					upsStatus
							.setPower(Double.parseDouble(resultMap.get(
									UpsOidInfo.power.getOid() + "." + index)
									.trim()) / 10);

					Integer upsStatusResult = Integer
							.parseInt(resultMap
									.get(UpsOidInfo.upsStatus.getOid() + "."
											+ index).trim());
					if (upsStatus.getRateVoltage() == 220) {

						byte singlePhaseByte = (byte) (upsStatusResult & 0xff);// 最低位
						// 单项电低8位
						String singlePhase = ConverToBitUtils
								.byteToBit(singlePhaseByte);
						upsStatus.setCityVoltageStatus(singlePhase.substring(0,
								1));
						upsStatus.setBatteryVoltageStatus(singlePhase
								.substring(1, 2));
						upsStatus.setRunningStatus(singlePhase.substring(2, 3));
						upsStatus.setUpsStatus(singlePhase.substring(3, 4));
						upsStatus
								.setPatternsStatus(singlePhase.substring(4, 5));
						upsStatus.setTestStatus(singlePhase.substring(5, 6));
						upsStatus
								.setShutdownStatus(singlePhase.substring(6, 7));
						upsStatus.setBuzzerStatus(singlePhase.substring(7, 8));

					} else {

						upsStatus.setUpsStatus(resultMap.get(
								UpsOidInfo.upsStatus.getOid() + "." + index)
								.trim());
					}
					upsStatus.setFrequency(Double
							.parseDouble(resultMap
									.get(UpsOidInfo.frequency.getOid() + "."
											+ index).trim()) / 10);
					upsStatus.setInternalTemperature(Double
							.parseDouble(resultMap.get(
									UpsOidInfo.internalTemperature.getOid()
											+ "." + index).trim()) / 10);

					String byPassVoltage = null;
					String byPassVolage_v = resultMap.get(
							UpsOidInfo.bypassVoltage.getOid() + "." + index)
							.trim();
					if (null != byPassVolage_v) {
						long v = Long.parseLong(resultMap
								.get(UpsOidInfo.bypassVoltage.getOid() + "."
										+ index).trim());
						if (v >= 0) {
							int a = (int) v & 0xffff;
							int b = (int) (v >> 16) & 0xffff;
							int c = (int) (v >> 32) & 0xffff;
							byPassVoltage = ((double) a / 10 + "V/"
									+ (double) b / 10 + "V/" + (double) c / 10 + "V");
						}
					}
					upsStatus.setBypassVoltage(byPassVoltage);
					upsStatus.setBypassFrequency(Double.parseDouble(resultMap
							.get(UpsOidInfo.bypassFrequency.getOid() + "."
									+ index).trim()) / 10);

					String inputVoltage_v = resultMap.get(
							UpsOidInfo.inputVoltage.getOid() + "." + index)
							.trim();
					String inputVoltage = null;
					if (null != inputVoltage_v) {
						long v = Long.parseLong(inputVoltage_v.trim().trim());
						int a = (int) v & 0xffff;
						int b = (int) (v >> 16) & 0xffff;
						int c = (int) (v >> 32) & 0xffff;
						inputVoltage = ((double) a / 10 + "V/" + (double) b
								/ 10 + "V/" + (double) c / 10 + "V");
					}

					upsStatus.setInputVoltage(inputVoltage);

					String outputVoltage_v = resultMap.get(
							UpsOidInfo.outputVoltage.getOid() + "." + index)
							.trim();
					String outputVoltage = null;
					if (null != inputVoltage_v) {
						long v = Long.parseLong(outputVoltage_v.trim().trim());
						int a = (int) v & 0xffff;
						int b = (int) (v >> 16) & 0xffff;
						int c = (int) (v >> 32) & 0xffff;
						outputVoltage = ((double) a / 10 + "v/" + (double) b
								/ 10 + "v/" + (double) c / 10 + "v");
					}
					upsStatus.setOutputVoltage(outputVoltage);

					upsStatus.setErrorVoltage(Double.parseDouble(resultMap.get(
							UpsOidInfo.errorVoltage.getOid() + "." + index)
							.trim()) / 10);

					String load_v = resultMap.get(
							UpsOidInfo.load.getOid() + "." + index).trim();
					String load = null;
					if (null != load_v) {
						long v = Long.parseLong(load_v.trim().trim());
						int a = (int) v & 0xffff;
						int b = (int) (v >> 16) & 0xffff;
						int c = (int) (v >> 32) & 0xffff;
						load = ((double) a / 10 + "%/" + (double) b / 10 + "%/"
								+ (double) c / 10 + "%");
					}

					upsStatus.setUpsLoad(load);

					upsStatus.setOutputFrenquency(Double.parseDouble(resultMap
							.get(UpsOidInfo.outputFrenquency.getOid() + "."
									+ index).trim()) / 10);
					upsStatus.setSingleVoltage(Double.parseDouble(resultMap
							.get(UpsOidInfo.singleVoltage.getOid() + "."
									+ index).trim()) / 100);
					upsStatus.setTotalVoltage(Double.parseDouble(resultMap.get(
							UpsOidInfo.totalVoltage.getOid() + "." + index)
							.trim()) / 10);
					upsStatus.setElectricQuantity(Double.parseDouble(resultMap
							.get(UpsOidInfo.electricQuantity.getOid() + "."
									+ index).trim()));
					upsStatus.setPassCurrent(Double.parseDouble(resultMap.get(
							UpsOidInfo.passCurrent.getOid() + "." + index)
							.trim()) / 10);
					upsStatus.setRemainingTime(Integer.parseInt(resultMap.get(
							UpsOidInfo.remainingTime.getOid() + "." + index)
							.trim()));
					upsStatus.setCollectTime(DateUtils.parseDate(resultMap
							.get("collectTime")));
					upsStatus.setStatus(0);			
					Device device = deviceDao.find(deviceId);
					upsStatus.setDevice(device);
					AlarmLog alarmLog=new AlarmLog();
					alarmLog.setCollectTime(new Date());
					alarmLog.setAlarmType(AlarmType.error);
					alarmLog.setContent("UPS电压异常");
					alarmLog.setDeviceType(DeviceType.UPS);
					alarmLog.setDeviceId(deviceId);
					alarmLogDao.save(alarmLog);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long end=System.currentTimeMillis();  
		log.info("UPS采集所用时间："+(end-start)+"ms");
		return upsStatus;
	}

	@Override
	public List<UpsStatus> findLatestDataByGitInfoId(Integer gitInfoId) {
		return upsStatusDao.findLatestDataByGitInfoId(gitInfoId);

	}

	@Override
	public Page<UpsStatus> searchByGitInfoIdAndDeviceTypeAndIndex(
			Page<UpsStatus> page, Integer gitInfoId, DeviceType ups,
			Integer index) {
		StringBuffer hql = new StringBuffer(
				"from UpsStatus where status=0 and device.id in (select id from Device where status=0 and deviceType=")
				.append(ups.ordinal());
		if (gitInfoId != null) {
			hql.append(" and gitInfo.id=").append(gitInfoId);
		}
		if (index != null) {
			hql.append(" and deviceIndex=").append(index);
		}
		hql.append(") order by id desc");
		return upsStatusDao.findPage(page, hql.toString());
	}

	@Override
	public void updateStatusByIds(String ids) {
		upsStatusDao.updateStatusByIds(ids);

	}

}
