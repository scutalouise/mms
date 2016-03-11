package com.agama.device.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.agama.authority.entity.User;
import com.agama.device.domain.TwoDimentionCode;
import com.agama.device.service.ITwoDimentionCodeService;
import com.fasterxml.jackson.core.JsonProcessingException;

@Controller
@RequestMapping(value = "device/twoDimentionCode")
public class TwoDimentionCodeController {
	
	@Autowired
	private ITwoDimentionCodeService twoDimentionCodeService;

	@RequestMapping(method = RequestMethod.GET)
	public String getData(@RequestParam Integer id, @RequestParam String identifier, @RequestParam String rand,
			HttpServletRequest request, Model model) throws JsonProcessingException {
		TwoDimentionCode twoDimentionCode = twoDimentionCodeService.getByidentifier(identifier);
		String pathSuf = null;
		if (twoDimentionCode == null) {
			pathSuf = twoDimentionCodeService.createQR(identifier, id, request, null);
			TwoDimentionCode twoDimention = new TwoDimentionCode();
			twoDimention.setIdentifier(identifier);
			twoDimention.setAddr(pathSuf);
			twoDimentionCodeService.save(twoDimention);
		} else {
			if (twoDimentionCode.getAddr().length() > 20) {
				pathSuf = twoDimentionCodeService.createQR(identifier, id, request, twoDimentionCode.getAddr());
			} else {
				pathSuf = twoDimentionCodeService.createQR(identifier, id, request, null);
			}
		}
		model.addAttribute("id", id);
		model.addAttribute("identifier", identifier);
		model.addAttribute("path", pathSuf + "?t=" + rand);
		return "details/twoDimentionCode";
	}

	@RequestMapping(value = "updateQR", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateQR(@RequestParam String identifier, @RequestParam Integer id,
			@RequestParam String rand, HttpServletRequest request) throws JsonProcessingException {
		TwoDimentionCode twoDimentionCode = twoDimentionCodeService.getByidentifier(identifier);
		Map<String, Object> map = new HashMap<String, Object>();
		String pathSuf = twoDimentionCodeService.createQR(identifier, id, request, twoDimentionCode.getAddr());
		twoDimentionCode.setAddr(pathSuf);
		twoDimentionCodeService.update(twoDimentionCode);
		map.put("path", pathSuf + "?t=" + rand);
		map.put("msg", "success");
		return map;
	}

	@RequestMapping(value = "callBack/{identifier}", method = RequestMethod.POST)
	@ResponseBody
	public String callBack(@PathVariable String identifier, HttpServletRequest request) {
		TwoDimentionCode twoDimentionCode = twoDimentionCodeService.getByidentifier(identifier);
		User user = (User) request.getSession().getAttribute("user");
		twoDimentionCode.setIsPrint(1);
		twoDimentionCode.setLastPrintTime(new Date());
		twoDimentionCode.setPrintQuantity(twoDimentionCode.getPrintQuantity() + 1);
		twoDimentionCode.setPrintUserId(user.getId());
		twoDimentionCodeService.update(twoDimentionCode);
		return "success";
	}
}
