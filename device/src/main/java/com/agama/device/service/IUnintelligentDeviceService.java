package com.agama.device.service;

import java.util.List;
import java.util.Map;

import com.agama.authority.entity.User;
import com.agama.common.dao.utils.Page;
import com.agama.common.enumbean.DeviceUsedStateEnum;
import com.agama.common.enumbean.StatusEnum;
import com.agama.common.service.IBaseService;
import com.agama.device.domain.UnintelligentDevice;

public interface IUnintelligentDeviceService extends
		IBaseService<UnintelligentDevice, Integer> {

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
	public void updateUserDeviceType(Integer id, Integer userDeviceTypeId,
			String userDeviceTypeName);

	public UnintelligentDevice getUnintelligentDeviceByIdentifier(
			String identifier);

	public List<UnintelligentDevice> getUnintelligentDeviceByOrgId(int orgId);

	public List<Object> getNameAndIdentifierByOrgId(int orgId);

	public List<UnintelligentDevice> getAllList();

	/**
	 * @Description:根据设备编号获取设备详情信息
	 * @param identifier
	 *            设备编号
	 * @return List
	 * @Since :2016年1月18日 上午9:33:01
	 */
	public Map<String, Object> getDetailByIdentifierForHandset(String identifier);

	/**
	 * @Description:查询已经领用的非智能设备分页集合
	 * @param page
	 * @param organizationId
	 * @param status
	 * @param deviceUsedState 设备状态
	 * @return
	 * @Since :2016年2月24日 上午10:20:36
	 */
	public Page<UnintelligentDevice> searchObtainUnintelligentDeviceList(
			Page<UnintelligentDevice> page, String organizationId,
			StatusEnum status, DeviceUsedStateEnum deviceUsedState,User user);

	/**
	 * @Description:领用设备
	 * @param orgId
	 * @param unintelligentDeviceIdList
	 * @Since :2016年2月24日 下午4:24:12
	 */
	public void unintelligentDeviceObtain(Integer orgId,String type,
			List<Integer> unintelligentDeviceIdList,User user);
	/**
	 * @Description:退回非智能设备
	 * @param unintelligentDeviceIdList
	 * @Since :2016年2月24日 下午4:24:43
	 */
	public void backUnintelligentDevice(List<Integer> unintelligentDeviceIdList);
	/**
	 * @Description:非智能设备报废
	 * @param unintelligentDeviceIdList
	 * @Since :2016年2月25日 上午10:20:58
	 */
	public void scrappedUnintelligentDevice(List<Integer> unintelligentDeviceIdList);
	/**
	 * @Description:非智能设备审核
	 * @param type
	 * @param unintelligentDeviceIdList
	 * @Since :2016年3月1日 下午5:08:36
	 */
	public void unintelligentDeviceAudit(Integer type,
			List<Integer> unintelligentDeviceIdList);
	
	public List<UnintelligentDevice> getListByDeviceUsedStateEnum(DeviceUsedStateEnum deviceUsedStateEnum);
	
	public List<UnintelligentDevice> getListByObtainUser(Integer userId);
	/**
	 * @Description:根据设备id集合修改设备使用状态
	 * @param unintelligentDeviceIdList
	 * @param putinstorage
	 * @Since :2016年3月10日 上午11:45:31
	 */
	public void updateDeviceUsedStateByDeviceIdList(
			List<Integer> unintelligentDeviceIdList,
			DeviceUsedStateEnum deviceUsedState);
	
	public List<UnintelligentDevice> getListByQueryMap(Map<String, Object> map);


}
