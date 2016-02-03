package com.agama.pemm.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;





import com.agama.common.dao.utils.Page;
import com.agama.common.dao.utils.PropertyFilter;
import com.agama.common.web.BaseController;
import com.agama.pemm.domain.DischargeLog;
import com.agama.pemm.service.IDischargeLogService;

/**
 * @Description:放电日志控制层
 * @Author:ranjunfeng
 * @Since :2015年12月2日 上午10:01:38
 */
@Controller
@RequestMapping("system/dischargeLog")
public class DischargeLogController extends BaseController {
	@Autowired
	private IDischargeLogService dischargeLogService;
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		
		return "log/dischargeLogList";
	}
	@RequestMapping(value="json",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getData(HttpServletRequest request){
		Page<DischargeLog> page=getPage(request);
		
		page=dischargeLogService.searchList(page,0);
		
		return getEasyUIData(page);
	}
	@RequiresPermissions("sys:dischargeLog:delete")
	@RequestMapping(value="delete")
	@ResponseBody
	public String delete(String ids){
		dischargeLogService.updateStatusByIds(ids);
		return "success";
	}
}
