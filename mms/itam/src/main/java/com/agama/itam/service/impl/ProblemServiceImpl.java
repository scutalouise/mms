package com.agama.itam.service.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agama.authority.entity.User;
import com.agama.authority.service.IUserService;
import com.agama.common.dao.utils.Page;
import com.agama.common.dao.utils.PropertyFilter;
import com.agama.common.enumbean.ProblemStatusEnum;
import com.agama.common.enumbean.StatusEnum;
import com.agama.common.service.impl.BaseServiceImpl;
import com.agama.device.service.IDeviceService;
import com.agama.itam.dao.IProblemDao;
import com.agama.itam.domain.Problem;
import com.agama.itam.domain.ProblemType;
import com.agama.itam.mongo.domain.ProblemHandle;
import com.agama.itam.service.IProblemHandleService;
import com.agama.itam.service.IProblemService;
import com.agama.itam.service.IProblemTypeService;

/**
 * @Description:问题记录业务实现类
 * @Author:佘朝军
 * @Since :2016年1月19日 下午3:00:15
 */
@Transactional(readOnly = true)
@Service
public class ProblemServiceImpl extends BaseServiceImpl<Problem, Serializable> implements IProblemService {

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

	@Override
	public List<Problem> getAllList() throws Exception {
		List<Problem> problemList = problemDao.getAllList();
		for (Problem problem : problemList) {
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
		}
		return problemList;
	}

	@Override
	public Page<Problem> searchForPage(Page<Problem> page, List<PropertyFilter> filters) {
		page = problemDao.findPage(page, filters);
		List<Problem> problemList = page.getResult();
		for (Problem problem : problemList) {
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
		}
		return page;
	}

	@Override
	public Page<Problem> searchListForHandling(String identifier, Integer problemTypeId, Integer enable, String recordTime, String recordEndTime,
			List<Integer> roleIds, Page<Problem> page) throws Exception {
		page = problemDao.searchListForHandling(identifier, problemTypeId, enable, recordTime, recordEndTime, page);
		List<Problem> problemList = page.getResult();
		for (Problem problem : problemList) {
			Map<String, String> deviceMap = deviceService.getDeviceMapByIdentifier(problem.getIdentifier());
			String roleIdStr = deviceMap.get("roleId");
			if (!StringUtils.isBlank(roleIdStr) && roleIds.contains(Integer.parseInt(roleIdStr))) {
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

			} else {
				problemList.remove(problem);
			}
		}
		return page;
	}

	@Transactional(readOnly = false)
	public void saveProblem(Problem problem, User user) throws Exception {
		Map<String, String> deviceMap = deviceService.getDeviceMapByIdentifier(problem.getIdentifier());
		// TODO 运维排程
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

}
