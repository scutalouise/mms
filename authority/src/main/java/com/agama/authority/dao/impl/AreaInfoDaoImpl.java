package com.agama.authority.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.agama.authority.dao.IAreaInfoDao;
import com.agama.authority.entity.AreaInfo;
import com.agama.common.dao.impl.HibernateDaoImpl;
import com.agama.common.domain.StateEnum;
import com.agama.common.enumbean.EnabledStateEnum;
import com.agama.common.enumbean.StatusEnum;

/**
 * @Description:区域Dao实现
 * @Author:scuta
 * @Since :2015年8月27日 上午9:31:16
 */
@Repository("areaInfoDao")
public class AreaInfoDaoImpl extends HibernateDaoImpl<AreaInfo, Integer> implements IAreaInfoDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<AreaInfo> findListByPid(Integer pid) {
		StringBuffer hql=new StringBuffer("from AreaInfo where 1=1");
		hql.append(" and enable='" +EnabledStateEnum.ENABLED + "' and status='" + StatusEnum.NORMAL + "' ");//增加对可用性与状态的判断；
		if(pid!=null){
			hql.append(" and pid=").append(pid);
		}else{
			hql.append(" and pid is null");
		}
		hql.append(" order by areaCode asc");
		return getSession().createQuery(hql.toString()).list();
	}

	@Override
	public List<AreaInfo> getListRelevancyOrganization(StateEnum stateEnum,String searchValue) {
		StringBuffer hql=new StringBuffer("select a from AreaInfo a,Organization o,GitInfo g,Device d where a.id=o.areaId and o.id=g.organizationId and g.id=d.gitInfo.id and d.status=0 and g.status=0");
		if(searchValue!=null){
			hql.append(" and (a.areaName like '%").append(searchValue).append("%'");
			hql.append(" or o.orgName like '%").append(searchValue).append("%'");
			hql.append(")");
		}
		
		hql.append(" group by a.id");
		//String hql="select a  from AreaInfo a, Organization o where a.id=o.areaId group by a.id";
		return this.find(hql.toString());
	}

	

}
