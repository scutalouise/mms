package com.agama.itam.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agama.authority.entity.Organization;
import com.agama.authority.entity.User;
import com.agama.authority.service.IOrganizationService;
import com.agama.authority.service.IUserRoleService;
import com.agama.authority.service.IUserService;
import com.agama.aws.dao.MongoDao;
import com.agama.common.dao.utils.Page;
import com.agama.common.dao.utils.PropertyFilter;
import com.agama.common.enumbean.AlarmRuleType;
import com.agama.common.enumbean.DeviceUsedStateEnum;
import com.agama.common.enumbean.EnabledStateEnum;
import com.agama.common.enumbean.FirstDeviceType;
import com.agama.common.enumbean.MaintainWayEnum;
import com.agama.common.enumbean.ProblemStatusEnum;
import com.agama.common.enumbean.ReportWayEnum;
import com.agama.common.enumbean.SecondDeviceType;
import com.agama.common.enumbean.StatusEnum;
import com.agama.common.service.impl.BaseServiceImpl;
import com.agama.device.domain.CollectionDevice;
import com.agama.device.domain.HostDevice;
import com.agama.device.domain.NetworkDevice;
import com.agama.device.domain.PeDevice;
import com.agama.device.domain.UnintelligentDevice;
import com.agama.device.service.ICollectionDeviceService;
import com.agama.device.service.IDeviceService;
import com.agama.device.service.IHostDeviceService;
import com.agama.device.service.INetworkDeviceService;
import com.agama.device.service.IPeDeviceService;
import com.agama.device.service.IUnintelligentDeviceService;
import com.agama.device.service.IUserAdminOrgService;
import com.agama.device.service.IUserMaintainOrgService;
import com.agama.itam.bean.DeviceFaultTopNBean;
import com.agama.itam.bean.MaintenanceTopNBean;
import com.agama.itam.bean.SupplyTopNBean;
import com.agama.itam.dao.IProblemDao;
import com.agama.itam.domain.Arrange;
import com.agama.itam.domain.KnowledgeDetails;
import com.agama.itam.domain.Problem;
import com.agama.itam.domain.ProblemType;
import com.agama.itam.mongo.domain.ProblemHandle;
import com.agama.itam.service.IArrangeService;
import com.agama.itam.service.IKnowledgeDetailsService;
import com.agama.itam.service.IProblemHandleService;
import com.agama.itam.service.IProblemService;
import com.agama.itam.service.IProblemTypeService;
import com.agama.itam.util.SendMessageThread;
import com.agama.tool.utils.date.DateUtils;

/**
 * @Description:问题记录业务实现类
 * @Author:佘朝军
 * @Since :2016年1月19日 下午3:00:15
 */
@Transactional(readOnly = true)
@Service
public class ProblemServiceImpl extends BaseServiceImpl<Problem, Serializable> implements IProblemService {

	private static Logger logger = LoggerFactory.getLogger(ProblemServiceImpl.class);

	@Autowired
	private IProblemDao problemDao;
	@Autowired
	private IProblemTypeService ptService;
	@Autowired
	private IUserService userService;
	@Autowired
	private IDeviceService deviceService;
	@Autowired
	private IProblemHandleService phService;
	@Autowired
	private IUserRoleService urService;
	@Autowired
	private IArrangeService arrangeService;
	@Autowired
	private SendMessageThread sendMessageThread;
	@Autowired
	private IUserAdminOrgService uaoService;
	@Autowired
	private IUserMaintainOrgService umoService;
	@Autowired
	private IOrganizationService orgService;
	@Autowired
	private IKnowledgeDetailsService kdService;

	@Autowired
	private IHostDeviceService hds;
	@Autowired
	private ICollectionDeviceService cds;
	@Autowired
	private INetworkDeviceService nds;
	@Autowired
	private IPeDeviceService peds;
	@Autowired
	private IUnintelligentDeviceService uds;
	@Autowired
	private MongoDao mongoDao;

