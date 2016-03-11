package com.agama.itam.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agama.common.dao.utils.Page;
import com.agama.common.service.impl.BaseServiceImpl;
import com.agama.itam.bean.DeviceAccountsChartBean;
import com.agama.itam.dao.IAccountsChartDao;
import com.agama.itam.service.IAccountsChartService;

@Service
public class AccountsChartServiceImpl extends BaseServiceImpl<DeviceAccountsChartBean, Integer> implements IAccountsChartService{

	@Autowired
	private IAccountsChartDao accountsChartDao;
	
	@Override
	public Page<DeviceAccountsChartBean> findPageBySQL(Page<DeviceAccountsChartBean> page) {
		return accountsChartDao.findPageBySQL(page);
	}

}
