package com.agama.itam.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.agama.aws.helper.MongoQueryFilter;
import com.agama.common.dao.utils.Page;
import com.agama.common.web.BaseController;
import com.agama.device.domain.DevicePurchase;
import com.agama.device.service.IDevicePurchaseService;
import com.agama.itam.bean.DeviceAccountsChartBean;
import com.agama.itam.bean.DeviceBelongChartBean;
import com.agama.itam.bean.DeviceFaultTopNBean;
import com.agama.itam.bean.DeviceMaintenanceChartBean;
import com.agama.itam.bean.MaintenanceTopNBean;
import com.agama.itam.bean.SupplyTopNBean;
import com.agama.itam.mongo.domain.InspectRecord;
import com.agama.itam.service.IAccountsChartService;
import com.agama.itam.service.IBelongChartService;
import com.agama.itam.service.IMaintenanceChartService;
import com.agama.itam.service.IProblemService;
import com.agama.itam.service.InspectRecordService;
import com.agama.tool.service.excel.ExcelUtils;
import com.agama.tool.service.excel.JsGridReportBase;
import com.agama.tool.service.excel.TableData;

@Controller
@RequestMapping("device/chart")
public class ChartController extends BaseController {

	@Autowired
	private IDevicePurchaseService devicePurchaseService;
	@Autowired
	private InspectRecordService inspectRecordService;
	@Autowired
	private IBelongChartService belongChartService;
	@Autowired
	private IMaintenanceChartService maintenanceChartService;
	@Autowired
	private IProblemService problemService;
	@Autowired
	private IAccountsChartService accountsChartService;	
	
	/**
	 * 报表界面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "chart/chartView";
	}
	
	/**
	 * 供应商topN报表界面
	 */
	@RequestMapping(value="supplyTopNChart",method=RequestMethod.GET)
	public String supplyTopNChart(){
		return "chart/supplyTopNChart";
	}
	
	/**
	 * 运维商topN报表界面
	 */
	@RequestMapping(value="maintainTopNChart",method=RequestMethod.GET)
	public String maintainTopNChart(){
		return "chart/maintainTopNChart";
	}

	/**
	 * 设备故障topN报表界面
	 */
	@RequestMapping(value="deviceFaultTopNChart",method=RequestMethod.GET)
	public String deviceFaultTopNChart(){
		return "chart/deviceFaultTopNChart";
	}
	
	/**
	 * 采购记录报表界面
	 */
	@RequestMapping(value = "devicePurchase",method=RequestMethod.GET)
	public String getDevicePurchase() {
		return "chart/devicePurchaseChart";
	}

	/**
	 * 设备归属报表界面
	 */
	@RequestMapping(value = "deviceBelong",method=RequestMethod.GET)
	public String getDeviceBelong() {
		return "chart/deviceBelongChart";
	}

	/**
	 * 设备维修报表界面
	 */
	@RequestMapping(value = "deviceMaintenance",method=RequestMethod.GET)
	public String getDeviceMaintenance() {
		return "chart/deviceMaintenanceChart";
	}

	/**
	 * 设备巡检报表界面
	 */
	@RequestMapping(value = "deviceInspect",method=RequestMethod.GET)
	public String getDeviceInspect() {
		return "chart/deviceInspectChart";
	}

	/**
	 * 设备台账报表界面
	 */
	@RequestMapping(value = "deviceAccounts",method=RequestMethod.GET)
	public String getDeviceAccounts() {
		return "chart/deviceAccountsChart";
	}

