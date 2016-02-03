package com.agama.pemm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agama.common.dao.utils.Page;
import com.agama.common.service.impl.BaseServiceImpl;
import com.agama.pemm.dao.IDischargeLogDao;
import com.agama.pemm.domain.DischargeLog;
import com.agama.pemm.service.IDischargeLogService;

/**
 * @Description:放电日志数据访问层实现类
 * @Author:ranjunfeng
 * @Since :2015年12月2日 上午9:57:33
 */
@Service
public class DischargeLogServiceImpl extends
		BaseServiceImpl<DischargeLog, Integer> implements IDischargeLogService {
	@Autowired
	private IDischargeLogDao dischargeLogDao;
	@Override
	public Page<DischargeLog> searchList(Page<DischargeLog> page, Integer status) {
		StringBuffer hql=new StringBuffer("from DischargeLog where status=").append(status).append(" order by dischargeDate desc");
		return dischargeLogDao.findPage(page, hql.toString());
	}
	@Override
	public void updateStatusByIds(String ids) {
		dischargeLogDao.updateStatusByIds(ids);
		
	}

}
