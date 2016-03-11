package com.agama.itam.dao;

import com.agama.common.dao.IBaseDao;
import com.agama.common.dao.utils.Page;
import com.agama.itam.bean.DeviceAccountsChartBean;

public interface IAccountsChartDao extends IBaseDao<DeviceAccountsChartBean, Integer>{
	
	public Page<DeviceAccountsChartBean> findPageBySQL(Page<DeviceAccountsChartBean> page);

}
