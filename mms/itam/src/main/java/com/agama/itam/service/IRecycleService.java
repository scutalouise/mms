package com.agama.itam.service;

import java.io.Serializable;

import com.agama.common.dao.utils.Page;
import com.agama.common.service.IBaseService;
import com.agama.itam.domain.Recycle;

/**
 * @Description:回收站Service层接口
 * @Author:杨远高
 * @Since :2016年1月29日 上午10:40:42
 */
public interface IRecycleService extends IBaseService<Recycle, Serializable> {
	
	public Page<Recycle> findPageBySQL(Page<Recycle> page, String sql, Object... values);
	
}
