package com.agama.pemm.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.agama.authority.service.IAreaInfoService;
import com.agama.authority.service.IOrganizationService;
import com.agama.common.enumbean.DeviceInterfaceType;
import com.agama.pemm.bean.ThChartBean;
import com.agama.pemm.bean.UpsChartBean;
import com.agama.pemm.domain.Device;
import com.agama.pemm.domain.GitInfo;
import com.agama.pemm.service.IAlarmLogService;
import com.agama.pemm.service.IDeviceService;
import com.agama.pemm.service.IGitInfoService;
import com.agama.pemm.service.IThStatusService;
import com.agama.pemm.service.IUpsStatusService;

import com.agama.common.enumbean.DeviceType;

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
	@Autowired
	private IUpsStatusService upsStatusService;
	@Autowired
	private IThStatusService thStatusService;
	@Autowired
	private IOrganizationService organizationService;
	
	@RequestMapping(method=RequestMethod.GET)
	public String list(){
		return "chart/chartView";
	}
	@RequestMapping(value="json",method=RequestMethod.GET)
	@ResponseBody
	public Object getData(Integer organizationId,DeviceInterfaceType deviceInterfaceType,String beginDate,String endDate){
		//查找到所有的所属组织机构                                                                 
		String organizationIdIdStr=organizationService.getOrganizationIdStrById(organizationId);
		return alarmLogService.getAlarmNumAndTime(organizationIdIdStr,deviceInterfaceType,beginDate,endDate);
	}
	/**
	 * @Description:通过设备的ID获取UPS状态信息和最新一条数据的时间封装到Map集合中
	 * @param deviceId 设备ID
	 * @param time 查看的时间
	 * @return 
	 * @Since :2015年10月14日 下午5:53:32
	 */
	@RequestMapping(value="upsJson",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getUpsData(Integer deviceId,String time){
		Map<String,Object> map=new HashMap<String, Object>();
		Date endDate =new Date();
		
		Date beginDate = new Date(endDate.getTime()
				- (Long.parseLong(time) * 1000 * 60 * 60));
		
		List<UpsChartBean> UpsChartBeanList=upsStatusService.getListByDeviceId(deviceId,beginDate,endDate);
		map.put("data", UpsChartBeanList);
		Date lastDate=new Date();
		if(UpsChartBeanList!=null&&UpsChartBeanList.size()>0){
			lastDate=UpsChartBeanList.get(UpsChartBeanList.size()-1).getCollectTime();
		}
		map.put("lastDate", lastDate);
		return map;
	}
	@RequestMapping(value="thJson",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getThData(Integer deviceId,String time){
		Map<String,Object> map=new HashMap<String, Object>();
		//当前时间
		Date endDate=new Date();
		//查询的开始时间
		Date beginDate = new Date(endDate.getTime()
				- (Long.parseLong(time) * 1000 * 60 * 60));
		List<ThChartBean> thChartBeanList=thStatusService.getListByDeviceId(deviceId,beginDate,endDate);
		map.put("data", thChartBeanList);
		Date lastDate=new Date();
		if(thChartBeanList!=null&&thChartBeanList.size()>0){
			lastDate=thChartBeanList.get(thChartBeanList.size()-1).getCollectTime();
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
		List<UpsChartBean> upsChartBeanList=upsStatusService.getListByDeviceId(deviceId,beginDate,endDate);
		map.put("data", upsChartBeanList);
		Date lastDate=new Date();
		if(upsChartBeanList!=null&&upsChartBeanList.size()>0){
			lastDate=upsChartBeanList.get(upsChartBeanList.size()-1).getCollectTime();
		}
		map.put("lastDate", lastDate);
		return map;
	}
	/**	
	 * @Description:获取最新的温湿度数据
	 * @param deviceId
	 * @param startDate 上次获取的最新时间
	 * @return
	 * @Since :2015年10月15日 上午10:37:35
	 */
	@RequestMapping(value="thNewJson",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getThNewData(Integer deviceId,Long startDate){
		Map<String,Object> map=new HashMap<String, Object>();
		Date endDate =new Date();
		Date beginDate=new Date(startDate);	
		List<ThChartBean> thChartBeanList=thStatusService.getListByDeviceId(deviceId,beginDate,endDate);
		map.put("data", thChartBeanList);
		Date lastDate=new Date();
		if(thChartBeanList!=null&&thChartBeanList.size()>0){
			lastDate=thChartBeanList.get(thChartBeanList.size()-1).getCollectTime();
		}
		map.put("lastDate", lastDate);
		return map;
	}
	
	@RequestMapping(value="upsChartView",method=RequestMethod.GET)
	public String upsChartView(){
		return "chart/upsChartView";
	}
	@RequestMapping(value="gitInfoView")
	public String gitInfoView(Integer organizationId,Model model){
		String organizationIdStr=organizationService.getOrganizationIdStrById(organizationId);
		List<GitInfo> gitInfoList=gitInfoService.getListByOrganizationIdStrAndDeviceType(organizationIdStr,DeviceType.UPS);
		model.addAttribute("gitInfoList", gitInfoList);
		return "chart/gitInfoView";
	}
	@RequestMapping(value="deviceTabs")
	public String deviceTabs(Integer gitInfoId,Model model){
		List<Device> upsDeviceList=deviceService.getListByGitInfoIdAndDeviceType(gitInfoId, DeviceType.UPS);
		List<Device> thDeviceList=deviceService.getListByGitInfoIdAndDeviceType(gitInfoId, DeviceType.TH);
		model.addAttribute("upsDeviceList", upsDeviceList);
		model.addAttribute("thDeviceList", thDeviceList);
		return "chart/deviceTabs";
		
	}
	@RequestMapping(value="upsChart")
	public String upsChart(Integer deviceId,Model model){
		model.addAttribute("deviceId", deviceId);
		return "chart/upsChart";
		
	}
	@RequestMapping(value="thChart")
	public String thChart(Integer deviceId,Model model){
		model.addAttribute("deviceId", deviceId);
		return "chart/thChart";
	}
	/**
	 * @Description:跳转到topN报表界面
	 * @return
	 * @Since :2015年10月23日 下午4:33:25
	 */
	@RequestMapping(value="topNChart")
	public String topNChart(){
		return "chart/topNChart";
	}
	@RequestMapping(value="/topNDataJson")
	@ResponseBody
	public Object topNDataJson(Integer organizationId,Integer top,String beginDate,String endDate){
		//查找到所有的所属组织机构                                                                 
				String organizationIdIdStr=organizationService.getOrganizationIdStrById(organizationId);
		return alarmLogService.getAlarmNum(organizationIdIdStr,top,beginDate,endDate);

		
	}

	
	
}
