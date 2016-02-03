package com.agama.device.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.agama.common.domain.SoundBean;
import com.agama.device.domain.SendConfig;
import com.agama.device.domain.Sms;
import com.agama.device.service.ISendConfigService;
import com.agama.device.service.ISmsService;
import com.alibaba.fastjson.JSON;

/**
 * @Description:短信模块控制器实体类
 * @Author:ranjunfeng
 * @Since :2015年10月9日 上午10:35:02
 */
@Controller
@RequestMapping("system/sms")
public class SmsController {
	@Autowired
	private ISmsService smsService;
	@Autowired
	private ISendConfigService sendConfigService;

	/**
	 * @Description:短信配置主页面
	 * @param model
	 * @return
	 * @Since :2015年10月9日 上午10:34:37
	 */
	@RequestMapping(method=RequestMethod.GET)
	public String view(Model model){
		
		
		Sms sms=new Sms();
		SendConfig sendConfig=new SendConfig();
		SoundBean soundBean=new SoundBean();
		List<Sms> smsList=smsService.getAll();
		List<SendConfig> sendConfigs=sendConfigService.getAll();
		if(smsList!=null&&smsList.size()>0){
			sms=smsList.get(0);
			 
		}
		
		if(sendConfigs!=null&&sendConfigs.size()>0){
			sendConfig=sendConfigs.get(0);
			soundBean=JSON.parseObject(sendConfig.getContent(),SoundBean.class);
			
		}
		model.addAttribute("sms",sms);
		model.addAttribute("soundConfig",sendConfig);
		
		
		model.addAttribute("soundBean",soundBean);
		return "sms/smsindex";
	}
	@RequestMapping(value="save",method=RequestMethod.POST)
	@ResponseBody
	public String save(@Valid Sms sms){
		if(sms.getId()!=null){
			smsService.update(sms);
		}else{
			smsService.save(sms);
		}
		return "success";
	}

}
