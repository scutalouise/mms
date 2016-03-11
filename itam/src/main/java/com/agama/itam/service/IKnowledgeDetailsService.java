package com.agama.itam.service;

import java.io.Serializable;

import com.agama.common.service.IBaseService;
import com.agama.itam.domain.KnowledgeDetails;

/**
 * @Description:
 * @Author:杨远高
 * @Since :2016年3月7日 下午4:43:10
 */
public interface IKnowledgeDetailsService extends IBaseService<KnowledgeDetails, Serializable> {
	
	/**
	 * @Description:提供支持回收站的逻辑删除功能；
	 * @param id
	 * @param userId
	 * @Since :2016年3月7日 下午6:22:27
	 */
	public void delete(Integer id, Integer userId);
	
}
