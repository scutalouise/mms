package com.agama.pemm.service;

import java.util.List;

import com.agama.common.dao.utils.Page;
import com.agama.common.dao.utils.PropertyFilter;
import com.agama.common.service.IBaseService;
import com.agama.pemm.domain.GitInfo;

import com.agama.common.enumbean.DeviceType;

public interface IGitInfoService extends IBaseService<GitInfo, Integer> {
	/**
	 * 根据ids修改状态
	 * 
	 * @param ids
	 */
	public void updateStatusByIds(String ids);

	/**
	 * 根据状态查询集合
	 * 
	 * @param status
	 * @return
	 */
	public List<GitInfo> getListByStatus(Integer status);

	/**
	 * 根据区域ID字符串查询出所有区域的设备列表
	 * 
	 * @param areaInfoIdStr
	 */
	public List<GitInfo> getListByOrganizationIdStr(String organizationIdStr);

	/**
	 * 根据区域ID查询集合
	 * 
	 * @param areaInfoId
	 * @param page
	 * @param filters
	 * @return
	 */
	public Page<GitInfo> searchListByAreaInfoId(Integer areaInfoId,
			Page<GitInfo> page, List<PropertyFilter> filters);

	/**
	 * 查找是否是否ip重复，id为gitInfoId的设备除外
	 * 
	 * @param ip
	 * @param gitInfoId
	 * @return
	 */
	public GitInfo getGitInfoByIpAndId(String ip, Integer gitInfoId);

	/**
	 * 更具区域ID字符串和设备类型查询粗区域下面的设备列表
	 * 
	 * @param areaInfoIdStr
	 * @param deviceType
	 * @return
	 */
	public List<GitInfo> getListByOrganizationIdStrAndDeviceType(
			String areaInfoIdStr, DeviceType deviceType);

	/**
	 * @Description: 根据网关Id字符串查询集合
	 * @param gitIds
	 * @return
	 * @Since :2015年11月2日 下午5:27:32
	 */
	public List<GitInfo> getListByIds(String gitIds);

	/**
	 * @Description: 根据网关id集合查询网关挂接设备的状态信息
	 * @param gitInfoIds
	 * @return
	 * @Since :2015年11月3日 下午6:12:56
	 */
	public List<GitInfo> getDeviceStatusByGitInfoIds(String[] gitInfoIds);

}
