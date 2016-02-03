package com.agama.pemm.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agama.common.dao.utils.Page;
import com.agama.common.domain.StateEnum;
import com.agama.common.enumbean.AlarmType;
import com.agama.common.enumbean.DeviceInterfaceType;
import com.agama.common.enumbean.DeviceType;
import com.agama.common.enumbean.OperationType;
import com.agama.common.service.impl.BaseServiceImpl;
import com.agama.pemm.bean.ThChartBean;
import com.agama.pemm.dao.IAlarmRuleDao;
import com.agama.pemm.dao.IThStatusDao;
import com.agama.pemm.domain.AlarmRule;
import com.agama.pemm.domain.ThStatus;
import com.agama.pemm.service.IAlarmRuleService;
import com.agama.pemm.service.IThStatusService;

/**
 * @Description:温湿度状态业务逻辑处理
 * @Author:ranjunfeng
 * @Since :2015年10月12日 上午11:21:43
 */
@Service
public class ThStatusServiceImpl extends BaseServiceImpl<ThStatus, Integer>
		implements IThStatusService {
	@Autowired
	private IThStatusDao thStatusDao;
	@Autowired
	private IAlarmRuleDao alarmRuleDao;
	@Autowired
	private IAlarmRuleService alarmRuleService;

	@Override
	public List<ThStatus> findLatestDataByGitInfoId(Integer gitInfoId) {
		return thStatusDao.findLatestDataByGitInfoId(gitInfoId);
	}

	@Override
	public Map<String, Object> checkTemperature(ThStatus thStatus,
			AlarmRule alarmRule) {
		Map<String, Object> temperatureMap = new HashMap<String, Object>();
		StateEnum runState = StateEnum.good;
		StringBuffer alarmContent = new StringBuffer();
		OperationType operationType = alarmRule.getOperationType();
		Integer value = alarmRule.getValue();
		Integer minValue = alarmRule.getMinValue();
		Integer maxValue = alarmRule.getMaxValue();
		runState = alarmRuleService.ruleCompare(operationType,
				Double.parseDouble(thStatus.getTemperature() + ""), value,
				minValue, maxValue,alarmRule.getRuleAlarmType());
		if (runState != StateEnum.good) {
			alarmContent.append("").append(alarmRule.getRemark()).append("、");
		}
		temperatureMap.put("runState", runState);
		temperatureMap.put("alarmContent", alarmContent.toString());
		return temperatureMap;

	}

	@Override
	public Map<String, Object> checkHumidity(ThStatus thStatus,
			AlarmRule alarmRule) {
		Map<String, Object> humidityMap = new HashMap<String, Object>();
		StateEnum runState = StateEnum.good;
		StringBuffer alarmContent = new StringBuffer();
		OperationType operationType = alarmRule.getOperationType();
		Integer value = alarmRule.getValue();
		Integer minValue = alarmRule.getMinValue();
		Integer maxValue = alarmRule.getMaxValue();
		runState = alarmRuleService.ruleCompare(operationType,
				Double.parseDouble(thStatus.getHumidity() + ""), value,
				minValue, maxValue,alarmRule.getRuleAlarmType());
		if (runState != StateEnum.good) {
			alarmContent.append("").append(alarmRule.getRemark()).append("、");
		}
		humidityMap.put("runState", runState);
		humidityMap.put("alarmContent", alarmContent.toString());
		return humidityMap;

	}

	@Override
	public Map<String, Object> checkAlarm(ThStatus thStatus,
			Integer alarmConditionId) {
		Map<String, Object> alarmResultMap = new HashMap<String, Object>();
		StateEnum runState = StateEnum.good;
		StateEnum temperatureState=StateEnum.good; //温度状态
		StateEnum humidityState=StateEnum.good;   //湿度状态
		List<AlarmRule> alarmRules = alarmRuleDao
				.getListByConditionIdAndDeviceInterfaceType(alarmConditionId,
						DeviceInterfaceType.TH);
		List<Map<String,Object>> alarmMapList=new ArrayList<Map<String,Object>>();
		// 报警内容
		StringBuffer alarmContent = new StringBuffer();
		for (AlarmRule alarmRule : alarmRules) {
			// 温度
			if (alarmRule.getAlarmType() == AlarmType.TEMPERATURE) {
				Map<String, Object> temperatureMap = checkTemperature(thStatus,
						alarmRule);
				if (runState.ordinal() < StateEnum.valueOf(
						temperatureMap.get("runState").toString()).ordinal()) {
					runState = StateEnum.valueOf(temperatureMap.get("runState")
							.toString());
					
				}
				if(temperatureState.ordinal()<StateEnum.valueOf(
						temperatureMap.get("runState").toString()).ordinal()){
					temperatureState=StateEnum.valueOf(temperatureMap.get("runState").toString());
				}
				thStatus.setTemperatureState(temperatureState.toString());
				alarmContent.append(temperatureMap.get("alarmContent")
						.toString());
				if(StateEnum.valueOf(temperatureMap.get("runState").toString())!=StateEnum.good){
					alarmMapList.add(temperatureMap);
				}
			}
			if (alarmRule.getAlarmType() == AlarmType.HUMIDITY) {
				Map<String, Object> humidityMap = checkHumidity(thStatus,
						alarmRule);
				if (runState.ordinal() < StateEnum.valueOf(
						humidityMap.get("runState").toString()).ordinal()) {
					runState = StateEnum.valueOf(humidityMap.get("runState")
							.toString());
				
				}
				if(humidityState.ordinal()<StateEnum.valueOf(
						humidityMap.get("runState").toString()).ordinal()){
					humidityState=StateEnum.valueOf(humidityMap.get("runState").toString());
				}
				
				thStatus.setHumidityState(humidityState.toString());
				alarmContent.append(humidityMap.get("alarmContent").toString());
				if(StateEnum.valueOf(humidityMap.get("runState").toString())!=StateEnum.good){
					alarmMapList.add(humidityMap);
				}
			}

		}
		if(alarmContent.indexOf("、")>0){
			alarmContent=new StringBuffer(alarmContent.substring(0,alarmContent.length()-1));
		}
		thStatusDao.update(thStatus);
		alarmResultMap.put("runState", runState);
		alarmResultMap.put("alarmContent", alarmContent.toString());
		alarmResultMap.put("alarmMapList", alarmMapList);
		return alarmResultMap;
	}

	@Override
	public List<ThChartBean> getListByDeviceId(Integer deviceId,
			Date beginDate, Date endDate) {

		return thStatusDao.getListByDeviceId(deviceId, beginDate, endDate);
	}

	@Override
	public Page<ThStatus> searchByGitInfoIdAndDeviceTypeAndIndex(
			Page<ThStatus> page, Integer gitInfoId, DeviceType th,
			Integer index, String startDate, String endDate) {
		StringBuffer hql = new StringBuffer(
				"from ThStatus where status=0 and deviceId in (select id from Device where status=0 and deviceType=")
				.append(th.ordinal());
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
		return thStatusDao.findPage(page, hql.toString());
	}

	@Override
	public List<ThStatus> getListByGitInfoIdAndDeviceTypeAndIndex(
			Integer gitInfoId, DeviceType th, Integer index, String startDate,
			String endDate) {
		StringBuffer hql = new StringBuffer(
				"from ThStatus where status=0 and deviceId in (select id from Device where status=0 and deviceType=")
				.append(th.ordinal());
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
		return thStatusDao.find(hql.toString());
	}

}
