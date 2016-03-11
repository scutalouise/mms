package com.agama.device.dao.impl;

import org.springframework.stereotype.Repository;

import com.agama.common.dao.impl.HibernateDaoImpl;
import com.agama.common.enumbean.StatusEnum;
import com.agama.device.dao.IAlarmTemplateDao;
import com.agama.device.domain.AlarmTemplate;
import com.agama.device.domain.OrgAlarmTemplate;
/**
 * @Description:告警模板数据访问层实现类
 * @Author:ranjunfeng
 * @Since :2015年12月25日 下午4:35:32
 */
@Repository
public class AlarmTemplateDaoImpl extends
		HibernateDaoImpl<AlarmTemplate, Integer> implements IAlarmTemplateDao {

	@Override
	public void updateStatusByIds(String ids) {
		StringBuffer hql=new StringBuffer("update AlarmTemplate set status="+StatusEnum.DELETED.getId()+" where id in (").append(ids).append(")");
		this.getSession().createQuery(hql.toString()).executeUpdate();
		
	}

	

}
