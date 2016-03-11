package com.agama.itam.service;

import javax.servlet.http.HttpServletRequest;

import com.agama.common.dao.utils.Page;
import com.agama.common.service.IBaseService;
import com.agama.itam.bean.DeviceBelongChartBean;

public interface IBelongChartService extends IBaseService<DeviceBelongChartBean, Integer>{
	
	/**
	 * @Description 通过 设备名称，设备类型，所属机构，采购日期（开始），采购日期（截止）查找设备归属详情
	 * @return
	 * @Since 2016年2月26日 上午9:32:58
	 */
	public Page<DeviceBelongChartBean> findPageBySQL(Page<DeviceBelongChartBean> page,HttpServletRequest request);
}
