package com.agama.pemm.service;

import com.agama.common.dao.utils.Page;
import com.agama.common.service.IBaseService;
import com.agama.pemm.domain.DischargeLog;


/**
 * @Description:放电日志业务逻辑接口
 * @Author:ranjunfeng
 * @Since :2015年12月2日 上午9:53:44
 */
public interface IDischargeLogService extends IBaseService<DischargeLog, Integer> {
	/**
	 * @Description:根据状态分页查询所有的放电日志
	 * @param page
	 * @return
	 * @Since :2015年12月11日 下午3:07:06
	 */
	public Page<DischargeLog> searchList(Page<DischargeLog> page,Integer status);

	public void updateStatusByIds(String ids);

}
