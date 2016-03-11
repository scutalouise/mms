package com.agama.itam.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agama.common.enumbean.CountUnitEnum;
import com.agama.common.enumbean.DeviceAlarmOptionEnum;
import com.agama.common.enumbean.FirstDeviceType;
import com.agama.device.dao.IAlarmTemplateDao;
import com.agama.device.dao.IDeviceAlarmConditionDao;
import com.agama.device.dao.IDeviceAlarmRuleDao;
import com.agama.device.dao.IHostDeviceDao;
import com.agama.device.dao.IOrgAlarmTemplateDao;
import com.agama.device.dao.IPeDeviceDao;
import com.agama.device.domain.DeviceAlarmCondition;
import com.agama.device.domain.DeviceAlarmRule;
import com.agama.device.domain.OrgAlarmTemplate;
import com.agama.device.service.IAlarmTemplateService;
import com.agama.itam.service.IDeviceAlarmHandleService;

/**
 * @Description:设备告警处理业务类
 * @Author:ranjunfeng
 * @Since :2016年3月8日 上午11:16:31
 */
@Service
@Transactional
public class DeviceAlarmHandleServiceImpl implements IDeviceAlarmHandleService {
	@Autowired
	private IOrgAlarmTemplateDao orgAlarmTemplateDao;
	@Autowired
	private IDeviceAlarmRuleDao deviceAlarmRuleDao;
	@Autowired
	private IDeviceAlarmConditionDao deviceAlarmConditionDao;
	@Autowired
	private IPeDeviceDao peDeviceDao;
	@Autowired
	private IHostDeviceDao hostDeviceDao;
	@Autowired
	private IAlarmTemplateService alarmTemplateService;

	/**
	 * 业务告警接口
	 */
	@Scheduled(fixedRateString = "${interval}")
	public void deviceAlarmHandle() {
	
		List<OrgAlarmTemplate> orgAlarmTemplates = orgAlarmTemplateDao
				.findAll();
		for (OrgAlarmTemplate orgAlarmTemplate : orgAlarmTemplates) {
			alarmTemplateService.alarmHandle(orgAlarmTemplate);
//			StringBuffer alarmContent = new StringBuffer();
//			deviceAlarmHandleService.deviceAlarmHandle(orgAlarmTemplate);
//			//获取业务告警规则
//			List<DeviceAlarmCondition> deviceAlarmConditions=deviceAlarmConditionDao.findListByTemplateId(orgAlarmTemplate.getAlarmTemplateId());
//			//迭代告警条件
//			for (DeviceAlarmCondition deviceAlarmCondition : deviceAlarmConditions) {
//				for (DeviceAlarmRule deviceAlarmRule : deviceAlarmCondition.getDeviceAlarmRules()) {
//					//库存数量
//					if(deviceAlarmRule.getDeviceAlarmOption()==DeviceAlarmOptionEnum.INVENTORYNUM){
//						
//					}
//				}
//				
//			}
			// 获取模板下面的告警规则
			/*List<DeviceAlarmRule> deviceAlarmRules = deviceAlarmRuleDao
					.findListByTemplateId(orgAlarmTemplate.getAlarmTemplateId());
			for (DeviceAlarmRule deviceAlarmRule : deviceAlarmRules) {*/
				// 库存数量单位，如果是数字

				/*if (deviceAlarmRule.getAlarmDeviceType() == FirstDeviceType.HOSTDEVICE) {

				} else if (deviceAlarmRule.getAlarmDeviceType() == FirstDeviceType.NETWORKDEVICE) {

				} else if (deviceAlarmRule.getAlarmDeviceType() == FirstDeviceType.UNINTELLIGENTDEVICE) {

				} else if (deviceAlarmRule.getAlarmDeviceType() == FirstDeviceType.COLLECTDEVICE) {

				} else if (deviceAlarmRule.getAlarmDeviceType() == FirstDeviceType.PEDEVICE) {

					Long inventoryTotalCount = 0L;
					Long inventoryCount = 0L;
					inventoryCount = peDeviceDao
							.getInventoryCountBySecondDeviceType(
									orgAlarmTemplate.getOrgId(),
									deviceAlarmRule.getAlarmDevice());
					inventoryTotalCount = peDeviceDao.getCountAll(
							orgAlarmTemplate.getOrgId(),
							deviceAlarmRule.getAlarmDevice());
					if (deviceAlarmRule.getInventoryCountUnit() == CountUnitEnum.NUMBER) {
						if (deviceAlarmRule.getInventoryCount() < inventoryCount) {
							alarmContent.append(
									deviceAlarmRule.getAlarmDevice().getName())
									.append("库存过少");
						}
					} else {
						if (deviceAlarmRule.getInventoryCount() < (inventoryCount * 100 / inventoryTotalCount)) {

							alarmContent.append(
									deviceAlarmRule.getAlarmDevice().getName())
									.append("库存过少");

						}
					}
				}*/
//				System.out.println(alarmContent.toString());
				// if(deviceAlarmRule.getInventoryCount()<inventoryCount){
				// alarmContent.append(deviceAlarmRule.getAlarmDevice().getName()).append("库存过少");
				// }

//			}
		}

	}
}
