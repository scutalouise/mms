package com.agama.itam.service;

import javax.servlet.http.HttpServletRequest;

import com.agama.common.dao.utils.Page;
import com.agama.common.service.IBaseService;
import com.agama.itam.bean.DeviceMaintenanceChartBean;

public interface IMaintenanceChartService extends IBaseService<DeviceMaintenanceChartBean, Integer>{

	public Page<DeviceMaintenanceChartBean> findPageBySQL(Page<DeviceMaintenanceChartBean> page,HttpServletRequest request);
	
}
