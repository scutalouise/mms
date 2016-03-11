package com.agama.itam.controller;

import java.util.Date;
import java.util.List;

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
import com.agama.common.dao.utils.PropertyFilter;
import com.agama.common.domain.TreeBean;
import com.agama.common.enumbean.EnabledStateEnum;
import com.agama.common.enumbean.StatusEnum;
import com.agama.common.web.BaseController;
import com.agama.itam.domain.KnowledgeClassification;
import com.agama.itam.service.IKnowledgeClassificationService;

/**
 * @Description:知识库分类信息
 * @Author:杨远高
 * @Since :2016年3月7日 下午4:59:54
 */
@Controller
@RequestMapping("knowledge/classification")
public class KnowledgeClassificationController extends BaseController{

	@Autowired
	private IKnowledgeClassificationService knowledgeClassificationService;

	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "knowledge/classificationList";
	}
	
	/**
	 * 获取知识库分类信息json
	 */
	@RequestMapping(value="json",method = RequestMethod.GET)
	@ResponseBody
	public List<KnowledgeClassification> classificationList(HttpServletRequest request) {
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		filters.add(new PropertyFilter("EQ_StatusEnum_status",StatusEnum.NORMAL.toString()));//过滤掉，状态为删除状态并且不可用的记录；
		filters.add(new PropertyFilter("EQ_EnabledStateEnum_enable",EnabledStateEnum.ENABLED.toString()));//过滤掉，状态为禁用的记录；
		return knowledgeClassificationService.search(filters);
	}
	
	/**
	 * 添加知识库分类信息跳转
	 * 
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("knowledgeClassification", new KnowledgeClassification());
		model.addAttribute("action", "create");
		return "knowledge/classificationForm";
	}

	/**
	 * 添加知识库分类信息
	 * @param classification
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid KnowledgeClassification classification, Model model) {
		classification.setOperateTime(new Date());
		classification.setOperator(UserUtil.getCurrentUser().getId());
		knowledgeClassificationService.save(classification);
		return "success";
	}

	/**
	 * 修改知识库分类信息跳转
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("knowledgeClassification", knowledgeClassificationService.get(id));
		model.addAttribute("action", "update");
		return "knowledge/classificationForm";
	}

	/**
	 * 修改知识库分类信息
	 * @param classification
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody KnowledgeClassification classification,Model model) {
		classification.setOperateTime(new Date());
		classification.setOperator(UserUtil.getCurrentUser().getId());
		knowledgeClassificationService.update(classification);
		return "success";
	}

	/**
	 * 删除知识库分类信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Integer id) {
		knowledgeClassificationService.delete(id, UserUtil.getCurrentUser().getId());
		return "success";
	}
	
	
	@RequestMapping(value="tree")
	@ResponseBody
	public List<TreeBean> tree(Integer pid){
		List<TreeBean> treeBeans=knowledgeClassificationService.getTreeByPid(pid);
		return treeBeans;
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
			model.addAttribute("knowledgeClassification", knowledgeClassificationService.get(id));
		}
	}
	
	
}
