package com.agama.device.service.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agama.authority.dao.IOrganizationDao;
import com.agama.authority.entity.Organization;
import com.agama.common.domain.StateEnum;
import com.agama.common.enumbean.AlarmOptionUnitEnum;
import com.agama.common.enumbean.CountUnitEnum;
import com.agama.common.enumbean.DeviceAlarmOptionEnum;
import com.agama.common.enumbean.FirstDeviceType;
import com.agama.common.service.impl.BaseServiceImpl;
import com.agama.device.dao.IAlarmTemplateDao;
import com.agama.device.dao.IDeviceAlarmConditionDao;
import com.agama.device.dao.IPeDeviceDao;
import com.agama.device.dao.impl.DeviceAlarmConditionDaoImpl;
import com.agama.device.domain.AlarmTemplate;
import com.agama.device.domain.DeviceAlarmCondition;
import com.agama.device.domain.DeviceAlarmRule;
import com.agama.device.domain.OrgAlarmTemplate;
import com.agama.device.domain.PeDevice;
import com.agama.device.service.IAlarmRuleService;
import com.agama.device.service.IAlarmTemplateService;
import com.agama.device.service.IDeviceAlarmRuleService;

@Service
public class AlarmTemplateServiceImpl extends
		BaseServiceImpl<AlarmTemplate, Integer> implements
		IAlarmTemplateService {
	@Autowired
	private IAlarmTemplateDao alarmTemplateDao;
	@Autowired
	private IDeviceAlarmConditionDao deviceAlarmConditionDao;
	@Autowired
	private IPeDeviceDao peDeviceDao;
	@Autowired
	private IDeviceAlarmRuleService deviceAlarmRuleService;
	@Autowired
	private IOrganizationDao organizationDao;
	
	@Override
	public void updateStatusByIds(String ids) {
		alarmTemplateDao.updateStatusByIds(ids);
		
	}
	@Override
	public void alarmHandle(OrgAlarmTemplate orgAlarmTemplate) {
		Organization organization=organizationDao.find(orgAlarmTemplate.getOrgId());
		//获取业务告警规则
		List<DeviceAlarmCondition> deviceAlarmConditions=deviceAlarmConditionDao.findListByTemplateId(orgAlarmTemplate.getAlarmTemplateId());
		StringBuffer message=new StringBuffer("网点【").append(organization.getOrgName()).append("】出现如下问题：\n");
		StringBuffer content=new StringBuffer();
		//迭代告警条件
		for (DeviceAlarmCondition deviceAlarmCondition : deviceAlarmConditions) {
			
			for (DeviceAlarmRule deviceAlarmRule : deviceAlarmCondition.getDeviceAlarmRules()) {
				//库存数量
				if(deviceAlarmRule.getDeviceAlarmOption()==DeviceAlarmOptionEnum.INVENTORYNUM){
					content.append(deviceAlarmCondition.getAlarmDevice().getName());				
					Long inventoryTotalCount = 0L;
					Double inventoryCount = 0.0;
					Double compareValue=0.0;
					//动环设备
					if(deviceAlarmCondition.getAlarmDeviceType()==FirstDeviceType.PEDEVICE){
						inventoryCount = Double.parseDouble(peDeviceDao
								.getInventoryCountBySecondDeviceType(
										orgAlarmTemplate.getOrgId(),
										deviceAlarmCondition.getAlarmDevice())+"");
						inventoryTotalCount = peDeviceDao.getCountAll(
								orgAlarmTemplate.getOrgId(),
								deviceAlarmCondition.getAlarmDevice());
					}
					
					if (deviceAlarmRule.getAlarmOptionUnit() == AlarmOptionUnitEnum.NUMBER) {
						compareValue=inventoryCount;
					} else {
						if(inventoryTotalCount>0){
							compareValue=inventoryCount*100/inventoryTotalCount;
						}
					}
					StateEnum state=deviceAlarmRuleService.ruleCompare(deviceAlarmRule.getOperationType(), compareValue, deviceAlarmRule.getValue(), deviceAlarmRule.getMinValue(), deviceAlarmRule.getMaxValue(), deviceAlarmRule.getState());
					if(state!=StateEnum.good){
						content.append(deviceAlarmRule.getRemark()).append("\n");
					}
					
				}else if(deviceAlarmRule.getDeviceAlarmOption()==DeviceAlarmOptionEnum.MAINTENANCETIME){
					
				}else if(deviceAlarmRule.getDeviceAlarmOption()==DeviceAlarmOptionEnum.SCRAPPEDTIME){
					if(deviceAlarmCondition.getAlarmDeviceType()==FirstDeviceType.PEDEVICE){
						List<PeDevice> peDevices=peDeviceDao.find(Restrictions.eq("organizationId", orgAlarmTemplate.getOrgId()));
						for (PeDevice peDevice : peDevices) {
							//购买日期
							Date manufactureDate=peDevice.getManufactureDate();
							if(deviceAlarmRule.getAlarmOptionUnit()==AlarmOptionUnitEnum.SCRAPPEDTIME_OF_DAY){
								
							}
						}
					}
				}
			}
			
		}
		if(content!=null){
			System.out.println(message.append(content));
		}
		
		
	}
	
	
	

	
	

}
