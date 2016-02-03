package com.agama.authority.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.agama.authority.service.IAuthorityTransmitService;
import com.agama.authority.service.IUserOrgService;
import com.agama.authority.service.IUserRoleService;
import com.agama.authority.service.IUserService;
import com.agama.authority.service.UserRealm.ShiroUser;
import com.agama.common.dao.utils.Page;
import com.agama.common.dao.utils.PropertyFilter;
import com.agama.common.web.BaseController;

/**
 * 用户controller
 * @author ty
 * @date 2015年1月13日
 */
@Controller
@RequestMapping("system/user")
public class UserController extends BaseController {

	@Autowired
	private IUserService userService;

	@Autowired
	private IUserRoleService userRoleService;
	
	@Autowired
	private IUserOrgService userOrgService;
	
	@Autowired
	private IAuthorityTransmitService authorityTransmitService;
	
	@Value("#{configProperties[initPwd]}")
	private String initPwd;

	
	

	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list(HttpServletRequest request) {
		isTransmit(request);
		return "system/userList";
	}

	/**
	 * 获取用户json
	 */
	@RequiresPermissions("sys:user:view")
	@RequestMapping(value="json",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getData(HttpServletRequest request) {
		Page<User> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = userService.search(page, filters);
		isTransmit(request);
		return getEasyUIData(page);
	}

	private void isTransmit(HttpServletRequest request) {
		//新增加一个对于权限移交的支持，加入一个is_transmit参数，并判断是否有权限移交：鉴别只能权限移交一次，不能多次或重复的移交；
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		boolean is_exists = authorityTransmitService.isExistsByProperty("transmitUserId", user.getId());
		request.getSession().setAttribute("is_transmit", is_exists);
	}

	/**
	 * 添加用户跳转
	 * 
	 * @param model
	 */
	@RequiresPermissions("sys:user:add")
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("action", "create");
		return "system/userForm";
	}

	/**
	 * 添加用户
	 * 
	 * @param user
	 * @param model
	 */
	@RequiresPermissions("sys:user:add")
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid User user, Model model) {
		userService.save(user);
		return "success";
	}

	/**
	 * 修改用户跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:user:update")
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("user", userService.get(id));
		model.addAttribute("action", "update");
		return "system/userForm";
	}

	/**
	 * 修改用户
	 * 
	 * @param user
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:user:update")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody User user,Model model) {
		userService.update(user);
		return "success";
	}

	/**
	 * 删除用户
	 * 
	 * @param id
	 * @return
	 */
	@RequiresPermissions("sys:user:delete")
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Integer id) {
		userService.delete(id);
		return "success";
	}

	/**
	 * 弹窗页-用户拥有的角色
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:user:roleView")
	@RequestMapping(value = "{userId}/userRole")
	public String getUserRole(@PathVariable("userId") Integer id, Model model) {
		model.addAttribute("userId", id);
		return "system/userRoleList";
	}
	/**
	 * 弹窗页-用户所在机构
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:user:orgView")
	@RequestMapping(value = "{userId}/userOrg")
	public String getUserOrg(@PathVariable("userId") Integer id, Model model) {
		model.addAttribute("userId", id);
		return "system/userOrgList";
	}

	/**
	 * 获取用户拥有的角色ID集合
	 * 
	 * @param id
	 * @return
	 */
	@RequiresPermissions("sys:user:roleView")
	@RequestMapping(value = "{id}/role")
	@ResponseBody
	public List<Integer> getRoleIdList(@PathVariable("id") Integer id) {
		return userRoleService.getRoleIdList(id);
	}
	/**
	 * 获取用户拥有的机构ID集合
	 * @param id
	 * @return
	 */
	@RequiresPermissions("sys:user:orgView")
	@RequestMapping(value = "{id}/org")
	@ResponseBody
	public List<Integer> getOrgIdList(@PathVariable("id") Integer id) {
		return userOrgService.getOrgIdList(id);
	}

	/**
	 * 修改用户拥有的角色
	 * 
	 * @param id
	 * @param newRoleList
	 * @return
	 */
	@RequiresPermissions("sys:user:roleUpd")
	@RequestMapping(value = "{id}/updateRole")
	@ResponseBody
	public String updateUserRole(@PathVariable("id") Integer id,@RequestBody List<Integer> newRoleList) {
		userRoleService.updateUserRole(id, userRoleService.getRoleIdList(id),newRoleList);
		return "success";
	}
	/**
	 * 修改用户所在的部门
	 * 
	 * @param id
	 * @param newRoleList
	 * @return
	 */
	@RequiresPermissions("sys:user:orgUpd")
	@RequestMapping(value = "{id}/updateOrg")
	@ResponseBody
	public String updateUserOrg(@PathVariable("id") Integer id,@RequestBody List<Integer> newRoleList) {
		userOrgService.updateUserOrg(id,newRoleList);
		return "success";
	}

	/**
	 * 修改密码跳转
	 */
	@RequestMapping(value = "updatePwd", method = RequestMethod.GET)
	public String updatePwdForm(Model model, HttpSession session) {
		model.addAttribute("user", (User) session.getAttribute("user"));
		return "system/updatePwd";
	}

	/**
	 * 修改密码
	 */
	@RequestMapping(value = "updatePwd", method = RequestMethod.POST)
	@ResponseBody
	public String updatePwd(String oldPassword,@Valid @ModelAttribute @RequestBody User user, HttpSession session) {
		if (userService.checkPassword((User) session.getAttribute("user"),oldPassword)) {
			userService.updatePwd(user);
			session.setAttribute("user", user);
			return "success";
		} else {
			return "fail";
		}

	}

	/**
	 * Ajax请求校验loginName是否唯一。
	 */
	@RequestMapping(value = "checkLoginName")
	@ResponseBody
	public String checkLoginName(String loginName) {
		if (userService.getUser(loginName) == null) {
			return "true";
		} else {
			return "false";
		}
	}

	/**
	 * ajax请求校验原密码是否正确
	 * 
	 * @param oldPassword
	 * @param request
	 * @return
	 */
