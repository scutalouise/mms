package com.agama.pemm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.agama.authority.common.web.BaseController;
@Controller
@RequestMapping("system/alarmRule")
public class AlarmRuleController extends BaseController{
	@RequestMapping(method=RequestMethod.GET)
	public String view(){
		return "alarmTemplate/alarmRule"; 
	}

}
