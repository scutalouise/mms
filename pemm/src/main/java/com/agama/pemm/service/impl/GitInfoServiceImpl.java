package com.agama.pemm.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agama.common.dao.utils.Page;
import com.agama.common.dao.utils.PropertyFilter;
import com.agama.common.enumbean.DeviceInterfaceType;
import com.agama.common.enumbean.DeviceType;
import com.agama.common.service.impl.BaseServiceImpl;
import com.agama.pemm.dao.IAcStatusDao;
import com.agama.pemm.dao.IAlarmLogDao;
import com.agama.pemm.dao.IDeviceDao;
import com.agama.pemm.dao.IGitInfoDao;
import com.agama.pemm.dao.ISwitchInputStatusDao;
import com.agama.pemm.dao.IThStatusDao;
import com.agama.pemm.dao.IUpsStatusDao;
import com.agama.pemm.domain.AcStatus;
import com.agama.pemm.domain.Device;
import com.agama.pemm.domain.GitInfo;
import com.agama.pemm.domain.SwitchInputStatus;
import com.agama.pemm.domain.ThStatus;
import com.agama.pemm.domain.UpsStatus;
import com.agama.pemm.service.IGitInfoService;

@Service("gitInfoService")
@Transactional
public class GitInfoServiceImpl extends BaseServiceImpl<GitInfo, Integer>
		implements IGitInfoService {

	@Autowired
	private IGitInfoDao gitInfoDao;
	@Autowired
	private IUpsStatusDao upsStatusDao;
	@Autowired
	private IThStatusDao thStatusDao;
	@Autowired
	private ISwitchInputStatusDao switchInputStatusDao;
	@Autowired
	private IAcStatusDao acStatusDao;
	
	@Autowired
	private IDeviceDao deviceDao;
	@Autowired
	private IAlarmLogDao alarmLogDao;

	@Override
	public void updateStatusByIds(String ids) {
		gitInfoDao.updateStatusByIds(ids);
		
		deviceDao.updateStatusByGitInfoIds(ids, 1);
		// 逻辑删除UPS状态信息
		upsStatusDao.updateStatusByGitInfoIds(ids, 1);
		// 逻辑删除温湿度状态信息
		thStatusDao.updateStatusByGitInfoIds(ids, 1);
		// 逻辑删除开关量输出状态信息
		switchInputStatusDao.updateStatusByGitInfoIds(ids, 1);
		// 逻辑删除日志信息
		alarmLogDao.updateStatusByGitInfoIds(ids, 1);
	}

	public List<GitInfo> getListByStatus(Integer status) {
		return gitInfoDao.find(Restrictions.eq("status", status));

	}

	@Override
	public List<GitInfo> getListByOrganizationIdStr(String organizationIdStr) {
		StringBuffer hql = new StringBuffer(
				"select g from GitInfo g,Organization o where g.status=0 and o.id=g.organizationId");
		if (organizationIdStr != null && !organizationIdStr.trim().equals("")) {
			hql.append(" and g.organizationId in (").append(organizationIdStr)
					.append(")");
		}
		hql.append(" order by o.orgCode");
		return gitInfoDao.find(hql.toString());
	}

	@Override
	public Page<GitInfo> searchListByAreaInfoId(Integer areaInfoId,
			Page<GitInfo> page, List<PropertyFilter> filters) {
		StringBuffer hql = new StringBuffer("from GitInfo where status=0");
		if (areaInfoId != null) {
			hql.append(" and areaInfoId=?0");
		}
		return areaInfoId != null ? gitInfoDao.findPage(page, hql.toString(),
				areaInfoId) : gitInfoDao.findPage(page, hql.toString());

	}

	@Override
	public GitInfo getGitInfoByIpAndId(String ip, Integer gitInfoId) {
		StringBuffer hql = new StringBuffer(
				"from GitInfo where status=0 and ip='").append(ip).append("'");
		if (gitInfoId != null) {
			hql.append(" and id!=").append(gitInfoId);
		}
		return gitInfoDao.findUnique(hql.toString());
	}

	@Override
	public List<GitInfo> getListByOrganizationIdStrAndDeviceType(
			String organizationIdStr, DeviceType deviceType) {
		StringBuffer hql = new StringBuffer("from GitInfo where status=0");
		if (organizationIdStr != null && !organizationIdStr.trim().equals("")) {
			hql.append(" and organizationId in (").append(organizationIdStr)
					.append(")");
		}

		hql.append(
				" and id in (select gitInfo.id from Device where status=0 and deviceType=")
				.append(DeviceType.UPS.ordinal()).append(") ");
		return gitInfoDao.find(hql.toString());

	}

	@Override
	public List<GitInfo> getListByIds(String gitIds) {
		StringBuffer hql = new StringBuffer("from GitInfo where status=0");
		if (gitIds != null) {
			hql.append(" and id in (").append(gitIds).append(")");
		}
		return gitInfoDao.find(hql.toString());
	}

	@Override
	public List<GitInfo> getDeviceStatusByGitInfoIds(String[] gitInfoIds) {
		List<GitInfo> gitInfos = new ArrayList<GitInfo>();
		for (String gitInfoIdStr : gitInfoIds) {
			GitInfo gitInfo = gitInfoDao.find(Integer.parseInt(gitInfoIdStr));
			List<UpsStatus> upsStatusList = upsStatusDao
					.findLatestDataByGitInfoId(Integer.parseInt(gitInfoIdStr));
			List<ThStatus> thStatusList = thStatusDao
					.findLatestDataByGitInfoId(Integer.parseInt(gitInfoIdStr));
			List<SwitchInputStatus> waterStatusList = switchInputStatusDao
					.findLatestData(Integer.parseInt(gitInfoIdStr),
							DeviceInterfaceType.WATER);
			List<SwitchInputStatus> smokeStatusList = switchInputStatusDao
					.findLatestData(Integer.parseInt(gitInfoIdStr),
							DeviceInterfaceType.SMOKE);
			List<AcStatus> acStatusList=acStatusDao.findLastestDataByGitInfoId(Integer.parseInt(gitInfoIdStr));
			
			
			gitInfo.setUpsStatusList(upsStatusList);
			gitInfo.setAcStatusList(acStatusList);
			gitInfo.setThStatusList(thStatusList);
			if (waterStatusList.size() > 0) {
				gitInfo.setWaterStatusList(waterStatusList);
			} else {
				List<Device> deviceList = deviceDao
						.getWaterDeviceByGitInfoIdAndDeviceInterfaceType(
								gitInfo.getId(), DeviceInterfaceType.WATER);
				List<SwitchInputStatus> waterStatus_List = new ArrayList<SwitchInputStatus>();
				for (Device device : deviceList) {
					SwitchInputStatus switchInputStatus = new SwitchInputStatus();
					switchInputStatus.setName(device.getName());
					switchInputStatus.setCurrentState(0);
					waterStatus_List.add(switchInputStatus);

				}
				gitInfo.setWaterStatusList(waterStatus_List);
			}
			if (smokeStatusList.size() > 0) {
				gitInfo.setSmokeStatusList(smokeStatusList);
			} else {
				List<Device> deviceList = deviceDao
						.getWaterDeviceByGitInfoIdAndDeviceInterfaceType(
								gitInfo.getId(), DeviceInterfaceType.SMOKE);
				List<SwitchInputStatus> smokeStatus_List = new ArrayList<SwitchInputStatus>();
				for (Device device : deviceList) {
					SwitchInputStatus switchInputStatus = new SwitchInputStatus();
					switchInputStatus.setName(device.getName());
					switchInputStatus.setCurrentState(0);
					smokeStatus_List.add(switchInputStatus);

				}
				gitInfo.setSmokeStatusList(smokeStatus_List);
			}
			gitInfos.add(gitInfo);
		}
		return gitInfos;
	}

}
