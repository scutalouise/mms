package com.agama.pemm.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.hibernate.engine.spi.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;







import com.agama.authority.entity.User;
import com.agama.authority.service.IUserService;
import com.agama.common.dao.utils.Page;
import com.agama.common.dao.utils.PropertyFilter;
import com.agama.common.domain.StateEnum;
import com.agama.common.web.BaseController;
import com.agama.pemm.domain.GitInfo;
import com.agama.pemm.service.IGitInfoService;

@Controller
@RequestMapping("gitInfo")
public class GitInfoController extends BaseController {
	@Autowired
	private IGitInfoService gitInfoService;
	@Autowired
	private IUserService userService;
	@RequestMapping(method=RequestMethod.GET)
	public String list(){
		return "gitInfo/gitInfoList";
	}
	@RequiresPermissions("sys:gitInfo:view")
	@RequestMapping(value="json",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getData(Integer areaInfoId,HttpServletRequest request){
		Page<GitInfo> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		
		page = gitInfoService.search(page, filters);
		return getEasyUIData(page);
	}
	
	@RequestMapping(value="userjson/{organizationId}",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> collectUserList(@PathVariable("organizationId") Integer organizationId,HttpServletRequest request){
		Page<User> page = getPage(request);
		page=userService.getUsersByOrganizationId(page,organizationId);
		return getEasyUIData(page);
	}
	
	@RequiresPermissions("sys:gitInfo:add")
	@RequestMapping(value="create",method=RequestMethod.GET)
	public  String createForm(Model model){
		model.addAttribute("user", new User());
		model.addAttribute("action", "create");
		return "gitInfo/gitInfoForm";
	}
	@RequiresPermissions("sys:gitInfo:add")
	@RequestMapping(value="create",method=RequestMethod.POST)
	@ResponseBody
	public String create(@Valid GitInfo gitInfo,Model model){
		gitInfo.setStatus(0);
		gitInfo.setCurrentState(StateEnum.good);
		gitInfoService.save(gitInfo);
		return "success";
	}
	/**
	 * 修改GIT设备
	 * @param id
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:gitInfo:update")
	@RequestMapping(value="update/{id}",method=RequestMethod.GET)
	public String updateForm(@PathVariable("id") Integer id,Model model){
		GitInfo gitInfo=gitInfoService.get(id);
		model.addAttribute("gitInfo",gitInfo );
		model.addAttribute("action", "update");
		return "gitInfo/gitInfoForm";
	}
	@RequiresPermissions("sys:gitInfo:update")
	@RequestMapping(value="update",method=RequestMethod.POST)
	@ResponseBody
	public String update(@Valid GitInfo gitInfo,Model model){
		gitInfo.setStatus(0);
		gitInfoService.update(gitInfo);
		return "success";
	}
	@RequiresPermissions("sys:gitInfo:delete")
	@RequestMapping(value="delete")
	@ResponseBody
	public String delete(String ids){
		gitInfoService.updateStatusByIds(ids);
		return "success";
	}
	@RequestMapping(value="validVailable",method=RequestMethod.POST)
	@ResponseBody
	public boolean validVailable(String ip,Integer gitInfoId){
		boolean result=false;
		GitInfo gitInfo=gitInfoService.getGitInfoByIpAndId(ip,gitInfoId);
		if(gitInfo!=null){
			result= false;
		}else{
			result=true;
		}
		return result;
	}
	
	

}
