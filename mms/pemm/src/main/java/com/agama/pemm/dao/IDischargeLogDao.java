package com.agama.pemm.dao;

import com.agama.common.dao.IBaseDao;
import com.agama.pemm.domain.DischargeLog;

/**
 * @Description:放电日志数据访问层接口
 * @Author:ranjunfeng
 * @Since :2015年12月2日 上午9:47:57
 */
public interface IDischargeLogDao extends IBaseDao<DischargeLog, Integer> {

	public void updateStatusByIds(String ids);

}
