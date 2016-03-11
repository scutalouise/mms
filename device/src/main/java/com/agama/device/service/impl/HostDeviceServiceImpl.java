package com.agama.device.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agama.authority.dao.IOrganizationDao;
import com.agama.authority.entity.Organization;
import com.agama.authority.entity.User;
import com.agama.common.dao.utils.Page;
import com.agama.common.domain.StateEnum;
import com.agama.common.enumbean.DeviceUsedStateEnum;
import com.agama.common.enumbean.FirstDeviceType;
import com.agama.common.enumbean.StatusEnum;
import com.agama.common.enumbean.UsingStateEnum;
import com.agama.common.service.impl.BaseServiceImpl;
import com.agama.device.dao.IDevicePurchaseDao;
import com.agama.device.dao.IHostDeviceDao;
import com.agama.device.dao.INetworkDeviceDao;
import com.agama.device.domain.DevicePurchase;
import com.agama.device.domain.HostDevice;
import com.agama.device.domain.NetworkDevice;
import com.agama.device.service.IHostDeviceService;


@Service
@Transactional
public class HostDeviceServiceImpl extends BaseServiceImpl<HostDevice, Integer>implements IHostDeviceService {
	public static StateEnum deviceCurrentState=StateEnum.good;
	private static Map<String, Object> alarmConditionMap = new HashMap<String, Object>();
	@Autowired
	private IHostDeviceDao hostDeviceDao;
	@Autowired
	private INetworkDeviceDao netWorkDeviceDao;
	@Autowired
	private IOrganizationDao organizationDao;
	@Autowired
	private IDevicePurchaseDao devicePurchaseDao;

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
		String hql = "from HostDevice where organizationId = " + orgId + " and status = " + StatusEnum.NORMAL.ordinal();
		return hostDeviceDao.find(hql);
	}

	@Override
	public List<Object> getNameAndIdentifierByOrgId(int orgId) {
		String hql = "select new Map(name as name,identifier as identifier) from HostDevice where status = " + StatusEnum.NORMAL.ordinal() + " and organizationId = " + orgId;
		return hostDeviceDao.find(hql);
	}

	@Override
	public List<HostDevice> getAllList() {
		String hql = "from HostDevice where status = " + StatusEnum.NORMAL.ordinal();
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
	public Page<HostDevice> searchObtainHostDeviceList(Page<HostDevice> page,
			String organizationId, StatusEnum status,DeviceUsedStateEnum deviceUsedState,User user) {
		
		StringBuffer sql=new StringBuffer("select hd.id as id,");
		sql.append("hd.identifier as identifier,");
		sql.append("hd.manufacture_Date as manufactureDate,");
		sql.append("hd.warranty_Date as warrantyDate,");
		sql.append("hd.name as name,");
		sql.append("hd.remark as remark,");
		sql.append("hd.model as model,");
		sql.append("hd.update_Time as updateTime,");
		sql.append("hd.manager_Id as managerId,");
		sql.append("managerUser.name as managerName,");
		sql.append("hd.organization_Id as organizationId,");
		sql.append("o.org_Name as organizationName,");
		sql.append("hd.ip as ip,");
		sql.append("hd.authorization_Code as authorizationCode,");
		sql.append("hd.oSType as oSTypeValue,");
		sql.append("hd.oSBits as oSBits,");
		sql.append("hd.cpu as cpu,");
		sql.append("hd.harddisk as harddisk,");
		sql.append("hd.motherboard as motherboard,");
		sql.append("hd.memory as memory,");
		sql.append("hd.device_Used_State as deviceUsedStateValue,");
		sql.append("hd.obtain_User_Id as obtainUserId,");
		sql.append("obtainUser.name as obtainUserName "); 
		sql.append("from Host_Device hd ");
		sql.append("left join User obtainUser ");
		sql.append("on hd.obtain_User_Id=obtainUser.id ");
		sql.append("left join User managerUser ");
		sql.append("on hd.manager_Id=managerUser.id ");
		sql.append("left join Organization o ");
		sql.append("on hd.organization_Id=o.id ");
		sql.append("where hd.status=").append(status.getId());
		//如果设备状态为领用，则查询条件为领用和审核
		if(deviceUsedState==DeviceUsedStateEnum.USED){
			sql.append(" and (hd.device_Used_State='");
			if(user!=null){
				sql.append(DeviceUsedStateEnum.TURNOVER);
			}else{
				sql.append(DeviceUsedStateEnum.USED);
			}
			sql.append("' or hd.device_Used_State='").append(DeviceUsedStateEnum.AUDIT.toString()).append("')");
		}else{
			sql.append(" and hd.device_Used_State='").append(deviceUsedState.toString()).append("'");
		}
		sql.append(" and hd.scrapped_State='").append(UsingStateEnum.NO.toString()).append("'");
		if(user!=null){
			sql.append(" and hd.obtain_User_Id=").append(user.getId());
		}else{
			if(organizationId!=null){
				sql.append(" and hd.organization_Id=").append(organizationId);
			}
		}
		return hostDeviceDao.findPageBySQL(page, sql.toString());
	}

	@Override
	public void hostDeviceObtain(Integer orgId,String type, List<Integer> hostDeviceIdList,User user) {
		for (Integer hostDeviceId : hostDeviceIdList) {
			HostDevice hostDevice=hostDeviceDao.find(hostDeviceId);
			
			
			//类型为借调
			if(type.equals("secondment")){
				hostDevice.setSecondmentState(UsingStateEnum.YES);
			}
			
			if(orgId!=null){
				Organization organization=organizationDao.find(orgId);	
				hostDevice.setOrganizationId(organization.getId());
				hostDevice.setOrganizationName(organization.getOrgName());
			}
			if(user!=null){
				hostDevice.setObtainUserId(user.getId());
				
			}
			hostDevice.setDeviceUsedState(DeviceUsedStateEnum.AUDIT); //状态改为领用审核中
			
			//hostDevice.setObtainTime(new Date());
			hostDeviceDao.update(hostDevice);
		}
		
	}

	@Override
	public void backHostDevice(List<Integer> hostDeviceIdList) {
		for (Integer hostDeviceId : hostDeviceIdList) {
			HostDevice hostDevice=hostDeviceDao.find(hostDeviceId);
			DevicePurchase devicePurchase=devicePurchaseDao.find(hostDevice.getPurchaseId());
			hostDevice.setOrganizationId(devicePurchase.getOrgId());
			hostDevice.setOrganizationName(devicePurchase.getOrgName());
			hostDevice.setDeviceUsedState(DeviceUsedStateEnum.PUTINSTORAGE);  //入库状态
			hostDeviceDao.update(hostDevice);
		}
		
	}

	@Override
	public void scrappedHostDevice(List<Integer> hostDeviceIdList) {
		for (Integer hostDeviceId : hostDeviceIdList) {
			HostDevice hostDevice=hostDeviceDao.find(hostDeviceId);
			hostDevice.setDeviceUsedState(DeviceUsedStateEnum.SCRAP);
			hostDeviceDao.update(hostDevice);
		}
		
	}

	@Override
	public void hostDeviceAudit(Integer type, List<Integer> hostDeviceIdList) {
		for (Integer hostDeviceId : hostDeviceIdList) {
			HostDevice hostDevice=hostDeviceDao.find(hostDeviceId);
			DeviceUsedStateEnum deviceUsedState=DeviceUsedStateEnum.USED;
			//type=0 不通过，返回仓库状态
			if(type==0){
				deviceUsedState=DeviceUsedStateEnum.PUTINSTORAGE;
			}
			hostDevice.setDeviceUsedState(deviceUsedState);
			hostDeviceDao.update(hostDevice);
		}		
	}
	
	@Override
	public List<HostDevice> getListByDeviceUsedStateEnum(DeviceUsedStateEnum deviceUsedStateEnum) {
		String hql = " from HostDevice where status = " + StatusEnum.NORMAL.ordinal() + " and deviceUsedState = '" + deviceUsedStateEnum + "' ";
		return hostDeviceDao.find(hql);
	}
	
	public List<HostDevice> getListByObtainUser(Integer userId) {
		String hql = " from HostDevice where status = " + StatusEnum.NORMAL.ordinal() + " and obtainUserId = " + userId + " and deviceUsedState = '" 
				+ DeviceUsedStateEnum.TURNOVER.toString() + "' ";
		return hostDeviceDao.find(hql);
	}
	
	public List<HostDevice> getListByQueryMap(Map<String, Object> map) {
		return hostDeviceDao.getListByQueryMap(map);
	}

	@Override
	public void updateDeviceUsedStateByDeviceIdList(
			List<Integer> hostDeviceIdList, DeviceUsedStateEnum deviceUsedState) {
		for (Integer hostDeviceId : hostDeviceIdList) {
			HostDevice hostDevice=hostDeviceDao.find(hostDeviceId);
			hostDevice.setDeviceUsedState(deviceUsedState);
			hostDeviceDao.update(hostDevice);
		}
		
	}

}
