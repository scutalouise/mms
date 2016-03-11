package com.agama.itam.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.agama.device.service.IAlarmLogService;
import com.agama.device.service.IPeDeviceService;



@Controller
@RequestMapping("system/home")
public class HomeController {
	@Autowired
	private IPeDeviceService peDeviceService;
	@Autowired
	private IAlarmLogService alarmLogService;

	@RequestMapping(method = RequestMethod.GET)
	public String home(Model model) {
		List<Map<String, Object>> currentStateAndNum = peDeviceService.getCurrentStateAndCount();
		for (Map<String, Object> map : currentStateAndNum) {
			model.addAttribute("currentState_" + map.get("currentState"), map.get("num"));
		}
		return "home/home";
	}

	@RequestMapping(value = "yearLineChart", method = RequestMethod.GET)
	@ResponseBody
	public Object yearLineChart(String type) {
		String beginDate = "";
		String endDate = "";
		SimpleDateFormat year_dateFormat = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat month_dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat day_dateFormat = new SimpleDateFormat("yyyy-MM-dd HH");
		Calendar end = Calendar.getInstance();
		Calendar start = Calendar.getInstance();
		start.clear();
		switch (type) {
		case "year":
			start.set(Calendar.YEAR, end.get(Calendar.YEAR) - 1);
			start.set(Calendar.MONTH, end.get(Calendar.MONTH));
			break;
		case "month":
			start.set(end.get(Calendar.YEAR), end.get(Calendar.MONTH), end.get(Calendar.DAY_OF_MONTH));
			Calendar todayOfLastMonth = Calendar.getInstance();
			todayOfLastMonth.setTime(end.getTime());
			todayOfLastMonth.add(Calendar.MONTH, -1);
			beginDate = month_dateFormat.format(todayOfLastMonth.getTime());
			break;
		case "day":
			start.set(end.get(Calendar.YEAR), end.get(Calendar.MONTH), end.get(Calendar.DAY_OF_MONTH));
			start.set(Calendar.HOUR_OF_DAY, end.get(Calendar.HOUR_OF_DAY)-24);
			
		}
		
		switch (type) {
		case "year":
			beginDate = year_dateFormat.format(start.getTime());
			endDate = year_dateFormat.format(new Date());
			return alarmLogService.getAlarmNumAndTimeForYear(beginDate, endDate);
			
		case "month":
			
			endDate = month_dateFormat.format(new Date());
			return alarmLogService.getAlarmNumAndTimeForMonth(beginDate, endDate);
			
		default:
			beginDate = day_dateFormat.format(start.getTime());
			endDate = day_dateFormat.format(new Date());
			return alarmLogService.getAlarmNumAndTimeForDay(beginDate, endDate);
			
			
		}
		
	}
}
