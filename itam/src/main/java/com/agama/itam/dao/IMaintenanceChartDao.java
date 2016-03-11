package com.agama.itam.dao;

import javax.servlet.http.HttpServletRequest;

import com.agama.common.dao.IBaseDao;
import com.agama.common.dao.utils.Page;
import com.agama.itam.bean.DeviceMaintenanceChartBean;

public interface IMaintenanceChartDao extends IBaseDao<DeviceMaintenanceChartBean, Integer>{
	
	public Page<DeviceMaintenanceChartBean> findPageBySQL(Page<DeviceMaintenanceChartBean> page,HttpServletRequest request);
}
