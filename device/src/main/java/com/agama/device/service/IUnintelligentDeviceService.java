package com.agama.device.service;

import com.agama.common.service.IBaseService;
import com.agama.device.domain.UnintelligentDevice;

public interface IUnintelligentDeviceService extends IBaseService<UnintelligentDevice, Integer> {

	/**
	 * 修改管理员
	 * 
	 * @param id
	 * @param managerId
	 * @param managerName
	 */
	public void updateManager(Integer id, Integer managerId, String managerName);

	/**
	 * 修改角色
	 * 
	 * @param id
	 * @param roleId
	 * @param roleName
	 */
	public void updateRole(Integer id, Integer roleId, String roleName);

	/**
	 * 修改用户自定义设备类型
	 * 
	 * @param id
	 * @param userDeviceTypeId
	 * @param userDeviceTypeName
	 */
	public void updateUserDeviceType(Integer id, Integer userDeviceTypeId, String userDeviceTypeName);

}
