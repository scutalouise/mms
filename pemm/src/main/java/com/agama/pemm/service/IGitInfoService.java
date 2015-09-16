package com.agama.pemm.service;

import java.util.List;

import com.agama.common.dao.utils.Page;
import com.agama.common.dao.utils.PropertyFilter;
import com.agama.common.service.IBaseService;
import com.agama.pemm.bean.DeviceType;
import com.agama.pemm.domain.Device;
import com.agama.pemm.domain.GitInfo;

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
	public List<GitInfo> getListByAreaInfoIdStr(String areaInfoIdStr);

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
	public List<GitInfo> getListByAreaInfoIdStrAndDeviceType(
			String areaInfoIdStr, DeviceType deviceType);

}
