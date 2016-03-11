package com.agama.authority.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
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
import com.agama.authority.utils.UserUtil;
import com.agama.common.dao.utils.Page;
import com.agama.common.dao.utils.PropertyFilter;
import com.agama.common.domain.TreeBean;
import com.agama.common.enumbean.EnabledStateEnum;
import com.agama.common.enumbean.StatusEnum;
import com.agama.common.web.BaseController;
import com.agama.tool.service.excel.ExcelUtils;
import com.agama.tool.service.excel.JsGridReportBase;
import com.agama.tool.service.excel.TableData;

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
	@RequestMapping(value="json",method = RequestMethod.POST)
	@ResponseBody
	public List<Organization> organizationList(HttpServletRequest request) {
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		filters.add(new PropertyFilter("EQ_StatusEnum_status",StatusEnum.NORMAL.toString()));//过滤掉，状态为删除状态并且不可用的记录；
		filters.add(new PropertyFilter("EQ_EnabledStateEnum_enable",EnabledStateEnum.ENABLED.toString()));//过滤掉，状态为禁用的记录；
		return organizationService.search(filters);
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
		organizationService.delete(id, UserUtil.getCurrentUser().getId());
		return "success";
	}
	
	
	@RequestMapping(value="tree")
	@ResponseBody
	public List<TreeBean> tree(Integer pid){
		List<TreeBean> treeBeans=organizationService.getTreeByPid(pid);
		return treeBeans;
		
	}
	
	/**
	 * 导出excel
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("exportExcel")
	public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("application/msexcel;charset=GBK");
		Page<Organization> page = getPage(request);
		page.setPageSize(Integer.MAX_VALUE);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		filters.add(new PropertyFilter("EQ_StatusEnum_status",StatusEnum.NORMAL.toString()));
		filters.add(new PropertyFilter("EQ_EnabledStateEnum_enable",EnabledStateEnum.ENABLED.toString()));
		List<Organization> list = organizationService.search(page, filters).getResult();// 获取数据
		String title = "机构信息表";
		String[] hearders = new String[] { "机构名字", "机构类型", "机构编码", "经度", "纬度", "地址", "联系方式" };// 表头数组
		String[] fields = new String[] { "orgName", "orgType", "orgCode", "longitude", "latitude", "address", "contact" };// People对象属性数组
		TableData td = ExcelUtils.createTableData(list, ExcelUtils.createTableHeader(hearders), fields);
		JsGridReportBase report = new JsGridReportBase(request, response);
		report.exportToExcel(title, SecurityUtils.getSubject().getPrincipal().toString(), td);
	}

}
