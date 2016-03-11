package com.agama.itam.service;

import com.agama.common.dao.utils.Page;
import com.agama.common.service.IBaseService;
import com.agama.itam.bean.DeviceAccountsChartBean;

public interface IAccountsChartService extends IBaseService<DeviceAccountsChartBean, Integer>{
	
	public Page<DeviceAccountsChartBean> findPageBySQL(Page<DeviceAccountsChartBean> page);

}
