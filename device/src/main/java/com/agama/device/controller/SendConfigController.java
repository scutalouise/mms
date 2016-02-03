package com.agama.device.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.agama.common.domain.SoundBean;
import com.agama.common.enumbean.SendType;
import com.agama.common.web.BaseController;
import com.agama.device.domain.SendConfig;
import com.agama.device.service.ISendConfigService;
import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping("system/sendConfig")
public class SendConfigController extends BaseController {
	@Autowired
	private ISendConfigService sendConfigService;

	@RequestMapping(value = "soundSave", method = RequestMethod.POST)
	@ResponseBody
	public String sendSave(@Valid SoundBean soundBean, SendConfig sendConfig) {
		if (soundBean != null) {
			String content = JSON.toJSONString(soundBean);
			sendConfig.setContent(content);
		}
		sendConfig.setSendType(SendType.SOUND);
		if (sendConfig.getId() != null) {
			sendConfigService.update(sendConfig);
		} else {
			sendConfigService.save(sendConfig);
		}
		return "success";
	}
}
