package com.agama.itam.dao.impl;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import com.agama.common.dao.impl.HibernateDaoImpl;
import com.agama.itam.dao.IKnowledgeDetailsDao;
import com.agama.itam.domain.KnowledgeDetails;

/**
 * @Description:知识库Dao层实现；
 * @Author:杨远高
 * @Since :2016年3月7日 下午4:38:22
 */
@Repository("knowledgeDetailsDao")
public class KnowledgeDetailsDaoImpl extends HibernateDaoImpl<KnowledgeDetails, Serializable> implements IKnowledgeDetailsDao {
	
	
}
