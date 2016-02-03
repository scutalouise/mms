package com.agama.pemm.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;



import com.agama.common.dao.utils.Page;
import com.agama.common.web.BaseController;
import com.agama.common.enumbean.DeviceType;
import com.agama.pemm.domain.ThStatus;
import com.agama.pemm.domain.UpsStatus;
import com.agama.pemm.service.IThStatusService;
@Controller
@RequestMapping("system/thStatus")
public class ThStatusController extends BaseController{
	@Autowired
	private IThStatusService thStatusService;
	@RequestMapping(value="thStatusList",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getThStatus(HttpServletRequest request) {
		
		Page<ThStatus> page = getPage(request);
		String startDate=request.getParameter("startDate");
		String endDate=request.getParameter("endDate");
		Integer gitInfoId=Integer.parseInt(request.getParameter("gitInfoId"));
		Integer index=Integer.parseInt(request.getParameter("index"));
		page = thStatusService.searchByGitInfoIdAndDeviceTypeAndIndex(page,gitInfoId,DeviceType.TH,index,startDate,endDate);
		return getEasyUIData(page);

	}
}
