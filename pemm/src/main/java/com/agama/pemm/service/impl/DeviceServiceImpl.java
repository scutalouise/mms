package com.agama.pemm.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.transaction.Transactional;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agama.common.dao.utils.Page;
import com.agama.common.dao.utils.PropertyFilter;
import com.agama.common.domain.StateEnum;
import com.agama.common.enumbean.DeviceInterfaceType;
import com.agama.common.service.impl.BaseServiceImpl;
import com.agama.pemm.bean.DeviceStateRecord;
import com.agama.pemm.dao.IAlarmLogDao;
import com.agama.pemm.dao.IDeviceDao;
import com.agama.pemm.dao.IGitInfoDao;
import com.agama.pemm.dao.ISwitchInputStatusDao;
import com.agama.pemm.dao.IThStatusDao;
import com.agama.pemm.dao.IUpsStatusDao;
import com.agama.pemm.domain.Device;
import com.agama.pemm.domain.GitInfo;
import com.agama.pemm.protocol.snmp.AcOidInfo;
import com.agama.pemm.protocol.snmp.SwitchInputOidInfo;
import com.agama.pemm.protocol.snmp.UpsOidInfo;
import com.agama.pemm.service.IDeviceService;
import com.agama.pemm.utils.SNMPUtil;
import com.agama.tool.utils.ConvertUtils;
import com.agama.common.enumbean.DeviceType;

