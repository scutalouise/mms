package com.agama.pemm.controller;
import java.util.List;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.quartz.CronExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;






import com.agama.authority.entity.ScheduleJob;
import com.agama.authority.service.IScheduleJobService;
import com.agama.common.web.BaseController;
import com.agama.pemm.domain.DischargeTask;
import com.agama.pemm.domain.GitInfo;
import com.agama.pemm.service.IDischargeTaskService;
import com.agama.pemm.service.schedule.UpsDischargeJob;
@Controller
@RequestMapping("system/dischargeTask")
public class DischargeTaskController extends BaseController {
	@Autowired
	private IScheduleJobService scheduleJobService;
	
	@Autowired
	private IDischargeTaskService dischargeTaskService;
	
	@RequestMapping(value="list",method=RequestMethod.GET)
	public String List(){
		return "dischargeTask/dischargeTaskList";
		
	}
	@RequiresPermissions("sys:dischargeTask:add")
	@RequestMapping(value="addForm",method=RequestMethod.GET)
	public String createForm(Model model){
		model.addAttribute("action","add");
		return "dischargeTask/dischargeTaskForm";
	}
	@RequiresPermissions("sys:dischargeTask:add")
	@RequestMapping(value="add",method=RequestMethod.POST)
	@ResponseBody
	public String save(String name,String cronExpression,String description){
		ScheduleJob scheduleJob=new ScheduleJob();
		scheduleJob.setStatus("1");
		scheduleJob.setCronExpression(cronExpression);
		scheduleJob.setClassName(UpsDischargeJob.class.getName());
		scheduleJob.setDescription(description);
		scheduleJob.setGroup("upsDischargeJob");
		scheduleJob.setName(name);
		scheduleJobService.add(scheduleJob);
		scheduleJobService.stopJob(name, scheduleJob.getGroup());
		return "success";
	}
	@RequiresPermissions("sys:dischargeTask:update")
	@RequestMapping(value="update",method=RequestMethod.POST)
	@ResponseBody
	public String update(String name,String cronExpression,String description){
		ScheduleJob scheduleJob=new ScheduleJob();
		scheduleJob.setStatus("1");
		scheduleJob.setCronExpression(cronExpression);
		scheduleJob.setClassName(UpsDischargeJob.class.getName());
		scheduleJob.setDescription(description);
		scheduleJob.setGroup("upsDischargeJob");
		scheduleJob.setName(name);
		scheduleJobService.modify(scheduleJob);
		return "success";
	}
	@RequiresPermissions("sys:dischargeTask:selectedUpsDeviceList")
	@RequestMapping("selectedUpsDeviceList")
	public String selectedUpsDeviceList(){
		return "dischargeTask/selectedUpsDeviceList";
	}
	@RequestMapping("unSelectedUpsDeviceList")
	public String unSelectedUpsDeviceList(){
		return "dischargeTask/unSelectedUpsDeviceList";
		
	}
	
	@RequestMapping(value = "addSelectDevice",method=RequestMethod.POST)
	@ResponseBody
	public String addSelectDevice(String scheduleName,String scheduleGroup,@RequestParam("deviceIds[]") List<Integer> deviceIds) {
		dischargeTaskService.add(scheduleName,scheduleGroup,deviceIds);
		
		return "success";

	}
	@RequestMapping(value = "delSelectDevice",method=RequestMethod.POST)
	@ResponseBody
	public String delSelectDevice(@RequestParam("deviceIds") String deviceIds){
		dischargeTaskService.updateByDeviceIds(deviceIds);
		return "success";
	}
	@RequiresPermissions("sys:dischargeTask:delScheduleJob")
	@RequestMapping(value="delScheduleJob",method=RequestMethod.POST)
	@ResponseBody
	public String delScheduleJob(String scheduleName,String scheduleGroup){
		
		dischargeTaskService.delDischargeTask(scheduleName,scheduleGroup);
		return "success";
		
	}
	@RequiresPermissions("sys:dischargeTask:startDischargeTask")
	@RequestMapping(value="startDischargeTask",method=RequestMethod.POST)
	@ResponseBody
	public String startDischargeTask(String scheduleName,String scheduleGroup){
		scheduleJobService.restartJob(scheduleName, scheduleGroup);
		return "success";
		
	}
	@RequiresPermissions("sys:dischargeTask:stopDischargeTask")
	@RequestMapping(value="stopDischargeTask",method=RequestMethod.POST)
	@ResponseBody
	public String stopDischargeTask(String scheduleName,String scheduleGroup){
		scheduleJobService.stopJob(scheduleName, scheduleGroup);
		return "success";
		
	}
	@RequiresPermissions("sys:dischargeTask:update")
	@RequestMapping(value="updateForm",method=RequestMethod.GET)
	public String updateForm(Model model){
		model.addAttribute("action","update");
		return "dischargeTask/dischargeTaskForm";
	}
	@RequestMapping(value="validScheduleName",method=RequestMethod.POST)
	@ResponseBody
	public boolean validScheduleName(String scheduleName,String scheduleGroup,String type){
		DischargeTask dischargeTask=new DischargeTask();
		dischargeTask.setScheduleName(scheduleName);
		dischargeTask.setScheduleGroup(scheduleGroup);
		dischargeTask.setStatus(0);
		List<DischargeTask> dischargeTasks=dischargeTaskService.getListByDischargeTask(dischargeTask);
		if(dischargeTasks.size()>0){
			if(type.equals("update")){
				return true;
			}else{
			return false;
			}
		}else{
			return true;
		}
	}
	@RequestMapping(value="discharge",method=RequestMethod.POST)
	@ResponseBody
	public boolean discharge(Integer deviceId){
		dischargeTaskService.discharge(deviceId);
		return true;
	}

}
