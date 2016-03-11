package com.agama.itam.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.agama.common.dao.impl.HibernateDaoImpl;
import com.agama.common.enumbean.EnabledStateEnum;
import com.agama.common.enumbean.StatusEnum;
import com.agama.itam.dao.IKnowledgeClassificationDao;
import com.agama.itam.domain.KnowledgeClassification;

/**
 * @Description:知识库分类Dao层实现；
 * @Author:杨远高
 * @Since :2016年3月7日 下午4:38:22
 */
@Repository("knowledgeClassificationDao")
public class KnowledgeClassificationDaoImpl extends HibernateDaoImpl<KnowledgeClassification, Integer> implements IKnowledgeClassificationDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<KnowledgeClassification> findListByPid(Integer pid) {
		StringBuffer hql=new StringBuffer("from KnowledgeClassification where 1=1");
		hql.append(" and enable='" +EnabledStateEnum.ENABLED + "' and status='" + StatusEnum.NORMAL + "' ");//增加对可用性与状态的判断；
		if(pid!=null){
			hql.append(" and pid=").append(pid);
		}else{
			hql.append(" and pid is null");
		}
		hql.append(" order by code asc");
		return getSession().createQuery(hql.toString()).list();
	}
	
	
}
