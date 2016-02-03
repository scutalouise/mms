package com.agama.pemm.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agama.authority.dao.IUserDao;
import com.agama.common.dao.utils.Page;
import com.agama.common.domain.StateEnum;
import com.agama.common.enumbean.AlarmType;
import com.agama.common.enumbean.DeviceInterfaceType;
import com.agama.common.enumbean.DeviceType;
import com.agama.common.enumbean.OperationType;
import com.agama.common.service.impl.BaseServiceImpl;
import com.agama.pemm.bean.UpsChartBean;
import com.agama.pemm.dao.IAlarmConditionDao;
import com.agama.pemm.dao.IAlarmLogDao;
import com.agama.pemm.dao.IAlarmRuleDao;
import com.agama.pemm.dao.IDeviceDao;
import com.agama.pemm.dao.IGitInfoDao;
import com.agama.pemm.dao.IUpsStatusDao;
import com.agama.pemm.domain.AlarmRule;
import com.agama.pemm.domain.UpsStatus;
import com.agama.pemm.service.IAlarmRuleService;
import com.agama.pemm.service.IUpsStatusService;

/**
 * @Description:UPS状态业务逻辑实体类
 * @Author:ranjunfeng
 * @Since :2015年9月30日 上午10:15:21
 */
@Service
@Transactional
public class UpsStatusServiceImpl extends BaseServiceImpl<UpsStatus, Integer>
		implements IUpsStatusService {

	private Logger logger = LoggerFactory.getLogger(UpsStatusServiceImpl.class);
	@Autowired
	private IUpsStatusDao upsStatusDao;
	@Autowired
	private IDeviceDao deviceDao;
	@Autowired
	private IGitInfoDao gitInfoDao;
	@Autowired
	private IAlarmLogDao alarmLogDao;
	@Autowired
	private IAlarmRuleDao alarmRuleDao;
	@Autowired
	private IUserDao userDao;
	@Autowired
	private IAlarmConditionDao alarmConditionDao;
	@Autowired
	private IAlarmRuleService alarmRuleService;

	public Map<String, Object> checkAlarm(UpsStatus upsStatus,
			Integer alarmConditionId) {
		Map<String, Object> alarmResultMap = new HashMap<String, Object>();
		StateEnum runState = StateEnum.good;
		List<AlarmRule> alarmRules = alarmRuleDao
				.getListByConditionIdAndDeviceInterfaceType(alarmConditionId,
						DeviceInterfaceType.UPS);
		List<Map<String,Object>> alarmMapList=new ArrayList<Map<String,Object>>();
		StringBuffer alarmContent = new StringBuffer();
		for (AlarmRule alarmRule : alarmRules) {
			// 通讯状态规则
			if (alarmRule.getAlarmType() == AlarmType.COMMUNICATIONSTATUS) {
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
				if(StateEnum.valueOf(communicationStatusMap.get("runState").toString())!=StateEnum.good){
					alarmMapList.add(communicationStatusMap);
				}
			}
			if (alarmRule.getAlarmType() == AlarmType.BYPASSVOLTAGE) { // UPS旁路电压
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
					if(StateEnum.valueOf(byPassVoltageMap.get("runState").toString())!=StateEnum.good){
						alarmMapList.add(byPassVoltageMap);
					}
				}
			}
			if (alarmRule.getAlarmType() == AlarmType.INPUTVOLTAGE) {
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
					if(StateEnum.valueOf(inputVoltageMap.get("runState").toString())!=StateEnum.good){
						alarmMapList.add(inputVoltageMap);
					}
				}
			}
			if (alarmRule.getAlarmType() == AlarmType.OUTPUTVOLTAGE) {
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
					if(StateEnum.valueOf(outputVoltageMap.get("runState").toString())!=StateEnum.good){
						alarmMapList.add(outputVoltageMap);
					}
				}
			}
			// 机内温度规则
			if (alarmRule.getAlarmType() == AlarmType.INTERNALTEMPERATURE) {
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
				if(StateEnum.valueOf(internalTemperatureMap.get("runState").toString())!=StateEnum.good){
					alarmMapList.add(internalTemperatureMap);
				}
			}
			// 电池电压
			if (alarmRule.getAlarmType() == AlarmType.BATTERYVOLTAGE) {
				Map<String, Object> batteryVoltageMap = checkBatteryVoltage(
						upsStatus, alarmRule);
				if (runState.ordinal() < StateEnum.valueOf(
						batteryVoltageMap.get("runState").toString()).ordinal()) {
					runState = StateEnum.valueOf(batteryVoltageMap.get(
							"runState").toString());
				}
				alarmContent.append(batteryVoltageMap.get("alarmContent")
						.toString());
				if(StateEnum.valueOf(batteryVoltageMap.get("runState").toString())!=StateEnum.good){
					alarmMapList.add(batteryVoltageMap);
				}
			}
			if (upsStatus.getUpsType() != 2 && upsStatus.getUpsType() != 3) {
				// 电池电量
				if (alarmRule.getAlarmType() == AlarmType.ELECTRICQUANTITY) {
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
					if(StateEnum.valueOf(electricQuantityMap.get("runState").toString())!=StateEnum.good){
						alarmMapList.add(electricQuantityMap);
					}
				}
			}
			// 负载
			if (alarmRule.getAlarmType() == AlarmType.LOAD) {
				Map<String, Object> loadMap = checkLoad(
						upsStatus, alarmRule);
				if (runState.ordinal() < StateEnum.valueOf(
						loadMap.get("runState").toString()).ordinal()) {
					runState = StateEnum.valueOf(loadMap.get(
							"runState").toString());
				}
				alarmContent.append(loadMap.get("alarmContent")
						.toString());
				if(StateEnum.valueOf(loadMap.get("runState").toString())!=StateEnum.good){
					alarmMapList.add(loadMap);
				}
			}

		}
		if(alarmContent.indexOf("、")>0){
			alarmContent=new StringBuffer(alarmContent.substring(0,alarmContent.length()-1));
		}
		alarmResultMap.put("runState", runState);
		alarmResultMap.put("alarmContent", alarmContent.toString());
		alarmResultMap.put("alarmMapList", alarmMapList);

		return alarmResultMap;
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
				value, minValue, maxValue,alarmRule.getRuleAlarmType());
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
						byPassVoltage_2, value, minValue, maxValue,alarmRule.getRuleAlarmType());
				runState = alarmRuleService.ruleCompare(operationType,
						byPassVoltage_3, value, minValue, maxValue,alarmRule.getRuleAlarmType());
			}

			runState = alarmRuleService.ruleCompare(operationType,
					byPassVoltage_1, value, minValue, maxValue,alarmRule.getRuleAlarmType());
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
						inputVoltage_2, value, minValue, maxValue,alarmRule.getRuleAlarmType());
				runState = alarmRuleService.ruleCompare(operationType,
						inputVoltage_3, value, minValue, maxValue,alarmRule.getRuleAlarmType());
			}

			runState = alarmRuleService.ruleCompare(operationType,
					inputVoltage_1, value, minValue, maxValue,alarmRule.getRuleAlarmType());
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
						outputVoltage_2, value, minValue, maxValue,alarmRule.getRuleAlarmType());
				runState = alarmRuleService.ruleCompare(operationType,
						outputVoltage_3, value, minValue, maxValue,alarmRule.getRuleAlarmType());
			}

			runState = alarmRuleService.ruleCompare(operationType,
					outputVoltage_1, value, minValue, maxValue,alarmRule.getRuleAlarmType());
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
				upsStatus.getInternalTemperature(), value, minValue, maxValue,alarmRule.getRuleAlarmType());
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
				minValue, maxValue,alarmRule.getRuleAlarmType());
		if (runState != StateEnum.good) {
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
				upsStatus.getElectricQuantity(), value, minValue, maxValue,alarmRule.getRuleAlarmType());
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
						upsLoad_2, value, minValue, maxValue,alarmRule.getRuleAlarmType());
				runState = alarmRuleService.ruleCompare(operationType,
						upsLoad_3, value, minValue, maxValue,alarmRule.getRuleAlarmType());
			}

			runState = alarmRuleService.ruleCompare(operationType,
					upsLoad_1, value, minValue, maxValue,alarmRule.getRuleAlarmType());
		}
		if (runState != StateEnum.good) {
			alarmContent.append("").append(alarmRule.getRemark()).append("、");
		}
		
		upsLoadMap.put("runState", runState);
		upsLoadMap.put("alarmContent", alarmContent.toString());
		return upsLoadMap;
	}

	@Override
	public List<UpsStatus> findLatestDataByGitInfoId(Integer gitInfoId) {
		return upsStatusDao.findLatestDataByGitInfoId(gitInfoId);

	}

	@Override
	public Page<UpsStatus> searchByGitInfoIdAndDeviceTypeAndIndex(
			Page<UpsStatus> page, Integer gitInfoId, DeviceType ups,
			Integer index, String startDate, String endDate) {
		StringBuffer hql = new StringBuffer(
				"from UpsStatus where status=0 and device.id in (select id from Device where status=0 and deviceType=")
				.append(ups.ordinal());
		if (gitInfoId != null) {
			hql.append(" and gitInfo.id=").append(gitInfoId);
		}
		if (index != null) {
			hql.append(" and deviceIndex=").append(index);
		}

		hql.append(")");
		if (startDate != null) {
			hql.append(" and date_format(collectTime,'%Y-%m-%d')>='")
					.append(startDate).append("'");
		}
		if (endDate != null) {
			hql.append(" and date_format(collectTime,'%Y-%m-%d')<='")
					.append(endDate).append("'");
		}
		hql.append(" order by id desc");
		return upsStatusDao.findPage(page, hql.toString());
	}

	public List<UpsStatus> getListByGitInfoIdAndDeviceTypeAndIndex(
			Integer gitInfoId, DeviceType ups, Integer index, String startDate,
			String endDate) {
		StringBuffer hql = new StringBuffer(
				"from UpsStatus where status=0 and device.id in (select id from Device where status=0 and deviceType=")
				.append(ups.ordinal());
		if (gitInfoId != null) {
			hql.append(" and gitInfo.id=").append(gitInfoId);
		}
		if (index != null) {
			hql.append(" and deviceIndex=").append(index);
		}

		hql.append(")");
		if (startDate != null) {
			hql.append(" and date_format(collectTime,'%Y-%m-%d')>='")
					.append(startDate).append("'");
		}
		if (endDate != null) {
			hql.append(" and date_format(collectTime,'%Y-%m-%d')<='")
					.append(endDate).append("'");
		}
		hql.append(" order by id desc");
		return upsStatusDao.find(hql.toString());

	}

	@Override
	public void updateStatusByIds(String ids) {
		upsStatusDao.updateStatusByIds(ids);

	}

	@Override
	public List<UpsChartBean> getListByDeviceId(Integer deviceId,
			Date beginDate, Date endDate) {
		// TODO Auto-generated method stub

		return upsStatusDao.getUpsChartListByDeviceId(deviceId, beginDate,
				endDate);
	}

	@Override
	public List<UpsChartBean> getUpsChartBeanList(Integer deviceId,
			Date beginDate, Date endDate) {
		List<UpsChartBean> upsChartBeans = new ArrayList<UpsChartBean>();
		List<UpsChartBean> list = upsStatusDao.getUpsChartListByDeviceId(
				deviceId, beginDate, endDate);
		Integer upsType = null;
		if (list.size() > 0) {
			upsType = list.get(0).getUpsType();
		}
		for (UpsChartBean upsChartBean : list) {
			UpsChartBean u = new UpsChartBean();
			if (upsType != 2 && upsType != 3) {
				u.setOutputVoltage(upsChartBean.getOutputVoltage().substring(0,
						upsChartBean.getOutputVoltage().indexOf("/")));
				u.setInputVoltage(upsChartBean.getInputVoltage().substring(0,
						upsChartBean.getInputVoltage().indexOf("/")));
				u.setUpsLoad(upsChartBean.getUpsLoad().substring(0,
						upsChartBean.getUpsLoad().indexOf("/")));
				u.setBatteryVoltage(upsChartBean.getBatteryVoltage());
				u.setCollectTime(upsChartBean.getCollectTime());
			}
			upsChartBeans.add(u);

		}

		if (upsType != null && upsType != 2 && upsType != 3) {
			return upsChartBeans;
		} else {
			return list;
		}
	}

}
