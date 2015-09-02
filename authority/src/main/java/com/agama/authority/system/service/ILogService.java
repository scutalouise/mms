package com.agama.authority.system.service;

import java.util.List;

import com.agama.authority.system.entity.Log;
import com.agama.common.service.IBaseService;

/**
 * @Description:日志service接口
 * @Author:scuta
 * @Since :2015年8月27日 上午10:01:01
 */
public interface ILogService extends IBaseService<Log, Integer> {

	/**
	 * 批量删除日志
	 * 
	 * @param idList
	 */
	public void deleteLog(List<Integer> idList);

}
