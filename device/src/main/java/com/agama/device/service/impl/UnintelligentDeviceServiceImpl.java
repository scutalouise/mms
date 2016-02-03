package com.agama.device.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agama.common.service.impl.BaseServiceImpl;
import com.agama.device.dao.IUnintelligentDeviceDao;
import com.agama.device.domain.UnintelligentDevice;
import com.agama.device.service.IUnintelligentDeviceService;

@Service
public class UnintelligentDeviceServiceImpl extends BaseServiceImpl<UnintelligentDevice, Integer>
		implements IUnintelligentDeviceService {

	@Autowired
	private IUnintelligentDeviceDao unintelligentDeviceDao;

	@Override
	public void updateManager(Integer id, Integer managerId, String managerName) {
		String hql = "update UnintelligentDevice set managerId=?0,managerName=?1 where id=?2";
		unintelligentDeviceDao.batchExecute(hql, managerId, managerName, id);
	}

	@Override
	public void updateRole(Integer id, Integer roleId, String roleName) {
		String hql = "update UnintelligentDevice set roleId=?0,roleName=?1 where id=?2";
		unintelligentDeviceDao.batchExecute(hql, roleId, roleName, id);
	}

	@Override
	public void updateUserDeviceType(Integer id, Integer userDeviceTypeId, String userDeviceTypeName) {
		String hql = "update UnintelligentDevice set userDeviceTypeId=?0,userDeviceTypeName=?1 where id=?2";
		unintelligentDeviceDao.batchExecute(hql, userDeviceTypeId, userDeviceTypeName, id);
	}

}
