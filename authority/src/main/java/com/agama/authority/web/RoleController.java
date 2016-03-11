package com.agama.authority.web;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
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

import com.agama.authority.entity.Role;
import com.agama.authority.entity.User;
import com.agama.authority.service.IRolePermissionService;
import com.agama.authority.service.IRoleService;
import com.agama.authority.utils.UserUtil;
import com.agama.common.dao.utils.Page;
import com.agama.common.dao.utils.PropertyFilter;
import com.agama.common.enumbean.EnabledStateEnum;
import com.agama.common.enumbean.StatusEnum;
import com.agama.common.web.BaseController;

/**
 * 角色controller
 * @author ty
 * @date 2015年1月13日
 */
@Controller
@RequestMapping("system/role")
public class RoleController extends BaseController{
	
	@Autowired
	private IRoleService roleService;
	
	@Autowired
	private IRolePermissionService rolePermissionService;
	
	/**
	 * 默认页面
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list(){
		return "system/roleList";
	}
	
	/**
	 * @Description:角色名要求不重复
	 * @param roleName
	 * @return
	 * @Since :2015年9月9日 下午4:01:13
	 */
	@RequestMapping(value="checkRoleName")
	@ResponseBody
	public String checkRoleName(@RequestParam(required=false,value="roleName") String name,@RequestParam("roleId") String roleId){
		Role role = roleService.getRoleByName(name) ;
		if(null == role){
			return "true";
		}else{
			if(roleId.equals(String.valueOf(role.getId()))){
				return "true";
			}else{
				return "false";
			}
		}
	}
	
	/**
	 * @Description:角色编码要求不重复
	 * @param roleCode
	 * @return
	 * @Since :2015年9月9日 下午6:26:31
	 */
	@RequestMapping(value="checkRoleCode")
	@ResponseBody
	public String checkRoleCode(String roleCode){
		if(roleService.getRoleByCode(roleCode) == null){
			return "true";
		}else{
			return "false";
		}
	}
	
	
	/**
	 * 角色集合(JSON)
	 */
	@RequiresPermissions("sys:role:view")
	@RequestMapping(value="json",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getData(HttpServletRequest request) {
		Page<Role> page=getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		filters.add(new PropertyFilter("EQ_StatusEnum_status",StatusEnum.NORMAL.toString()));//过滤掉，状态为删除状态并且不可用的记录；
		filters.add(new PropertyFilter("EQ_EnabledStateEnum_enable",EnabledStateEnum.ENABLED.toString()));//过滤掉，状态为禁用的记录；
		page = roleService.search(page, filters);
		return getEasyUIData(page);
	}
	
	/**
	 * 获取角色拥有的权限ID集合
	 * @param id
	 * @return
	 */
	@RequiresPermissions("sys:role:permView")
	@RequestMapping("{id}/json")
	@ResponseBody
	public List<Integer> getRolePermissions(@PathVariable("id") Integer id){
		List<Integer> permissionIdList=rolePermissionService.getPermissionIds(id);
		return permissionIdList;
	}
	
	/**
	 * 修改角色权限
	 * @param id
	 * @param newRoleList
	 * @return
	 */
	@RequiresPermissions("sys:role:permUpd")
	@RequestMapping(value = "{id}/updatePermission")
	@ResponseBody
	public String updateRolePermission(@PathVariable("id") Integer id,@RequestBody List<Integer> newRoleIdList,HttpSession session){
		List<Integer> oldRoleIdList=rolePermissionService.getPermissionIds(id);
		
		//获取application中的sessions
		@SuppressWarnings("rawtypes")
		HashSet sessions=(HashSet) session.getServletContext().getAttribute("sessions");
		if (null!=sessions) {//当前如果有正在使用的用户，需要更新正在使用的用户的权限
			
			@SuppressWarnings("unchecked")
			Iterator<Session> iterator= sessions.iterator();
			PrincipalCollection pc=null;
			
			//遍历sessions
			while(iterator.hasNext()){
				HttpSession s=(HttpSession) iterator.next();
				User user=(User) s.getAttribute("user");
				if(null != user && user.getId()==id){
					pc= (PrincipalCollection) s.getAttribute(String.valueOf(id));
					//清空该用户权限缓存
					rolePermissionService.clearUserPermCache(pc);
					s.removeAttribute(String.valueOf(id));
					break;
				}
			}
		}
		
		rolePermissionService.updateRolePermission(id,oldRoleIdList ,newRoleIdList);
		
		return "success";
	}
	
	/**
	 * 添加角色跳转
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:role:add")
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("role", new Role());
		model.addAttribute("action", "create");
		return "system/roleForm";
	}

	/**
	 * 添加角色
	 * @param role
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:role:add")
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid Role role,Model model) {
		roleService.save(role);
		return "success";
	}

	/**
	 * 修改角色跳转
	 * @param id
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:role:update")
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("role", roleService.get(id));
		model.addAttribute("action", "update");
		return "system/roleForm";
	}

	/**
	 * 修改角色
	 * @param role
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:role:update")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute("role") Role role,Model model) {
		roleService.save(role);
		return "success";
	}

	/**
	 * 删除角色
	 * @param id
	 * @return
	 */
	@RequiresPermissions("sys:role:delete")
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Integer id) {
		roleService.delete(id, UserUtil.getCurrentUser().getId());
		return "success";
	}
	
	@ModelAttribute
	public void getRole(@RequestParam(value = "id", defaultValue = "-1") Integer id, Model model) {
		if (id != -1) {
			model.addAttribute("role", roleService.get(id));
		}
	}
}
