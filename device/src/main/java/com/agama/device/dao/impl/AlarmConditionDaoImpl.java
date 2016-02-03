package com.agama.device.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.agama.common.dao.impl.HibernateDaoImpl;
import com.agama.common.dao.utils.Page;
import com.agama.common.enumbean.AlarmDeviceType;
import com.agama.common.enumbean.AlarmOptionType;
import com.agama.common.enumbean.DeviceInterfaceType;
import com.agama.common.enumbean.StatusEnum;
import com.agama.device.dao.IAlarmConditionDao;
import com.agama.device.domain.AlarmCondition;
/**
 * @Description:告警条件数据访问层实现类
 * @Author:ranjunfeng
 * @Since :2015年12月25日 下午4:37:54
 */
@Repository
public class AlarmConditionDaoImpl extends
		HibernateDaoImpl<AlarmCondition, Integer> implements IAlarmConditionDao {

	@Override
	public void updateStatusByIds(String ids) {
		StringBuffer hql=new StringBuffer("update AlarmCondition set status=").append(StatusEnum.DELETED.getId()).append(" where id in (").append(ids).append(")");
		this.getSession().createQuery(hql.toString()).executeUpdate();
		
	}

	@Override
	public List<AlarmCondition> getListByTemplateId(Integer alarmTemplateId,AlarmDeviceType alarmDeviceType,AlarmOptionType alarmOptionType) {
		StringBuffer hql=new StringBuffer("from AlarmCondition a where  a.status=").append(StatusEnum.NORMAL.getId());
		if(alarmTemplateId!=null){
			hql.append(" and a.alarmTemplate.id=").append(alarmTemplateId);
		}
		hql.append(" and a.alarmOptionType='").append(alarmOptionType).append("'");
		hql.append(" and a.alarmDeviceType=").append(alarmDeviceType.ordinal());
		return this.find(hql.toString());
	}
	@Override
	public Page<AlarmCondition> searchByAlarmTemplateIdAndStatus(
			Page<AlarmCondition> page, Integer alarmTemplateId,
			StatusEnum status) {
		StringBuffer hql=new StringBuffer("from AlarmCondition where status=").append(status.getId()).append(" and alarmTemplate.id=").append(alarmTemplateId);
		
		return this.findPage(page, hql.toString());
	}
}