@Service
@Transactional
public class DeviceServiceImpl extends BaseServiceImpl<Device, Integer>
		implements IDeviceService {
	@Autowired
	private IDeviceDao deviceDao;
	@Autowired
	private IGitInfoDao gitInfoDao;
	
	@Autowired
	private IUpsStatusDao upsStatusDao;
	@Autowired
	private IThStatusDao thStatusDao;
	@Autowired
	private ISwitchInputStatusDao switchInputStatusDao;
	@Autowired
	private IAlarmLogDao alarmLogDao;

	@Override
	public Device getListByGitInfoIdAndDeviceTypeAndIndex(Integer gitInfoId,
			DeviceType deviceType, Integer index) {
		Criterion gitInfoIdCriterion = Restrictions.eq("gitInfo.id", gitInfoId);
		Criterion deviceTypeCriterion = Restrictions.eq("deviceType",
				deviceType);
		Criterion indexCriterion = Restrictions.eq("deviceIndex", index);
		Criterion statusCriterion = Restrictions.eq("status", 0);
		return deviceDao.findUnique(gitInfoIdCriterion, deviceTypeCriterion,
				indexCriterion, statusCriterion);
	}

	@Override
	public void addDeviceForScan(List<Integer> gitInfoIdList,
			List<Map<String, Object>> deviceIndexList) {
	
		for (int i = 0; i < gitInfoIdList.size(); i++) {
			SNMPUtil snmpUtil = new SNMPUtil();
			for (Entry<String, Object> map : deviceIndexList.get(i).entrySet()) {
				Device device = new Device();
				GitInfo gitInfo = gitInfoDao.find(gitInfoIdList.get(i));
				device.setManagerId(gitInfo.getManagerId());
				device.setManagerName(gitInfo.getManagerName());
				device.setGitInfo(gitInfo);
				device.setStatus(0);
				device.setEnabled(1);
				DeviceType deviceType = null;
				DeviceInterfaceType deviceInterfaceType = null;
				String nameOid = null;
				if (map.getKey().toLowerCase().contains("ups")) {
					deviceType = DeviceType.UPS;
					deviceInterfaceType = DeviceInterfaceType.UPS;
					nameOid = "1.3.6.1.4.1.34651.2.1.1.1";
				} else if (map.getKey().toLowerCase().contains("th")) {
					deviceType = DeviceType.TH;
					deviceInterfaceType = DeviceInterfaceType.TH;
					nameOid = "1.3.6.1.4.1.34651.2.2.1.1";
				} else if (map.getKey().toLowerCase().contains("in")) {
					deviceType = DeviceType.SWITCHINPUT;
					nameOid = SwitchInputOidInfo.NAME.getOid();
				}else if(map.getKey().toLowerCase().contains("ac")){
					deviceType=DeviceType.AC;
					deviceInterfaceType = DeviceInterfaceType.AC;
					nameOid=AcOidInfo.NAME.getOid();
				}
				device.setDeviceType(deviceType);
				device.setDeviceInterfaceType(deviceInterfaceType);
				device.setDeviceIndex(Integer.parseInt(map.getValue()
						.toString()));
				device.setCurrentState(StateEnum.good);

				String oidStr = nameOid + "." + map.getValue();
				List<String> oids = new ArrayList<String>();
				oids.add(oidStr);
				try {
					Map<String, String> nameMap = snmpUtil.walkByGet(
							gitInfo.getIp(), oids);
					device.setName(ConvertUtils.change2Chinese(nameMap
							.get(oidStr)));
					System.out.println(ConvertUtils.change2Chinese(nameMap
							.get(oidStr)));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				deviceDao.save(device);
			}
			

		}

	}

	@Override
	public void updateStatusByIds(String ids) {
		deviceDao.updateStatusByIds(ids);
		//逻辑删除ups状态信息
		upsStatusDao.updateStatusDeviceIds(ids,1);
		//逻辑删除温湿度状态信息
		thStatusDao.updateStatusDeviceIds(ids,1);
		//逻辑删除开关量输出
		switchInputStatusDao.updateStatusDeviceIds(ids,1);
		//逻辑删除报警日志
		alarmLogDao.updateStatusDeviceIds(ids,1);
	

	}

	@Override
	public Page<Device> searchListByOrganizationId(Integer organizationId,
			Page<Device> page, List<PropertyFilter> filters) {
		StringBuffer hql = new StringBuffer(
				"from Device d where d.status=0 and d.gitInfo.id in (select g.id from GitInfo g where g.status=0 ");
		if (organizationId != null) {
			hql.append(" and g.organizationId=").append(organizationId);
		}
		hql.append(")");

		return deviceDao.findPage(page, hql.toString());
	}

	@Override
	public Page<Device> searchListByOrganizationIdStr(String organizationIdStr,
			Page<Device> page, List<PropertyFilter> filters) {
		StringBuffer hql = new StringBuffer(
				"from Device d where d.status=0 and d.gitInfo.id in (select g.id from GitInfo g where g.status=0 ");
		if (organizationIdStr != null) {
			hql.append(" and g.organizationId in (").append(organizationIdStr)
					.append(")");
		}
		hql.append(")");

		return deviceDao.findPage(page, hql.toString());
	}

	public List<DeviceStateRecord> getDeviceStateRecordByOrganizationId(
			String organizationIdStr) {
		return deviceDao
				.getDeviceStateRecordByOrganizationId(organizationIdStr);
	}

	@Override
	public List<Device> getListByGitInfoIdAndDeviceType(Integer gitInfoId,
			DeviceType deviceType) {
		// TODO Auto-generated method stub
		String hql = "from Device where status=0 and gitInfo.id=?0 and deviceType=?1 order by deviceIndex asc";
		return deviceDao.find(hql, gitInfoId, deviceType);
	}

	@Override
	public void updateCurrentStateById(Integer id, StateEnum stateEnum) {

		deviceDao.updateCurrentStateById(id, stateEnum);

	}

	@Override
	public List<Map<String, Object>> getCurrentStateAndCount() {
		// TODO Auto-generated method stub
		return deviceDao.getCurrentStateAndCount();
	}

	@Override
	public Page<Device> getDischargeDevice(Page<Device> page, String scheduleName,
			String scheduleGroup, Integer managerId,Integer type) {
		StringBuffer hql=new StringBuffer("from Device where status=0 and deviceInterfaceType=").append(DeviceInterfaceType.UPS.ordinal());
		if(scheduleName!=null&&scheduleGroup!=null){
			hql.append(" and id ");
			if(type==1){
				hql.append(" not ");
			}
			hql.append("in (").append("select deviceId from DischargeTask where status=0 and scheduleGroup='").append(scheduleGroup).append("'");
			if(type!=1){
				
			
			hql.append(" and scheduleName='").append(scheduleName).append("'");
			}
			hql.append(")");
		}
		return deviceDao.findPage(page, hql.toString());
	}

	@Override
	public List<Device> getDeviceListByScheduleNameAndScheduleGroup(
			String scheduleName, String scheduleGroup) {
		return deviceDao.getDeviceListByScheduleNameAndScheduleGroup(scheduleName, scheduleGroup);
	
	}

	@Override
	public void upsClose(Integer deviceId) {
		SNMPUtil snmpUtil=new SNMPUtil();
		try {
			Device device=deviceDao.find(deviceId);
			snmpUtil.sendSetCommand(device.getGitInfo().getIp(), UpsOidInfo.shutdown.getOid(), 1);
			
		} catch (Exception e) {	
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public List<Device> getDeviceByIpAndDeviceTypeAndIndex(String ip,
			DeviceType deviceType, Integer index) {
		// TODO Auto-generated method stub
		return deviceDao.getDeviceByIpAndDeviceTypeAndIndex(ip, deviceType, index);
	}

}
