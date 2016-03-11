package com.agama.itam.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agama.common.dao.utils.Page;
import com.agama.common.service.impl.BaseServiceImpl;
import com.agama.itam.bean.DeviceBelongChartBean;
import com.agama.itam.dao.IBelongChartDao;
import com.agama.itam.service.IBelongChartService;

@Service
public class BelongChartServiceImpl extends BaseServiceImpl<DeviceBelongChartBean, Integer> implements IBelongChartService{

	@Autowired
	private IBelongChartDao belongChartDao;

	@Override
	public Page<DeviceBelongChartBean> findPageBySQL(Page<DeviceBelongChartBean> page,HttpServletRequest request){
		return belongChartDao.findPageBySQL(page,request);
	}
	
}
