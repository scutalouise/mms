package com.agama.authority.system.web;

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

import com.agama.authority.common.web.BaseController;
import com.agama.authority.system.entity.Log;
import com.agama.authority.system.service.ILogService;
import com.agama.authority.system.service.excel.ExcelUtils;
import com.agama.authority.system.service.excel.JsGridReportBase;
import com.agama.authority.system.service.excel.TableData;
import com.agama.common.dao.utils.Page;
import com.agama.common.dao.utils.PropertyFilter;

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
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		List<Log> list = logService.search(filters);//获取数据
//        List<Log> list = logService.getAll();//获取数据
        
        String title = "log";
        String[] hearders = new String[] {"操作编码", "操作内容", "执行时间(mm)", "操作系统", "浏览器", "IP","MAC","操作者","操作时间","请求参数"};//表头数组
        String[] fields = new String[] {"operationCode", "description", "executeTime", "os", "browser", "ip","mac","creater","createDate","requestParam"};//People对象属性数组
        TableData td = ExcelUtils.createTableData(list, ExcelUtils.createTableHeader(hearders),fields);
        JsGridReportBase report = new JsGridReportBase(request, response);
        report.exportToExcel(title, SecurityUtils.getSubject().getPrincipal().toString(), td);
	}
}
