package com.agama.itam.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.agama.common.dao.utils.Page;
import com.agama.common.web.BaseController;
import com.agama.itam.domain.Recycle;
import com.agama.itam.service.IRecycleService;

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
		String sql = " select r.id as id,opUser.name as userName,recoveryUser.name as recoveryUserName, "
					+ "r.op_time as opTime,r.recovery_time as recoveryTime,r.table_name as tableName, "
					+ "r.table_record_id as tableRecordId,r.is_recovery as recoveryString "
					+ "from recycle r "
					+ "left join user opUser on r.op_user_id=opUser.id "
					+ "left join user recoveryUser on r.recovery_user_id=recoveryUser.id ";//查询语句
		Object[] values = {};//查询参数
		page = recycleService.findPageBySQL(page, sql, values);
		return getEasyUIData(page);
	}
}