	@Override
	public Page<Problem> searchForPage(Page<Problem> page, List<PropertyFilter> filters) throws Exception{
		page = problemDao.findPage(page, filters);
		List<Problem> problemList = page.getResult();
		page.setResult(packageProblemList(problemList));
		return page;
	}

	@Override
	public Page<Problem> searchListForHandling(String problemCode, Integer problemTypeId, Integer resolveUserId, String enable, String recordTime,
			String recordEndTime, Page<Problem> page, String model) throws Exception {
		page = problemDao.searchListForHandling(problemCode, problemTypeId, resolveUserId, enable, recordTime, recordEndTime, page, model);
		List<Problem> problemList = page.getResult();
		page.setResult(packageProblemList(problemList));
		return page;
	}

	@Transactional(readOnly = false)
	public void saveProblem(Problem problem, User user) throws Exception {
		synchronized (problem) {
			problem.setProblemCode(DateUtils.formatDate(new Date(), "yyMMddHHmmSSS"));
		}
		problemDao.save(problem);
		ProblemHandle ph = new ProblemHandle();
		ph.setDescription("新建问题");
		ph.setEnable(ProblemStatusEnum.NEW);
		ph.setHandleTime(new Date());
		ph.setProblemId(problem.getId());
		if (user != null) {
			ph.setHandleUserId(user.getId());
			ph.setHandleUserName(user.getName());
		} else {
			ph.setHandleUserName("系统(system)");
		}
		phService.saveProblemHandle(ph);
		autoAssignProblem(problem);
	}

	@Transactional(readOnly = false)
	public void deleteProblem(Problem problem, User user) throws Exception {
		problem.setStatus(StatusEnum.DELETED);
		problemDao.update(problem);
		ProblemHandle ph = new ProblemHandle();
		ph.setDescription("删除问题记录");
		ph.setEnable(problem.getEnable());
		ph.setHandleTime(new Date());
		ph.setProblemId(problem.getId());
		if (user != null) {
			ph.setHandleUserId(user.getId());
			ph.setHandleUserName(user.getName());
		} else {
			ph.setHandleUserName("系统(system)");
		}
		phService.saveProblemHandle(ph);
	}

	@Transactional(readOnly = false)
	public void updateProblem(Problem problem, User user) throws Exception {
		problemDao.update(problem);
		ProblemHandle ph = new ProblemHandle();
		ph.setDescription("修改问题内容信息");
		ph.setEnable(problem.getEnable());
		ph.setHandleTime(new Date());
		ph.setProblemId(problem.getId());
		if (user != null) {
			ph.setHandleUserId(user.getId());
			ph.setHandleUserName(user.getName());
		} else {
			ph.setHandleUserName("系统(system)");
		}
		phService.saveProblemHandle(ph);

	}

	@Transactional(readOnly = false)
	public void shutdown(Problem problem, User user, Integer classificationId) throws Exception {
		if (problem.isEnableKnowledge()) {
			KnowledgeDetails kd = new KnowledgeDetails();
			ProblemType pt = ptService.get(problem.getProblemTypeId());
			kd.setTitle(pt.getName());
			kd.setKeyword(pt.getName());
			kd.setClassificationId(classificationId);
			ProblemHandle ph = phService.getLatestByProblemId(problem.getId());
			kd.setContent(ph.getDescription());
			kd.setCommitTime(new Date());
			kd.setCommitUserId(user.getId());
			kd.setEnable(EnabledStateEnum.ENABLED);
			kd.setProblemId(problem.getId());
			kd.setStatus(StatusEnum.NORMAL);
			kd.setUpdateTime(new Date());
			kd.setUpdateUserId(user.getId());
			kdService.save(kd);
		}
		updateProblem(problem, user);

	}

