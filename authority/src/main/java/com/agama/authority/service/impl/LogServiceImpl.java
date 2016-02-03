package com.agama.authority.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agama.authority.dao.ILogDao;
import com.agama.authority.entity.Log;
import com.agama.authority.service.ILogService;
import com.agama.common.service.impl.BaseServiceImpl;

/**
 * @Description:日志service实现
 * @Author:scuta
 * @Since :2015年8月27日 上午10:02:08
 */
@Service("logService")
@Transactional(readOnly=true)
public class LogServiceImpl extends BaseServiceImpl<Log, Integer> implements ILogService{
	
	@Autowired
	private ILogDao logDao;
	
	/**
	 * 批量删除日志
	 * @param idList
	 */
	@Transactional(readOnly=false)
	public void deleteLog(List<Integer> idList){
		logDao.deleteBatch(idList);
	}
	
}
