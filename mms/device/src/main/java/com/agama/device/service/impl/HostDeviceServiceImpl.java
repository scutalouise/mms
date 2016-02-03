package com.agama.device.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agama.common.domain.StateEnum;
import com.agama.common.enumbean.FirstDeviceType;
import com.agama.common.enumbean.StatusEnum;
import com.agama.common.service.impl.BaseServiceImpl;
import com.agama.device.dao.IHostDeviceDao;
import com.agama.device.dao.INetworkDeviceDao;
import com.agama.device.domain.HostDevice;
import com.agama.device.domain.NetworkDevice;
import com.agama.device.service.IHostDeviceService;


@Service
public class HostDeviceServiceImpl extends BaseServiceImpl<HostDevice, Integer>implements IHostDeviceService {
	public static StateEnum deviceCurrentState=StateEnum.good;
	private static Map<String, Object> alarmConditionMap = new HashMap<String, Object>();
	@Autowired
	private IHostDeviceDao hostDeviceDao;
	@Autowired
	private INetworkDeviceDao netWorkDeviceDao;

	@Override
	public void updateCurrentStatus(String identifier, StateEnum currentState) {
		StateEnum device_CurrentState=StateEnum.good;
		if (FirstDeviceType.HOSTDEVICE.getValue().equals(
				identifier.substring(0, 2))) {
		
			HostDevice hostDevice=hostDeviceDao.findUnique(Restrictions.eq("identifier",
				identifier));
			device_CurrentState=hostDevice.getCurrentState();
		}else if(FirstDeviceType.NETWORKDEVICE.getValue().equals(
				identifier.substring(0, 2))){
			NetworkDevice netWorkDevice=netWorkDeviceDao.findUnique(Restrictions.eq("identifier",
					identifier));
			device_CurrentState=netWorkDevice.getCurrentState();
		}
		if(currentState.ordinal()>deviceCurrentState.ordinal()){
			deviceCurrentState=currentState;
		}
		if (alarmConditionMap.get("currentState_" + identifier) != null
				&& deviceCurrentState != StateEnum.valueOf(alarmConditionMap
						.get("currentState_" + identifier).toString())) {
			hostDeviceDao.updateCurrentStateByIdentifier(
					identifier,
					StateEnum.valueOf(alarmConditionMap.get(
							"currentState_" + identifier).toString()));
		} else {
			if (device_CurrentState != currentState) {
				if (FirstDeviceType.HOSTDEVICE.getValue().equals(
						identifier.substring(0, 2))) {
					hostDeviceDao.updateCurrentStateByIdentifier(identifier, currentState);
			
				}else if(FirstDeviceType.NETWORKDEVICE.getValue().equals(
						identifier.substring(0, 2))){
					netWorkDeviceDao.updateCurrentStateByIdentifier(identifier, currentState);
				}
			}
		}
		
	}
	@Override
	public HostDevice getHostDeviceByIdentifier(String identifier) {
		String hql = "from HostDevice where identifier = '" + identifier + "'";
		List<HostDevice> list = hostDeviceDao.find(hql);
		if (!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}
	@Override
	public List<HostDevice> getHostDeviceByOrgId(int orgId) {
		String hql = "from HostDevice where organizationId = " + orgId;
		return hostDeviceDao.find(hql);
	}

	@Override
	public List<Object> getNameAndIdentifierByOrgId(int orgId) {
		String hql = "select new Map(name as name,identifier as identifier) from HostDevice where status = " + StatusEnum.NORMAL.ordinal() + " and organizationId = " + orgId;
		return hostDeviceDao.find(hql);
	}

	@Override
	public Map<String, Object> getDetailByIdentifierForHandset(String identifier) {
		String hql = "select new Map(b.name as brand,di.firstDeviceType as firstDeviceType, di.secondDeviceType as secondDeviceType, "
				+ " o.orgName as hallName, hd.name as name, hd.enable as enable, hd.remark as description, hd.model as model, hd.managerId as manager, "
				+ " CONCAT(hd.manufactureDate) as manufactureDate, CONCAT(hd.warrantyDate) as warrantyDate, hd.identifier as identifier) "
				+ " from HostDevice hd, Brand b, DevicePurchase dp, Organization o, DeviceInventory di "
				+ " where hd.identifier ='" + identifier + "' and hd.organizationId = o.id and hd.purchaseId = dp.id "
				+ " and dp.deviceInventoryId = di.id and di.brandId = b.id";
		List<Map<String, Object>> list = hostDeviceDao.find(hql);
		if (!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}
	@Override
	public List<HostDevice> getAllList() {
		String hql = "from HostDevice where status = " + StatusEnum.NORMAL.ordinal();
		return hostDeviceDao.find(hql);
	}

}
