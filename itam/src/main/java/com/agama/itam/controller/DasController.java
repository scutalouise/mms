package com.agama.itam.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.agama.itam.service.ICollectAlarmHandleService;
import com.agama.itam.service.IProblemService;
import com.alibaba.fastjson.JSONArray;

@Controller
@RequestMapping("das")
public class DasController {

	@Autowired
	private IProblemService problemService;

	@Autowired
	private ICollectAlarmHandleService collectAlarmHandleService;

	@RequestMapping(value = "inspectionData", method = RequestMethod.POST)
	public void inspectionData(HttpServletRequest request) {
		String jsonStr = request.getParameter("data");

		collectAlarmHandleService.alarmConditionHandle(jsonStr);
	}

	@RequestMapping(value = "deviceInfo/dasId/{dasId}/lastDataGetTime/{time}", method = RequestMethod.GET)
	@ResponseBody
	public List getDeviceInfo(@PathVariable("dasId") String dasId, @PathVariable(value = "time") String time, HttpServletRequest request) {
		System.out.println(request.getParameter("data"));
		return new ArrayList();

	}

	@RequestMapping(value = "test", method = RequestMethod.GET)
	@ResponseBody
	public int test() {
		return 0;
	}

//	@SuppressWarnings("unchecked")
//	@RequestMapping(value = "receive", method = RequestMethod.POST)
//	@ResponseBody
//	public String createAlarmProblem(HttpServletRequest request) {
//		try {
//			String data = request.getParameter("data");
//			JSONArray jsonArray = JSONArray.parseArray(data);
//			Map<String, String>[] mapArray = (Map<String, String>[]) jsonArray.toArray();
//			List<Map<String, String>> list = Arrays.asList(mapArray);
//			problemService.saveAlarmProblem(list);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return "数据保存失败！系统发生异常！";
//		}
//		return "success";
//	}

}
