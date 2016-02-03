package com.agama.device.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.agama.common.dao.impl.HibernateDaoImpl;
import com.agama.common.dao.utils.Page;
import com.agama.common.enumbean.AlarmOptionType;
import com.agama.common.enumbean.AlarmRuleType;
import com.agama.common.enumbean.DeviceInterfaceType;
import com.agama.common.enumbean.StatusEnum;
import com.agama.device.dao.IAlarmRuleDao;
import com.agama.device.domain.AlarmRule;
import org.apache.commons.lang3.StringUtils;

/**
 * @Description:报警规则数据访问层实现类
 * @Author:ranjunfeng
 * @Since :2015年12月25日 下午4:36:11
 */
@Repository
public class AlarmRuleDaoImpl extends HibernateDaoImpl<AlarmRule, Integer>
		implements IAlarmRuleDao {

	@Override
	public void updateStatusByIds(String ids) {
		StringBuffer hql=new StringBuffer("update AlarmRule set status=").append(StatusEnum.DELETED.getId());
		if(ids!=null){
			hql.append(" where id in (").append(ids).append(")");
			
		}
		this.getSession().createQuery(hql.toString()).executeUpdate();
	}
	@Override
	public List<AlarmRule> getListByConditionIdAndAlarmOptionType(Integer alarmConditionId, AlarmOptionType alarmOptionType) {
		StringBuffer hql=new StringBuffer("from AlarmRule a where a.status=").append(StatusEnum.NORMAL.getId());
		
		if(alarmConditionId!=null){
			hql.append(" and a.alarmCondition.id=").append(alarmConditionId);
		}
		List<String> alarmRuleTypeForStrings=AlarmRuleType.getAlarmRuleTypeForStringByAlarmOptionType(alarmOptionType);
		
		hql.append(" and a.alarmRuleType in (").append(StringUtils.join(alarmRuleTypeForStrings,",")).append(")");
		
		return this.find(hql.toString());
	}
	@Override
	public Page<AlarmRule> searchByAlarmConditionIdAndStatus(
			Page<AlarmRule> page, Integer alarmConditionId, StatusEnum status) {
		StringBuffer hql=new StringBuffer("from AlarmRule where status=").append(status.getId()).append(" and alarmCondition.id=").append(alarmConditionId);
		
		return this.findPage(page, hql.toString());
	}

	

}
