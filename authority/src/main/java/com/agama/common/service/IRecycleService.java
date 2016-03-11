package com.agama.common.service;

import java.io.Serializable;
import java.util.List;

import com.agama.common.dao.utils.Page;
import com.agama.common.dao.utils.PropertyFilter;
import com.agama.common.entity.Recycle;

/**
 * @Description:回收站Service层接口
 * @Author:杨远高
 * @Since :2016年1月29日 上午10:40:42
 */
public interface IRecycleService extends IBaseService<Recycle, Serializable> {
	
	/**
	 * @Description:根据sql查找对象；
	 * @param page
	 * @param propertyFilters
	 * @return
	 * @Since :2016年3月9日 下午1:47:09
	 */
	public Page<Recycle> findPage(Page<Recycle> page, List<PropertyFilter> propertyFilters);
	
	/**
	 * @Description:执行数据的还原；返回执行了还原操作的数据记录数；
	 * @param id
	 * @param opUserId
	 * @return
	 * @Since :2016年3月9日 下午2:58:47
	 */
	public int execRecycle(Integer id,Integer opUserId);
	
}
