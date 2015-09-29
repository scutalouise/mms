package com.agama.pemm.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agama.common.dao.utils.Page;
import com.agama.common.dao.utils.PropertyFilter;
import com.agama.common.service.impl.BaseServiceImpl;
import com.agama.pemm.bean.DeviceStateRecord;
import com.agama.pemm.bean.DeviceType;
import com.agama.pemm.bean.StateEnum;
import com.agama.pemm.dao.IDeviceDao;
import com.agama.pemm.dao.IGitInfoDao;
import com.agama.pemm.domain.Device;
import com.agama.pemm.domain.GitInfo;
import com.agama.pemm.service.IDeviceService;
import com.agama.pemm.utils.SNMPUtil;

@Service
@Transactional
public class DeviceServiceImpl extends BaseServiceImpl<Device, Integer> implements
		IDeviceService {
	@Autowired
	private IDeviceDao deviceDao;
	@Autowired
	private IGitInfoDao gitInfoDao;

	@Override
	public Device getListByGitInfoIdAndDeviceTypeAndIndex(Integer gitInfoId,DeviceType deviceType,Integer index) {
		// TODO Auto-generated method stub
		Criterion gitInfoIdCriterion=Restrictions.eq("gitInfo.id", gitInfoId);
		Criterion deviceTypeCriterion=Restrictions.eq("deviceType", deviceType);
		Criterion indexCriterion=Restrictions.eq("deviceIndex", index);
		Criterion statusCriterion=Restrictions.eq("status", 0);
		return deviceDao.findUnique(gitInfoIdCriterion,deviceTypeCriterion,indexCriterion,statusCriterion);
	}

	@Override
	public void addDeviceForScan(List<Integer> gitInfoIdList,
			List<Map<String, Object>> deviceIndexList) {
		SNMPUtil snmpUtil=new SNMPUtil();
		for (int i = 0; i < gitInfoIdList.size(); i++) {
			
			for (Map<String, Object> deviceIndexMap : deviceIndexList) {
				for (Entry<String, Object> map: deviceIndexMap.entrySet()) {
					Device device=new Device();
					GitInfo gitInfo=gitInfoDao.find(gitInfoIdList.get(i));
					
					device.setGitInfo(gitInfo);
					device.setStatus(0);
					device.setEnabled(1);
					device.setDeviceType(DeviceType.UPS);
					device.setDeviceIndex(Integer.parseInt(map.getValue().toString()));
					device.setCurrentState(StateEnum.good);
					String oidStr="1.3.6.1.4.1.34651.2.1.1.1."+map.getValue();
					List<String> oids=new ArrayList<String>();
					oids.add(oidStr);
					try {
						Map<String,String> nameMap=snmpUtil.walkByGet(gitInfo.getIp(), oids);
						device.setName(nameMap.get(oidStr));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					deviceDao.save(device);
				}
			}
			
			
		}
		
	}

	@Override
	public void updateStatusByIds(String ids) {
		
		deviceDao.updateStatusByIds(ids);
		
	}

	@Override
	public Page<Device> searchListByAreaInfoId(Integer areaInfoId,
			Page<Device> page, List<PropertyFilter> filters) {
		StringBuffer hql=new StringBuffer("from Device d where d.status=0 and d.gitInfo.id in (select g.id from GitInfo g where g.status=0 ");
		if(areaInfoId!=null){
			hql.append(" and g.areaInfoId=").append(areaInfoId);
		}
		hql.append(")");
		
		return deviceDao.findPage(page, hql.toString());
	}
	public List<DeviceStateRecord> getDeviceStateRecordByAreaInfoId(String areaInfoIdStr){
		return deviceDao.getDeviceStateRecordByAreaInfoId(areaInfoIdStr);
	}

	@Override
	public List<Device> getListByGitInfoIdAndDeviceType(Integer gitInfoId,
			DeviceType deviceType) {
		// TODO Auto-generated method stub
		String hql="from Device where status=0 and gitInfo.id=?0 and deviceType=?1 order by deviceIndex asc";
		return deviceDao.find(hql, gitInfoId,deviceType);
	}

	@Override
	public void updateCurrentStateById(Integer id, StateEnum stateEnum) {
	
		deviceDao.updateCurrentStateById(id,stateEnum);
		
	}

	@Override
	public List<Map<String,Object>> getCurrentStateAndCount() {
		// TODO Auto-generated method stub
		return deviceDao.getCurrentStateAndCount();
	}
	
	
	
	
	
	
}
