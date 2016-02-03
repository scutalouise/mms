package com.agama.itam.dao;

import java.io.Serializable;
import java.util.List;

import com.agama.common.dao.IBaseDao;
import com.agama.common.dao.utils.Page;
import com.agama.itam.domain.Problem;

/**
 * @Description:问题记录DAO接口
 * @Author:佘朝军
 * @Since :2016年1月19日 下午2:49:34
 */
public interface IProblemDao extends IBaseDao<Problem, Serializable> {

	public List<Problem> getAllList() throws Exception;
	
	public Page<Problem> searchListForHandling(String identifier, Integer problemTypeId, Integer enable, String recordTime, String recordEndTime,
			Page<Problem> page) throws Exception;

}
