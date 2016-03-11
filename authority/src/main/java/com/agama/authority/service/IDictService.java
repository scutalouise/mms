package com.agama.authority.service;

import com.agama.authority.entity.Dict;
import com.agama.common.service.IBaseService;

/**
 * @Description:字典service
 * @Author:scuta
 * @Since :2015年8月27日 上午9:58:33
 */
public interface IDictService extends IBaseService<Dict, Integer> {

	/**
	 * @Description:提供支持回收站的逻辑删除操作；
	 * @param id
	 * @param opUserId
	 * @Since :2016年2月25日 下午5:36:23
	 */
	public void delete(Integer id, Integer opUserId);
}