	@RequestMapping(value="deviceBelongJson",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getDeviceBelongData(HttpServletRequest request){
		Page<DeviceBelongChartBean> deviceBelongPage = getPage(request);
		deviceBelongPage=belongChartService.findPageBySQL(deviceBelongPage,request);
		return getEasyUIData(deviceBelongPage);
	}
	
	@RequestMapping(value="deviceMaintenanceJson",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getDeviceMaintenance(HttpServletRequest request) throws Exception{
		Page<DeviceMaintenanceChartBean> maintenancePage = getPage(request);
		maintenancePage = maintenanceChartService.findPageBySQL(maintenancePage, request);
		return getEasyUIData(maintenancePage);
	}
	
	@RequestMapping(value="deviceInspectJson",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getDeviceInspect(HttpServletRequest request){
		Page<InspectRecord> page = getPage(request);
		Criteria criteria = MongoQueryFilter.buildFromHttpRequest(request);
		Map<String,Object> map = inspectRecordService.queryListForPage(criteria, null, page.getPageNo(), page.getPageSize());
		return map;
	}
	
	@RequestMapping(value="deviceAccountsJson",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getDeviceAccounts(HttpServletRequest request){
		Page<DeviceAccountsChartBean> accountsChartPage = getPage(request);
		accountsChartPage = accountsChartService.findPageBySQL(accountsChartPage);
		return getEasyUIData(accountsChartPage);
	}
	
	/**
	 * 设备采购记录_导出excel
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("devicePurchase_exportExcel")
	public void exportExcel1(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("application/msexcel;charset=UTF-8");
		Page<DevicePurchase> devicePurchasePage = getPage(request);
		devicePurchasePage.setPageSize(Integer.MAX_VALUE);
		List<DevicePurchase> list = devicePurchaseService.findPageByHQL(devicePurchasePage, request).getResult();

		String title = "设备采购记录";
		String[] hearders = new String[] { "采购批次编号", "采购名称", "采购日期", "采购机构", "采购数量", "备注" };
		String[] fields = new String[] { "id", "name", "purchaseDate", "orgName", "quantity", "otherNote" };
		TableData td = ExcelUtils.createTableData(list, ExcelUtils.createTableHeader(hearders), fields);
		JsGridReportBase report = new JsGridReportBase(request, response);
		report.exportToExcel(title, SecurityUtils.getSubject().getPrincipal().toString(), td);
	}

	/**
	 * 设备归属记录_导出excel
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("deviceBelong_exportExcel")
	public void exportExcel2(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("application/msexcel;charset=UTF-8");
		Page<DeviceBelongChartBean> deviceBelongPage = getPage(request);
		deviceBelongPage.setPageSize(Integer.MAX_VALUE);
		List<DeviceBelongChartBean> list= belongChartService.findPageBySQL(deviceBelongPage,request).getResult();
		
		String title = "设备归属记录";
		String[] hearders = new String[] { "设备名称","设备唯一码", "设备类型", "设备型号", "采购日期", "所属机构", "数量", "管理员" };
		String[] fields = new String[] { "name", "identifier", "deviceType", "model", "purchaseDate", "orgName", "quantity", "managerName" };
		TableData td = ExcelUtils.createTableData(list, ExcelUtils.createTableHeader(hearders), fields);
		JsGridReportBase report = new JsGridReportBase(request, response);
		report.exportToExcel(title, SecurityUtils.getSubject().getPrincipal().toString(), td);
	}
	
	/**
	 * 设备维修记录_导出excel
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("deviceMaintenance_exportExcel")
	public void exportExcel3(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("application/msexcel;charset=UTF-8");
		Page<DeviceMaintenanceChartBean> maintenancePage = getPage(request);
		maintenancePage.setPageSize(Integer.MAX_VALUE);
		List<DeviceMaintenanceChartBean> list=maintenanceChartService.findPageBySQL(maintenancePage, request).getResult();
		
		String title = "设备维修记录";
		String[] hearders = new String[] { "设备名称", "设备唯一识别码","上报日期", "关闭时间", "所属机构", "问题类型", "问题描述", "问题状态"
				 , "登记人", "处理人", "上报渠道"};
		String[] fields = new String[] { "name","identifier", "recordTime", "handleTime", "orgName", "problemType", "description", 
				"enable", "recordUserName", "resolveUserName", "reportWay"};
		TableData td = ExcelUtils.createTableData(list, ExcelUtils.createTableHeader(hearders), fields);
		JsGridReportBase report = new JsGridReportBase(request, response);
		report.exportToExcel(title, SecurityUtils.getSubject().getPrincipal().toString(), td);
	}

	/**
	 * 设备巡检记录_导出excel
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */

	@RequestMapping("deviceInspect_exportExcel")
	public void exportExcel4(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("application/msexcel;charset=UTF-8");
		Page<InspectRecord> inspectRecordPage = getPage(request);
		inspectRecordPage.setPageSize(Integer.MAX_VALUE);
		List<InspectRecord> list = inspectRecordService.getList(inspectRecordPage,request);// 获取数据

		String title = "设备巡检记录";
		String[] hearders = new String[] { "巡检网点", "巡检时间", "巡检状态", "应巡检总数", "实际巡检总数", "非网点设备数量", "未巡检设备", "巡检人" };
		String[] fields = { "orgId", "inspectTime", "inspectStatus", "deviceTotal", "inspectedTotal", "inexistendTotal",
				"uncheckedTotal", "userId" };
		TableData td = ExcelUtils.createTableData(list, ExcelUtils.createTableHeader(hearders), fields);
		JsGridReportBase report = new JsGridReportBase(request, response);
		report.exportToExcel(title, SecurityUtils.getSubject().getPrincipal().toString(), td);
	}

	/**
	 * 设备台账_导出excel
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */

	@RequestMapping("deviceAccounts_exportExcel")
	public void exportExcel5(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("application/msexcel;charset=UTF-8");
		Page<DeviceAccountsChartBean> accountsChartPage = getPage(request);
		accountsChartPage.setPageSize(Integer.MAX_VALUE);
		List<DeviceAccountsChartBean> list = accountsChartService.findPageBySQL(accountsChartPage).getResult();// 获取数据

		String title = "设备台账";
		String[] hearders = new String[] { "日期", "品牌名称","设备名称", "上期数", "入库", "出库", "报废", "备注" };
		String[] fields = { "purchaseDate", "brandName", "deviceType", "previousQuantity","inflowQuantity", 
				"outflowQuantity", "scrapQuantity", "otherNote" };
		TableData td = ExcelUtils.createTableData(list, ExcelUtils.createTableHeader(hearders), fields);
		JsGridReportBase report = new JsGridReportBase(request, response);
		report.exportToExcel(title, SecurityUtils.getSubject().getPrincipal().toString(), td);
	}
	
	/**
	 * 设备故障对应供应商TopN统计
	 */
	@RequestMapping(value="supplyTopNDataJson",method=RequestMethod.POST)
	@ResponseBody
	public List<SupplyTopNBean> getSupplyTopNDataJson(HttpServletRequest request){
		return problemService.findDataBySQL(request);
	}
	
	/**
	 * 设备故障对应运维商TopN解决
	 */
	@RequestMapping(value="maintenanceTopNDataJson",method=RequestMethod.POST)
	@ResponseBody
	public List<MaintenanceTopNBean> getMaintenanceTopNDataJson(HttpServletRequest request){
		return problemService.findMaintenanceDataBySQL(request);
	}
	
	/**
	 * 设备故障TopN
	 */
	@RequestMapping(value="deviceFaultTopNDataJson",method=RequestMethod.POST)
	@ResponseBody
	public List<DeviceFaultTopNBean> getDeviceFaultTopNDataJson(HttpServletRequest request){
		return problemService.findDeviceFaultDataBySQL(request);
	}
}
