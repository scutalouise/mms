package com.agama.itam.service.impl;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agama.common.dao.utils.Page;
import com.agama.common.service.impl.BaseServiceImpl;
import com.agama.itam.dao.IRecycleDao;
import com.agama.itam.domain.Recycle;
import com.agama.itam.service.IRecycleService;

/**
 * @Description:回收站实现类
 * @Author:杨远高
 * @Since :2016年1月29日 上午10:43:39
 */
@Transactional(readOnly = true)
@Service
public class RecycleServiceImpl extends BaseServiceImpl<Recycle, Serializable> implements IRecycleService {

	@Autowired
	private IRecycleDao recycleDaoImpl;

	@Override
	public Page<Recycle> findPageBySQL(Page<Recycle> page, String sql, Object... values) {
		return recycleDaoImpl.findPageBySQL(page, sql, values);
	}
	
}
