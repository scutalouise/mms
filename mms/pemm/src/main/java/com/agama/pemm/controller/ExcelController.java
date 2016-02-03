package com.agama.pemm.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.agama.authority.service.IOrganizationService;
import com.agama.common.enumbean.DeviceInterfaceType;
import com.agama.common.enumbean.DeviceType;
import com.agama.common.web.BaseController;
import com.agama.pemm.bean.ThChartBean;
import com.agama.pemm.bean.UpsChartBean;
import com.agama.pemm.domain.ThStatus;
import com.agama.pemm.domain.UpsStatus;
import com.agama.pemm.service.IAlarmLogService;
import com.agama.pemm.service.IExcelService;
import com.agama.pemm.service.IThStatusService;
import com.agama.pemm.service.IUpsStatusService;
import com.agama.tool.service.excel.ExcelUtils;
import com.agama.tool.service.excel.JsGridReportBase;
import com.agama.tool.service.excel.TableData;

@Controller
@RequestMapping(value = "system/excel")
public class ExcelController extends BaseController {
	@Autowired
	private IUpsStatusService upsStatusService;
	@Autowired
	private IThStatusService thStatusService;
	@Autowired
	private IAlarmLogService alarmLogService;
	@Autowired
	private IOrganizationService organizationService;
	@Autowired
	private IExcelService excelService;
	
	@RequestMapping(value = "exportUpsData/{deviceId}/{time}",method=RequestMethod.GET)
	@ResponseBody
	public void exportUpsData(@PathVariable Integer deviceId,@PathVariable Long time,HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("application/msexcel;charset=UTF-8");
	
		Date endDate =new Date();
		
		Date beginDate = new Date(endDate.getTime()
				- (time* 1000 * 60 * 60));
		
		List<UpsChartBean> list = upsStatusService.getUpsChartBeanList(deviceId, beginDate, endDate);
      
        String title = "UPS实时数据统计";
        String[] hearders = new String[] {"输入电压", "输出电压","负载容量","电池电压","获取时间"};//表头数组
        String[] fields = new String[] {"inputVoltage","outputVoltage", "upsLoad","batteryVoltage","collectTime"};//People对象属性数组
        TableData td = ExcelUtils.createTableData(list, ExcelUtils.createTableHeader(hearders),fields);
        try {
			JsGridReportBase report = new JsGridReportBase(request, response);
			report.exportToExcel(title, SecurityUtils.getSubject()
					.getPrincipal().toString(), td);
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
	@RequestMapping(value = "exportThData/{deviceId}/{time}",method=RequestMethod.GET)
	@ResponseBody
	public void exportThData(@PathVariable Integer deviceId,@PathVariable Long time,HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("application/msexcel;charset=UTF-8");
	
		Date endDate =new Date();
		
		Date beginDate = new Date(endDate.getTime()
				- (time* 1000 * 60 * 60));
		
		List<ThChartBean> list = thStatusService.getListByDeviceId(deviceId, beginDate, endDate);
      
        String title = "温湿度实时数据统计";
        String[] hearders = new String[] {"温度(℃)", "湿度(%)","获取时间"};//表头数组
        String[] fields = new String[] {"temperature","humidity","collectTime"};//People对象属性数组
        TableData td = ExcelUtils.createTableData(list, ExcelUtils.createTableHeader(hearders),fields);
        try {
			JsGridReportBase report = new JsGridReportBase(request, response);
			report.exportToExcel(title, SecurityUtils.getSubject()
					.getPrincipal().toString(), td);
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
	@RequestMapping(value="exportAlarmRecord/{beginDate}/{endDate}")
	public void exportAlarmRecord(Integer organizationId,DeviceInterfaceType deviceInterfaceType,@PathVariable("beginDate")String beginDate,@PathVariable("endDate") String endDate,HttpServletRequest request,HttpServletResponse response){
		response.setContentType("application/msexcel;charset=UTF-8");
		String organizationIdIdStr=organizationService.getOrganizationIdStrById(organizationId);

		List<Object> list= (List<Object>) alarmLogService.getAlarmNumAndTime(organizationIdIdStr,deviceInterfaceType,beginDate,endDate);
		
		
      
        String title = "报警统计";
        String[] hearders = new String[] {"次数","获取时间"};//表头数组
        String[] fields = new String[] {"num","collectTime"};//People对象属性数组
        TableData td = ExcelUtils.createTableData(list, ExcelUtils.createTableHeader(hearders),fields);
        try {
			JsGridReportBase report = new JsGridReportBase(request, response);
			report.exportToExcel(title, SecurityUtils.getSubject()
					.getPrincipal().toString(), td);
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
	@RequestMapping(value="exportUpsStatus/{startDate}/{endDate}/{index}/{gitInfoId}")
	public void exportUpsStatus(@PathVariable(value="startDate") String startDate,@PathVariable(value="endDate") String endDate,@PathVariable(value="index") Integer index,@PathVariable(value="gitInfoId") Integer gitInfoId,HttpServletRequest request,HttpServletResponse response){
		response.setContentType("application/msexcel;charset=UTF-8");
		
		
		
		List<UpsStatus> list = upsStatusService.getListByGitInfoIdAndDeviceTypeAndIndex(gitInfoId, DeviceType.UPS, index, startDate, endDate);
      
        String title = "UPS状态数据【"+startDate+"-"+endDate+"】";
        String[] headers = new String[] {"通讯状态", "电池电压","UPS状态","频率",
        		"机内温度","旁路电压","旁路频率","输入电压",
        		"输出电压","故障电压","负载","输出频率","单节电压","充电量",
        		"获取时间"};//表头数组
              
        excelService.exportUpsStatus(title, headers, list, SecurityUtils.getSubject().getPrincipal().toString(), request, response);

	}
	@RequestMapping(value="exportThStatus/{startDate}/{endDate}/{index}/{gitInfoId}")
	public void exportThStatus(@PathVariable(value="startDate") String startDate,@PathVariable(value="endDate") String endDate,@PathVariable(value="index") Integer index,@PathVariable(value="gitInfoId") Integer gitInfoId,HttpServletRequest request,HttpServletResponse response){
		response.setContentType("application/msexcel;charset=UTF-8");
		
		
		
		List<ThStatus> list = thStatusService.getListByGitInfoIdAndDeviceTypeAndIndex(gitInfoId, DeviceType.TH, index, startDate, endDate);
      
        String title = "温湿度状态数据【"+startDate+"至"+endDate+"】";
        String[] headers = new String[] {"温度", "湿度",
        		"获取时间"};//表头数组
              
        excelService.exportThStatus(title, headers, list, SecurityUtils.getSubject().getPrincipal().toString(), request, response);

	}
}
