package com.agama.authority.web;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.agama.authority.entity.Organization;
import com.agama.authority.service.IOrganizationService;
import com.agama.common.domain.TreeBean;
import com.agama.common.web.BaseController;

/**
 * 机构信息controller
 * @author ty
 * @date 2015年1月22日
 */
@Controller
@RequestMapping("system/organization")
public class OrganizationController extends BaseController{

	@Autowired
	private IOrganizationService organizationService;

	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "system/organization/orgList";
	}
	
	/**
	 * 获取机构信息json
	 */
	@RequestMapping(value="json",method = RequestMethod.GET)
	@ResponseBody
	public List<Organization> areaInfoList(HttpServletRequest request) {
		List<Organization> organizations=organizationService.getAll();
		return organizations;
	}
	
	/**
	 * 添加机构信息跳转
	 * 
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("organization", new Organization());
		model.addAttribute("action", "create");
		return "system/organization/orgForm";
	}

	/**
	 * 添加机构信息
	 * 
	 * @param organization
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid Organization organization, Model model) {
		organizationService.save(organization);
		return "success";
	}

	/**
	 * 修改机构信息跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("organization", organizationService.get(id));
		model.addAttribute("action", "update");
		return "system/organization/orgForm";
	}

	/**
	 * 修改机构信息
	 * 
	 * @param goodsType
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody Organization organization,Model model) {
		organizationService.update(organization);
		return "success";
	}

	/**
	 * 删除机构信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Integer id) {
		organizationService.delete(id);
		return "success";
	}
	@RequestMapping(value="tree")
	@ResponseBody
	public List<TreeBean> tree(Integer pid){
		List<TreeBean> treeBeans=organizationService.getTreeByPid(pid);
		return treeBeans;
		
	}
	
	
}
