package com.agama.pemm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.agama.common.enumbean.EnabledStateEnum;
import com.agama.common.web.BaseController;
import com.agama.pemm.domain.AcConfig;
import com.agama.pemm.service.IAcConfigService;
@Controller
@RequestMapping("acConfig")
public class AcConfigController extends BaseController {
	@Autowired
	private IAcConfigService acConfigService;
	@RequestMapping(value="acConfigForm/{deviceId}")
	public String acConfig(@PathVariable("deviceId") Integer deviceId,Model model){
		AcConfig acConfig=acConfigService.getAcConfigByDeviceIdAndEnabled(deviceId, EnabledStateEnum.ENABLED);
		model.addAttribute("deviceId", deviceId);
		model.addAttribute("acConfig", acConfig);
		return "acConfig/acConfigForm";
	}
	@RequestMapping(value="update")
	@ResponseBody
	public String update(AcConfig acConfig){
		if(acConfig.getId()!=null){
			acConfigService.update(acConfig);
		}else{
			acConfigService.save(acConfig);
		}
		return "success";
		
	}

}
