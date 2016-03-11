package com.agama.itam.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agama.common.dao.utils.Page;
import com.agama.common.service.impl.BaseServiceImpl;
import com.agama.itam.bean.DeviceMaintenanceChartBean;
import com.agama.itam.dao.IMaintenanceChartDao;
import com.agama.itam.service.IMaintenanceChartService;

@Service
public class MaintenanceChartServiceImpl extends BaseServiceImpl<DeviceMaintenanceChartBean, Integer> implements IMaintenanceChartService{

	@Autowired
	private IMaintenanceChartDao maintenanceChartDao;
	
	@Override
	public Page<DeviceMaintenanceChartBean> findPageBySQL(Page<DeviceMaintenanceChartBean> page,
			HttpServletRequest request) {
		return  maintenanceChartDao.findPageBySQL(page,request);
   }
}
