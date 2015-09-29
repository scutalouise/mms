package com.agama.pemm.controller;

import java.text.SimpleDateFormat;
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
import com.agama.pemm.bean.UpsChartBean;
import com.agama.pemm.domain.AlarmLog;
import com.agama.pemm.domain.Device;
import com.agama.pemm.domain.GitInfo;
import com.agama.pemm.domain.UpsStatus;
import com.agama.pemm.service.IAlarmLogService;
import com.agama.pemm.service.IDeviceService;
import com.agama.pemm.service.IGitInfoService;
import com.agama.pemm.service.IUpsStatusService;

@Controller
@RequestMapping(value="chart")
public class ChartController {
	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Autowired
	private IAreaInfoService areaInfoService;
	@Autowired
	private IAlarmLogService alarmLogService;
	@Autowired
	private IGitInfoService gitInfoService;
	@Autowired
	private IDeviceService deviceService;
	@Autowired
	private IUpsStatusService upsStatusService;
	
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
	@RequestMapping(value="upsJson",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getUpsData(Integer deviceId,String time){
		Map<String,Object> map=new HashMap<String, Object>();
		Date endDate =new Date();
		
		Date beginDate = new Date(endDate.getTime()
				- (Long.parseLong(time) * 1000 * 60 * 60));
		
		List<UpsChartBean> UpsChartBeanList=upsStatusService.getListbyDeviceId(deviceId,beginDate,endDate);
		map.put("data", UpsChartBeanList);
		Date lastDate=new Date();
		if(UpsChartBeanList!=null&&UpsChartBeanList.size()>0){
			lastDate=UpsChartBeanList.get(UpsChartBeanList.size()-1).getCollectTime();
		}
		map.put("lastDate", lastDate);
		return map;
	}
	@RequestMapping(value="upsNewJson",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getUpsNewData(Integer deviceId,Long startDate){
		Map<String,Object> map=new HashMap<String, Object>();
		Date endDate =new Date();
		Date beginDate=new Date(startDate);	
		List<UpsChartBean> UpsChartBeanList=upsStatusService.getListbyDeviceId(deviceId,beginDate,endDate);
		map.put("data", UpsChartBeanList);
		Date lastDate=new Date();
		if(UpsChartBeanList!=null&&UpsChartBeanList.size()>0){
			lastDate=UpsChartBeanList.get(UpsChartBeanList.size()-1).getCollectTime();
		}
		map.put("lastDate", lastDate);
		return map;
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
	@RequestMapping(value="deviceTabs")
	public String deviceTabs(Integer gitInfoId,Model model){
		List<Device> deviceList=deviceService.getListByGitInfoIdAndDeviceType(gitInfoId, DeviceType.UPS);
		model.addAttribute("deviceList", deviceList);
		return "chart/deviceTabs";
		
	}
	@RequestMapping(value="upsChart")
	public String upsChart(Integer deviceId,Model model){
		model.addAttribute("deviceId", deviceId);
		return "chart/upsChart";
		
	}
	
	

	
	
}
