package com.agama.itam.dao;

import java.util.List;

import com.agama.common.dao.IBaseDao;
import com.agama.itam.domain.KnowledgeClassification;

/**
 * @Description:知识库分类
 * @Author:杨远高
 * @Since :2016年3月7日 下午4:37:17
 */
public interface IKnowledgeClassificationDao extends IBaseDao<KnowledgeClassification, Integer> {

	/**
	 * @Description:根据pid返回对应的pid及所有其下的数据；
	 * @param pid
	 * @return
	 * @Since :2016年3月7日 下午5:20:29
	 */
	public List<KnowledgeClassification> findListByPid(Integer pid);
}