//	@RequiresPermissions("sys:user:update")
	@RequestMapping(value = "checkPwd")
	@ResponseBody
	public String checkPwd(String oldPassword, HttpSession session) {
		if (userService.checkPassword((User) session.getAttribute("user"),oldPassword)) {
			return "true";
		} else {
			return "false";
		}
	}
	
	/**
	 * 所有RequestMapping方法调用前的Model准备方法, 实现Struts2
	 * Preparable二次部分绑定的效果,先根据form的id从数据库查出Task对象,再把Form提交的内容绑定到该对象上。
	 * 因为仅update()方法的form中有id属性，因此仅在update时实际执行.
	 */
	@ModelAttribute
	public void getUser(@RequestParam(value = "id", defaultValue = "-1") Integer id,Model model) {
		if (id != -1) {
			model.addAttribute("user", userService.get(id));
		}
	}
	
	@RequiresPermissions("sys:user:resetPwd")
	@RequestMapping(value = "resetPwd/{id}")
	@ResponseBody
	public Map<String,Object> resetPwd(@PathVariable("id") Integer id){
		Map<String,Object> map=new HashMap<String, Object>();
		User user=userService.get(id);
		user.setPlainPassword(initPwd);
	
		userService.updatePwd(user);
		map.put("result", "success");
		map.put("message", "用户【"+user.getName()+"】初始化密码为:【"+initPwd+"】");
		return map;
		
	}
	
	/**
	 * @Description:根据客户端传递过来的参数判断，是否需要进行权限移交
	 * @return	map HashMap<String,Object>
	 * @Since :2015年12月29日 下午5:14:16
	 */
	@RequiresPermissions("sys:user:transmit")
	@RequestMapping(value="transmit/{id}")
	@ResponseBody
	public Map<String,Object> transmit(@PathVariable("id") Integer acceptUserId ){
		/**
		 * 处理过程：
		 * 0.权限移交不能是本人;
		 * 1.查找到当前要使用权限移交的人员的所有角色;
		 * 2.需要接受权限移交的人，userId;
		 * 3.判断，是否使用权限移交的人，已经有移交权限给其他人，如果没有则进行4,否者，提示用户需要先撤销移交权限;
		 * 4.将使用权限移交的人的角色，均赋值给接受权限移交的人;
		 */
		Map<String,Object> map = new HashMap<String,Object>();
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		map.put("result", "success");
		User acceptUser = userService.get(acceptUserId);
		if(null != acceptUserId && user.loginName.equals(acceptUser.getLoginName()) ){
			map.put("message", "您不能将权限移交给自己...");
		}else{
			int statusCode = userRoleService.transmitRole(user.getId(), acceptUserId);
			if(statusCode == -1){
				map.put("message", "用户【"+acceptUser.getName()+"】所拥有的权限大于您，不需要再移交权限。");
			}else if(statusCode == 1){
				map.put("message", "用户【"+acceptUser.getName()+"】已经接受了:【"+user.getName()+"】"+"移交的所有权限，【"+user.getName()+ "】下次登录生效");
			}
		}
		return map;
	}
	
	/**
	 * @Description:撤销移交授权
	 * @return
	 * @Since :2015年12月30日 下午2:56:06
	 */
	@RequiresPermissions("sys:user:transmit")
	@RequestMapping(value="cancelTransmit")
	@ResponseBody
	public Map<String,Object> cancelTransmit(){
		HashMap<String,Object> map = new HashMap<String,Object>();
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		Integer acceptUserId = userRoleService.cancelTransmitRole(user.getId());
		map.put("result", "success");
		User acceptUser = userService.get(acceptUserId);
		map.put("message", "用户【"+acceptUser.getName()+"】已经被撤销了所有权限，下次登录生效");
		return map;
	}

}
