package com.agama.pemm.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.aspectj.lang.annotation.Around;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.agama.authority.system.dao.IAreaInfoDao;
import com.agama.authority.system.service.IAreaInfoService;
import com.agama.pemm.bean.DeviceStateRecord;
import com.agama.pemm.bean.DeviceType;
import com.agama.pemm.domain.AlarmLog;
import com.agama.pemm.domain.Device;
import com.agama.pemm.domain.GitInfo;
import com.agama.pemm.service.IAlarmLogService;
import com.agama.pemm.service.IDeviceService;
import com.agama.pemm.service.IGitInfoService;

@Controller
@RequestMapping(value="chart")
public class ChartController {
	@Autowired
	private IAreaInfoService areaInfoService;
	@Autowired
	private IAlarmLogService alarmLogService;
	@Autowired
	private IGitInfoService gitInfoService;
	@Autowired
	private IDeviceService deviceService;
	
	@RequestMapping(method=RequestMethod.GET)
	public String list(){
		return "chart/chartView";
	}
	@RequestMapping(value="json",method=RequestMethod.GET)
	@ResponseBody
	public Object getData(Integer areaInfoId,String beginDate,String endDate){
		//查找到所有的所属区域
		String areaInfoIdStr=areaInfoService.getAreaInfoIdStrById(areaInfoId);
		
		return alarmLogService.getAlarmNumAndTime(areaInfoIdStr,beginDate,endDate);
	}
	@RequestMapping(value="upsChartView",method=RequestMethod.GET)
	public String upsChartView(){
		return "chart/upsChartView";
		
	}
	@RequestMapping(value="gitInfoView")
	public String gitInfoView(Integer areaInfoId,Model model){
		String areaInfoIdStr=areaInfoService.getAreaInfoIdStrById(areaInfoId);
		List<GitInfo> gitInfoList=gitInfoService.getListByAreaInfoIdStrAndDeviceType(areaInfoIdStr,DeviceType.UPS);
	
		model.addAttribute("gitInfoList", gitInfoList);
		return "chart/gitInfoView";
	}
	@RequestMapping(value="upsChart")
	public String upsChartView(Integer gitInfoId,Model model){
		List<Device> deviceList=deviceService.getListByGitInfoIdAndDeviceType(gitInfoId, DeviceType.UPS);
		model.addAttribute("deviceList", deviceList);
		return "chart/upsChart";
		
	}
	
	
}
