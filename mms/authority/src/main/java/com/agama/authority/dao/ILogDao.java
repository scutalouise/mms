package com.agama.authority.dao;

import java.util.List;

import com.agama.authority.entity.Log;
import com.agama.common.dao.IBaseDao;

/**
 * @Description:日志DAO
 * @Author:scuta
 * @Since :2015年8月27日 上午9:26:49
 */
public interface ILogDao extends IBaseDao<Log, Integer>{
	
	/**
	 * 批量删除日志
	 * @param ids 日志id列表
	 */
	public void deleteBatch(List<Integer> idList);
}