	private void autoAssignProblem(Problem problem) throws Exception {
		Map<String, String> deviceMap = deviceService.getDeviceMapByIdentifier(problem.getIdentifier());
		List<User> users = new ArrayList<User>();

		MaintainWayEnum maintenWay = MaintainWayEnum.valueOf(deviceMap.get("maintainWay"));
		if (maintenWay.equals(MaintainWayEnum.INNER)) {
			String orgIdStr = deviceMap.get("organizationId");
			if (StringUtils.isNotBlank(orgIdStr)) {
				users = uaoService.getUserListByOrgId(Integer.parseInt(orgIdStr));
			}
		} else {
			String maintainOrgIdStr = deviceMap.get("maintainOrgId");
			if (StringUtils.isNotBlank(maintainOrgIdStr)) {
				users = umoService.getUserListByOrgId(Integer.parseInt(maintainOrgIdStr));
			}
		}

		Integer userId = selectLessTaskUserId(users);
		if (userId != null) {
			problem.setResolveUserId(userId);
			problem.setEnable(ProblemStatusEnum.ASSIGNED);
			problemDao.update(problem);
			ProblemHandle reph = new ProblemHandle();
			reph.setDescription("系统自动分配问题");
			reph.setEnable(ProblemStatusEnum.ASSIGNED);
			reph.setHandleTime(new Date());
			reph.setProblemId(problem.getId());
			reph.setHandleUserName("系统(system)");
			phService.saveProblemHandle(reph);
			User receiveUser = userService.get(userId);
			if (!StringUtils.isBlank(receiveUser.getPhone())) {
				sendPhoneMessage(problem, receiveUser);
			}
		}

	}

	public void sendPhoneMessage(Problem problem, User receiveUser) {
		try {
			Map<String, Object> detailMap = deviceService.getDeviceByIdentifierForHandset(problem.getIdentifier());
			ProblemType pt = ptService.get(problem.getProblemTypeId());
			String smsContent = "设备故障报修：\n" + "设备名称：" + detailMap.get("name") + "\n设备编号：" + detailMap.get("identifier") + "\n设备品牌：" + detailMap.get("brand")
					+ "\n设备类型：" + ((FirstDeviceType) detailMap.get("firstDeviceType")).getName() + " - "
					+ ((SecondDeviceType) detailMap.get("secondDeviceType")).getName() + "\n所属网点：" + detailMap.get("hallName") + "\n问题类型：" + pt.getName()
					+ "\n问题描述：" + problem.getDescription() + "\n如收到短信，请回复 " + problem.getId() + " 进行问题响应!";
			sendMessageThread.setPhoneNumber(receiveUser.getPhone());
			sendMessageThread.setSmsContent(smsContent);
			Thread t = new Thread(sendMessageThread);
			t.start();
		} catch (Exception e) {
			logger.error("向 “" + receiveUser.getPhone() + "” 发送设备故障报修短信失败！");
			e.printStackTrace();
		}
	}

	private Integer selectLessTaskUserId(List<User> users) throws Exception {
		if (users != null && !users.isEmpty()) {
			List<Long> countList = new ArrayList<Long>();
			List<User> removeList = new ArrayList<User>();
			for (User user : users) {
				Arrange arrange = arrangeService.findByUserId(user.getId());
				if (arrange != null && arrange.isEnable() == true && arrange.checkWorkTime(new Date()) == true) {
					Long taskTotal = problemDao.countTaskTotalByUserId(user.getId());
					countList.add(taskTotal);
				} else {
					removeList.add(user);
				}
			}
			if (!countList.isEmpty()) {
				users.removeAll(removeList);
				User user = users.get(countList.indexOf(Collections.min(countList)));
				return user.getId();
			} else {
				return null;
			}
		} else {
			return null;
		}

	}

