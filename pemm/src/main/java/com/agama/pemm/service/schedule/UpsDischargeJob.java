package com.agama.pemm.service.schedule;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;

import com.agama.authority.entity.ScheduleJob;
import com.agama.pemm.bean.DischargeType;
import com.agama.pemm.domain.Device;
import com.agama.pemm.domain.DischargeLog;
import com.agama.pemm.protocol.snmp.UpsOidInfo;
import com.agama.pemm.service.IDeviceService;
import com.agama.pemm.service.IDischargeLogService;
import com.agama.pemm.utils.SNMPUtil;

/**
 * @Description:UPS放电定时任务
 * @Author:ranjunfeng
 * @Since :2015年11月25日 上午9:27:07
 */

public class UpsDischargeJob implements Job {
	


	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		ApplicationContext applicationContext=null;
	    try {
	      applicationContext=getApplicationContext(context);
	    } catch (Exception e) {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
	    }
	    IDeviceService deviceService=(IDeviceService)applicationContext.getBean("deviceServiceImpl");
	    IDischargeLogService dischargeLogService=(IDischargeLogService)applicationContext.getBean("dischargeLogServiceImpl");
		ScheduleJob scheduleJob = (ScheduleJob) context.getMergedJobDataMap()
				.get("scheduleJob");
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy年MM月dd日 HH时mm分ss秒");
		System.out.println("任务名称 = [" + scheduleJob.getName() + "]" + " 在 "
				+ dateFormat.format(new Date()) + " 时运行");
		List<Device> deviceList=deviceService.getDeviceListByScheduleNameAndScheduleGroup(scheduleJob.getName(), scheduleJob.getGroup());
		for (Device device : deviceList) {
			SNMPUtil snmpUtil=new SNMPUtil();
			try {
				snmpUtil.sendSetCommand(device.getGitInfo().getIp(),UpsOidInfo.dischargeTest.getOid()+"."+device.getDeviceIndex(),2);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			DischargeLog dischargeLog=new DischargeLog();
			dischargeLog.setDevice(device);
			dischargeLog.setDischargeType(DischargeType.AUTOMATIC);
			dischargeLog.setStatus(0);
			dischargeLog.setDischargeDate(new Date());
			dischargeLogService.save(dischargeLog);
			
		}
		
	}

	private static final String APPLICATION_CONTEXT_KEY = "applicationContextKey";
	 private ApplicationContext getApplicationContext(JobExecutionContext context) throws Exception {
	      ApplicationContext appCtx = null;
	      appCtx = (ApplicationContext) context.getScheduler().getContext().get(APPLICATION_CONTEXT_KEY);
	      if (appCtx == null) {
	        throw new JobExecutionException("No application context available in scheduler context for key \"" + APPLICATION_CONTEXT_KEY + "\"");
	      }
	      return appCtx;
	  }

}
