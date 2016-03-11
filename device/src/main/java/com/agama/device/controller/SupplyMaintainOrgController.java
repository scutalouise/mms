package com.agama.device.controller;

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

import com.agama.authority.entity.User;
import com.agama.authority.utils.UserUtil;
import com.agama.common.dao.utils.PropertyFilter;
import com.agama.common.domain.TreeBean;
import com.agama.common.enumbean.EnabledStateEnum;
import com.agama.common.enumbean.StatusEnum;
import com.agama.common.web.BaseController;
import com.agama.device.domain.SupplyMaintainOrg;
import com.agama.device.service.ISupplyMaintainBrandService;
import com.agama.device.service.ISupplyMaintainOrgService;

/**
 * @Description:供应商与运维机构
 * @Author:杨远高
 * @Since :2016年2月29日 下午3:18:22
 */
@Controller
@RequestMapping("device/supplyMaintainOrg")
public class SupplyMaintainOrgController extends BaseController{

	@Autowired
	private ISupplyMaintainOrgService supplyMaintainOrgService;
	
	@Autowired
	private ISupplyMaintainBrandService supplyMaintainBrandService;

	/**
	 * @Description:默认页面
	 * @return
	 * @Since :2016年2月29日 下午4:06:56
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String orgList() {
		return "supplyMaintainOrg/orgList";
	}
	
	/**
	 * 获取机构信息json
	 */
	@RequestMapping(value="json",method = RequestMethod.GET)
	@ResponseBody
	public List<SupplyMaintainOrg> supplyMaintainOrgList(HttpServletRequest request) {
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		filters.add(new PropertyFilter("EQ_StatusEnum_status",StatusEnum.NORMAL.toString()));//过滤掉，状态为删除状态并且不可用的记录；
		filters.add(new PropertyFilter("EQ_EnabledStateEnum_enable",EnabledStateEnum.ENABLED.toString()));//过滤掉，状态为禁用的记录；
		return supplyMaintainOrgService.search(filters);
	}
	
	/**
	 * 添加机构信息跳转
	 * 
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("supplyMaintainOrg", new SupplyMaintainOrg());
		model.addAttribute("action", "create");
		return "supplyMaintainOrg/orgForm";
	}

	/**
	 * 添加机构信息
	 * 
	 * @param supplyMaintainOrg
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid SupplyMaintainOrg supplyMaintainOrg, Model model) {
		supplyMaintainOrgService.save(supplyMaintainOrg);
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
		model.addAttribute("supplyMaintainOrg", supplyMaintainOrgService.get(id));
		model.addAttribute("action", "update");
		return "supplyMaintainOrg/orgForm";
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
	public String update(@Valid @ModelAttribute @RequestBody SupplyMaintainOrg supplyMaintainOrg,Model model) {
		supplyMaintainOrgService.update(supplyMaintainOrg);
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
		supplyMaintainOrgService.delete(id, UserUtil.getCurrentUser().getId());
		return "success";
	}
	
	
	/**
	 * @Description:跳转到品牌页面供选择；
	 * @return
	 * @Since :2016年3月1日 下午5:12:02
	 */
	@RequestMapping(value = "{orgId}/brands",method = RequestMethod.GET)
	public String brand(@PathVariable Integer orgId, Model model) {
		model.addAttribute("orgId", orgId);
		return "supplyMaintainOrg/orgBrandList";
	}
	
	
	@RequestMapping(value = "{orgId}/orgBrand",method = RequestMethod.GET)
	@ResponseBody
	public List<Integer> getBrandIdList(@PathVariable Integer orgId){
		return supplyMaintainBrandService.getBrandIdList(orgId);
	}
	
	
	/**
	 * @Description:
	 * @return
	 * @Since :2016年3月1日 下午5:49:12
	 */
	@RequestMapping(value = "brands/{id}",method = RequestMethod.POST)
	@ResponseBody
	public String setBrands(@PathVariable Integer id, @RequestBody List<Integer> ids) {
		supplyMaintainBrandService.setBrands(id,supplyMaintainBrandService.getBrandIdList(id), ids);
		return "success";
	}
	
	@RequestMapping(value="tree")
	@ResponseBody
	public List<TreeBean> tree(Integer pid){
		List<TreeBean> treeBeans=supplyMaintainOrgService.getTreeByPid(pid);
		return treeBeans;
	}
	
	/**
	 * 根据品牌Id获取供应商集合
	 */
	@RequestMapping(value = "supplyMaintainOrgList", method = RequestMethod.GET)
	@ResponseBody
	public List<SupplyMaintainOrg> getSupplyMaintainOrgList(@RequestParam Integer brandId) {
		return supplyMaintainOrgService.getSupplyMaintainOrgListByBrandId(brandId);
	}
	
	@RequestMapping(value = "users/orgId/{orgId}", method = RequestMethod.GET)
	@ResponseBody
	public List<User> getUsersByOrgId(@PathVariable("orgId") Integer orgId) {
		List<User> users =  supplyMaintainOrgService.getUserListByOrgId(orgId);
		System.out.println(users.size());
		return users;
	}
	
}