	private List<Problem> packageProblemList(List<Problem> list) throws Exception {
		for (Problem problem : list) {
			ProblemType type = ptService.get(problem.getProblemTypeId());
			problem.setProblemType(type.getName());
			problem.setRecordUserName("");
			problem.setResolveUserName("");
			if (problem.getRecordUserId() != null) {
				User recordUser = userService.get(problem.getRecordUserId());
				if (recordUser != null) {
					problem.setRecordUserName(recordUser.getName());
				}
			}
			if (problem.getResolveUserId() != null) {
				User resolveUser = userService.get(problem.getResolveUserId());
				if (resolveUser != null) {
					problem.setResolveUserName(resolveUser.getName());
				}
			}
			Map<String, String> deviceMap = deviceService.getDeviceMapByIdentifier(problem.getIdentifier());
			problem.setDeviceName(deviceMap.get("name"));
			String orgStr = deviceMap.get("organizationId");
			if (StringUtils.isNotBlank(orgStr)) {
				Organization org = orgService.get(Integer.parseInt(orgStr));
				problem.setOrgName(org.getOrgName());
			}
		}
		return list;
	}

	@Override
	public void updateChangeDeviceRepair(Problem problem, User user, String changeDeviceIdentifier) throws Exception {
		Map<String, String> deviceMap = deviceService.getDeviceMapByIdentifier(problem.getIdentifier());
		
		DeviceUsedStateEnum deviceUsedState = DeviceUsedStateEnum.valueOf(deviceMap.get("deviceUsedState"));
		Integer orgId = StringUtils.isBlank(deviceMap.get("organizationId")) ? null : Integer.parseInt(deviceMap.get("organizationId"));
		String usedUser = deviceMap.get("obtainUserId");
		FirstDeviceType newType = deviceService.getFirstDeviceTypeByIdentifier(changeDeviceIdentifier);
		//处理更新换上的设备信息
		switch (newType) {
		case COLLECTDEVICE:
			CollectionDevice cd = cds.getCollectionDeviceByIdentifier(changeDeviceIdentifier);
			if (deviceUsedState.equals(DeviceUsedStateEnum.USED)) {
				cd.setOrganizationId(orgId);
			} else {
				cd.setObtainUserId(StringUtils.isBlank(usedUser) ? null : Integer.parseInt(usedUser));;		
			}
			cd.setDeviceUsedState(deviceUsedState);
			cds.update(cd);
			break;
		case HOSTDEVICE:
			HostDevice hd = hds.getHostDeviceByIdentifier(changeDeviceIdentifier);
			if (deviceUsedState.equals(DeviceUsedStateEnum.USED)) {
				hd.setOrganizationId(orgId);
			} else {
				hd.setObtainUserId(StringUtils.isBlank(usedUser) ? null : Integer.parseInt(usedUser));;		
			}
			hd.setDeviceUsedState(deviceUsedState);
			hds.update(hd);
			break;
		case NETWORKDEVICE:
			NetworkDevice nd = nds.getNetworkDeviceByIdentifier(changeDeviceIdentifier);
			if (deviceUsedState.equals(DeviceUsedStateEnum.USED)) {
				nd.setOrganizationId(orgId);
			} else {
				nd.setObtainUserId(StringUtils.isBlank(usedUser) ? null : Integer.parseInt(usedUser));;		
			}
			nd.setDeviceUsedState(deviceUsedState);
			nds.update(nd);
			break;
		case PEDEVICE:
			PeDevice ped = peds.getPeDeviceByIdentifier(changeDeviceIdentifier);
			if (deviceUsedState.equals(DeviceUsedStateEnum.USED)) {
				ped.setOrganizationId(orgId);
			} else {
				ped.setObtainUserId(StringUtils.isBlank(usedUser) ? null : Integer.parseInt(usedUser));;		
			}
			ped.setDeviceUsedState(deviceUsedState);
			peds.update(ped);
			break;
		case UNINTELLIGENTDEVICE:
			UnintelligentDevice ud =  uds.getUnintelligentDeviceByIdentifier(changeDeviceIdentifier);
			if (deviceUsedState.equals(DeviceUsedStateEnum.USED)) {
				ud.setOrganizationId(orgId);
			} else {
				ud.setObtainUserId(StringUtils.isBlank(usedUser) ? null : Integer.parseInt(usedUser));;		
			}
			ud.setDeviceUsedState(deviceUsedState);
			uds.update(ud);
			break;
		}
		
		//更新换下设备的信息
		FirstDeviceType oldType = deviceService.getFirstDeviceTypeByIdentifier(problem.getIdentifier());
		switch (oldType) {
		case COLLECTDEVICE:
			CollectionDevice cd = cds.getCollectionDeviceByIdentifier(problem.getIdentifier());
			cd.setObtainUserId(user.getId());		
			cd.setDeviceUsedState(DeviceUsedStateEnum.WAITREPAIR);
			cds.update(cd);
			break;
		case HOSTDEVICE:
			HostDevice hd = hds.getHostDeviceByIdentifier(problem.getIdentifier());
			hd.setObtainUserId(user.getId());		
			hd.setDeviceUsedState(DeviceUsedStateEnum.WAITREPAIR);
			hds.update(hd);
			break;
		case NETWORKDEVICE:
			NetworkDevice nd = nds.getNetworkDeviceByIdentifier(problem.getIdentifier());
			nd.setObtainUserId(user.getId());		
			nd.setDeviceUsedState(DeviceUsedStateEnum.WAITREPAIR);
			nds.update(nd);
			break;
		case PEDEVICE:
			PeDevice ped = peds.getPeDeviceByIdentifier(problem.getIdentifier());
			ped.setObtainUserId(user.getId());		
			ped.setDeviceUsedState(DeviceUsedStateEnum.WAITREPAIR);
			peds.update(ped);
			break;
		case UNINTELLIGENTDEVICE:
			UnintelligentDevice ud =  uds.getUnintelligentDeviceByIdentifier(problem.getIdentifier());
			ud.setObtainUserId(user.getId());		
			ud.setDeviceUsedState(DeviceUsedStateEnum.WAITREPAIR);
			uds.update(ud);
			break;
		}
		
		problem.setIdentifier(changeDeviceIdentifier);
		problemDao.update(problem);
		
	}

