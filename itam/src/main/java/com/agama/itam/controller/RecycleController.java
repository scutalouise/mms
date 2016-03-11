package com.agama.itam.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.agama.authority.utils.UserUtil;
import com.agama.common.dao.utils.Page;
import com.agama.common.dao.utils.PropertyFilter;
import com.agama.common.entity.Recycle;
import com.agama.common.service.IRecycleService;
import com.agama.common.web.BaseController;

/**
 * @Description:回收站控制层；
 * @Author:杨远高
 * @Since :2016年2月1日 上午10:02:59
 */
@Controller
@RequestMapping("recycle")
public class RecycleController extends BaseController{

	@Autowired
	private IRecycleService recycleService;

	/**
	 * @Description:默认回收站页面
	 * @return
	 * @Since :2016年2月1日 上午10:20:07
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String view() {
		return "recycle/recycleList";
	}
	
	/**
	 * @Description:获取页面上数据
	 * @param request
	 * @return
	 * @Since :2016年2月1日 上午10:22:48
	 */
	@RequestMapping(value = "json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getData(HttpServletRequest request) {
		Page<Recycle> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = recycleService.findPage(page, filters);
		return getEasyUIData(page);
	}
	
	/**
	 * @Description:执行还原的操作；
	 * @param id
	 * @return
	 * @Since :2016年3月9日 下午3:52:35
	 */
	@RequestMapping(value = "{id}",method = RequestMethod.GET)
	@ResponseBody
	public String execRecycle(@PathVariable Integer id){
		int count = recycleService.execRecycle(id,UserUtil.getCurrentUser().getId());
		return count == 0 ? "没有做任何修改" : "success";
	}
	
	
}
