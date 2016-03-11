package com.agama.itam.service;

import java.util.List;

import com.agama.common.domain.TreeBean;
import com.agama.common.service.IBaseService;
import com.agama.itam.domain.KnowledgeClassification;

/**
 * @Description:
 * @Author:杨远高
 * @Since :2016年3月7日 下午4:43:10
 */
public interface IKnowledgeClassificationService extends IBaseService<KnowledgeClassification, Integer> {

	/**
	 * @Description:重写删除操作，提供支持回收站的逻辑删除；
	 * @param id
	 * @param userId
	 * @Since :2016年3月7日 下午5:10:16
	 */
	public void delete(Integer id, Integer userId);
	
	/**
	 * @Description:根据pid返回树结构
	 * @param pid
	 * @return
	 * @Since :2016年3月7日 下午5:12:03
	 */
	public List<TreeBean> getTreeByPid(Integer pid);
}
