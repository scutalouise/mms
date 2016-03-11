package com.agama.itam.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.agama.authority.utils.UserUtil;
import com.agama.common.dao.utils.Page;
import com.agama.common.dao.utils.PropertyFilter;
import com.agama.common.enumbean.EnabledStateEnum;
import com.agama.common.enumbean.StatusEnum;
import com.agama.common.web.BaseController;
import com.agama.itam.domain.KnowledgeDetails;
import com.agama.itam.service.IKnowledgeDetailsService;

/**
 * @Description:知识库详细信息；
 * @Author:杨远高
 * @Since :2016年3月7日 下午6:16:38
 */
@Controller
@RequestMapping("knowledge/details")
public class KnowledgeDetailsController extends BaseController{

	@Autowired
	private IKnowledgeDetailsService knowledgeDetailsService;

	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "knowledge/detailsList";
	}
	
	/**
	 * 获取知识库信息json
	 */
	@ResponseBody
	@RequestMapping(value="json",method = RequestMethod.GET)
	public Map<String, Object> detailsList(HttpServletRequest request) {
		Page<KnowledgeDetails> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		filters.add(new PropertyFilter("EQ_StatusEnum_status",StatusEnum.NORMAL.toString()));//过滤掉，状态为删除状态并且不可用的记录；
		filters.add(new PropertyFilter("EQ_EnabledStateEnum_enable",EnabledStateEnum.ENABLED.toString()));//过滤掉，状态为禁用的记录；
		page = knowledgeDetailsService.search(page, filters);
		return getEasyUIData(page);
	}
	
	/**
	 * 添加知识库信息跳转
	 * 
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("knowledgeDetails", new KnowledgeDetails());
		model.addAttribute("action", "create");
		return "knowledge/detailsForm";
	}

	/**
	 * 添加知识库信息
	 * @param details
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid KnowledgeDetails details, Model model) {
		details.setCommitTime(new Date());
		details.setCommitUserId(UserUtil.getCurrentUser().getId());
		details.setUpdateTime(new Date());
		details.setUpdateUserId(UserUtil.getCurrentUser().getId());
		knowledgeDetailsService.save(details);
		return "success";
	}

	/**
	 * 修改知识库信息跳转
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("knowledgeDetails", knowledgeDetailsService.get(id));
		model.addAttribute("action", "update");
		return "knowledge/detailsForm";
	}

	/**
	 * 修改知识库信息
	 * @param details
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody KnowledgeDetails details,Model model) {
		details.setUpdateTime(new Date());
		details.setUpdateUserId(UserUtil.getCurrentUser().getId());
		knowledgeDetailsService.update(details);
		return "success";
	}

	/**
	 * 删除知识库信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Integer id) {
		knowledgeDetailsService.delete(id, UserUtil.getCurrentUser().getId());
		return "success";
	}
	
	/**
	 * @Description:在处理修改方法之前，根据id加载数据库记录信息
	 * @param id
	 * @param model
	 * @Since :2016年3月7日 下午5:13:45
	 */
	@ModelAttribute
	public void getArrange(@RequestParam(value = "id", defaultValue = "-1") Integer id, Model model) {
		if (id != -1) {
			model.addAttribute("details", knowledgeDetailsService.get(id));
		}
	}
	
	
}