	@Override
	public void saveAlarmProblem(List<Map<String, String>> list) throws Exception {
		for (Map<String, String> map : list) {
			String typeStr = map.get("alarmRuleType");
			if (StringUtils.isNoneBlank(typeStr)) {
				AlarmRuleType type = AlarmRuleType.valueOf(typeStr);
				ProblemType pt = ptService.getUniqueByAlarmType(type);
				if (pt != null) {
					Problem p = new Problem();
					p.setProblemTypeId(pt.getId());
					p.setDescription(map.get("des"));
					p.setEnable(ProblemStatusEnum.NEW);
					p.setEnableKnowledge(false);
					p.setIdentifier(map.get("identifier"));
					p.setRecordTime(new Date());
					p.setRecordUserId(null);
					p.setReportUser("系统自动巡检上报");
					p.setReportWay(ReportWayEnum.AUTOCHECK);
					p.setResponsed(false);
					p.setStatus(StatusEnum.NORMAL);
				}
				
			}
			
		}
		
	}

	@Override
	public List<SupplyTopNBean> findDataBySQL(HttpServletRequest request) {
		return problemDao.findDataBySQL(request);
	}
	
	@Override
	public List<MaintenanceTopNBean> findMaintenanceDataBySQL(HttpServletRequest request) {
		return problemDao.findMaintenanceDataBySQL(request);
	}

	@Override
	public List<DeviceFaultTopNBean> findDeviceFaultDataBySQL(HttpServletRequest request) {
		return problemDao.findDeviceFaultDataBySQL(request);
	}

	@Override
	public Problem getDetailsById(Long id) throws Exception {
		Problem problem = problemDao.find(id);
		if (problem != null) {
			List<Problem> list = new ArrayList<Problem>();
			list.add(problem);
			problem = packageProblemList(list).get(0);
		}
		return problem;
	}

}
