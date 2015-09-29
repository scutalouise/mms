package com.agama.pemm.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.agama.authority.common.web.BaseController;
import com.agama.common.dao.utils.Page;
import com.agama.common.dao.utils.PropertyFilter;
import com.agama.pemm.domain.AlarmLog;
import com.agama.pemm.domain.GitInfo;
import com.agama.pemm.service.IAlarmLogService;
@Controller
@RequestMapping("system/alarmLog")
public class AlarmLogController extends BaseController {
	@Autowired
	private IAlarmLogService alarmLogService;
	@RequestMapping(value="json",method = RequestMethod.POST)
	@ResponseBody
	public List<AlarmLog> getData(HttpServletRequest request){
		
		return alarmLogService.getAlarmLog();
	}

}
