package com.agama.authority.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.agama.authority.entity.Log;
import com.agama.authority.service.ILogService;
import com.agama.common.dao.utils.Page;
import com.agama.common.dao.utils.PropertyFilter;
import com.agama.common.web.BaseController;
import com.agama.tool.service.excel.ExcelUtils;
import com.agama.tool.service.excel.JsGridReportBase;
import com.agama.tool.service.excel.TableData;

/**
 * 日志controller
 * @author ty
 * @date 2015年1月14日
 */
@Controller
@RequestMapping("system/log")
public class LogController extends BaseController{

	@Autowired
	private ILogService logService;
	
	/**
	 * 默认页面
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list(){
		return "system/logList";
	}
	
	/**
	 * 获取日志json
	 */
	@RequiresPermissions("sys:log:view")
	@RequestMapping("json")
	@ResponseBody
	public Map<String, Object> list(HttpServletRequest request) {
		Page<Log> logPage=getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		if(filters.size()>0){
			logPage.setPageNo(1);
		}
		logPage = logService.search(logPage, filters);
		//构造easyui表格数据	
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("rows", logPage.getResult());
		map.put("total", logPage.getTotalCount());
		return map;
	}
	
	/**
	 * 删除日志
	 * @param id
	 */
	@RequiresPermissions("sys:log:delete")
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Integer id) {
		logService.delete(id);
		return "success";
	}
	
	/**
	 * 批量删除日志
	 * @param idList
	 */
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	public String delete(@RequestBody List<Integer> idList) {
		logService.deleteLog(idList);
		return "success";
	}

	/**
	 * 导出excel
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("exportExcel")
	public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setContentType("application/msexcel;charset=GBK");
		Page<Log> logPage=getPage(request);
		logPage.setPageSize(Integer.MAX_VALUE);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		List<Log> list = logService.search(logPage, filters).getResult();//获取数据
//        List<Log> list = logService.getAll();//获取数据
        
        String title = "log";
        String[] hearders = new String[] {"用户","操作时间","操作编码", "内容","请求参数", "执行时间(mm)", "IP","操作系统", "浏览器", "MAC"};//表头数组
        String[] fields = new String[] {"creater","createDate","operationCode", "description","requestParam", "executeTime","ip", "os", "browser", "mac"};//People对象属性数组
        TableData td = ExcelUtils.createTableData(list, ExcelUtils.createTableHeader(hearders),fields);
        JsGridReportBase report = new JsGridReportBase(request, response);
        report.exportToExcel(title, SecurityUtils.getSubject().getPrincipal().toString(), td);
	}
}
