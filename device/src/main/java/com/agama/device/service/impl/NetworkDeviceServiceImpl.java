package com.agama.device.service.impl;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agama.authority.dao.IOrganizationDao;
import com.agama.authority.entity.Organization;
import com.agama.authority.entity.User;
import com.agama.common.dao.utils.Page;
import com.agama.common.enumbean.DeviceUsedStateEnum;
import com.agama.common.enumbean.StatusEnum;
import com.agama.common.enumbean.UsingStateEnum;
import com.agama.common.service.impl.BaseServiceImpl;
import com.agama.device.dao.IDevicePurchaseDao;
import com.agama.device.dao.INetworkDeviceDao;
import com.agama.device.domain.DevicePurchase;
import com.agama.device.domain.NetworkDevice;
import com.agama.device.service.INetworkDeviceService;

@Service
@Transactional
public class NetworkDeviceServiceImpl extends BaseServiceImpl<NetworkDevice, Integer>implements INetworkDeviceService {

	@Autowired
	private INetworkDeviceDao networkDeviceDao;
	@Autowired
	private IOrganizationDao organizationDao;
	@Autowired
	private IDevicePurchaseDao devicePurchaseDao;

	@Override
	public NetworkDevice getNetworkDeviceByIdentifier(String identifier) {
		String hql = "from NetworkDevice where identifier = '" + identifier + "'";
		List<NetworkDevice> list = networkDeviceDao.find(hql);
		if (!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<NetworkDevice> getNetworkDeviceByOrgId(int orgId) {
		String hql = "from NetworkDevice where organizationId = " + orgId + " and status = " + StatusEnum.NORMAL.ordinal();
		return networkDeviceDao.find(hql);
	}

	@Override
	public List<Object> getNameAndIdentifierByOrgId(int orgId) {
		String hql = "select new Map(name as name,identifier as identifier) from NetworkDevice where status = " + StatusEnum.NORMAL.ordinal() + " and organizationId = " + orgId;
		return networkDeviceDao.find(hql);
	}

	@Override
	public List<NetworkDevice> getAllList() {
		String hql = "from NetworkDevice where status = " + StatusEnum.NORMAL.ordinal();
		return networkDeviceDao.find(hql);
	}

	@Override
	public Map<String, Object> getDetailByIdentifierForHandset(String identifier) {
		String hql = "select new Map(b.name as brand,di.firstDeviceType as firstDeviceType, di.secondDeviceType as secondDeviceType, "
				+ " o.orgName as hallName, hd.name as name, hd.enable as enable, hd.remark as description, hd.model as model, hd.managerId as manager, "
				+ " CONCAT(hd.manufactureDate) as manufactureDate, CONCAT(hd.warrantyDate) as warrantyDate, hd.identifier as identifier) "
				+ " from NetworkDevice hd, Brand b, DevicePurchase dp, Organization o, DeviceInventory di "
				+ " where hd.identifier ='" + identifier + "' and hd.organizationId = o.id and hd.purchaseId = dp.id "
				+ " and dp.deviceInventoryId = di.id and di.brandId = b.id";
		List<Map<String, Object>> list = networkDeviceDao.find(hql);
		if (!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public Page<NetworkDevice> searchObtainNetWorkDeviceList(
			Page<NetworkDevice> page, String organizationId, StatusEnum status,
			DeviceUsedStateEnum deviceUsedState,User user) {
		
		StringBuffer sql=new StringBuffer("select nd.id as id,");
		sql.append("nd.identifier as identifier,");
		sql.append("nd.manufacture_Date as manufactureDate,");
		sql.append("nd.warranty_Date as warrantyDate,");
		sql.append("nd.name as name,");
		sql.append("nd.remark as remark,");
		sql.append("nd.model as model,");
		sql.append("nd.update_time as updateTime,");
		sql.append("nd.manager_id as managerId,");
		sql.append("managerUser.name as managerName,");
		sql.append("nd.organization_id as organizationId,");
		sql.append("o.org_Name as organizationName,");
		sql.append("nd.authorization_Code as authorizationCode,");
		sql.append("nd.max_Network_Load as maxNetworkLoad,");
		sql.append("nd.device_Used_State as deviceUsedStateValue,");
		sql.append("nd.obtain_User_Id as obtainUserId,");
		sql.append("obtainUser.name as obtainUserName ");
		sql.append("from network_device nd ");
		sql.append("left join User obtainUser ");
		sql.append("on nd.obtain_User_Id=obtainUser.id ");
		sql.append("left join User managerUser ");
		sql.append("on nd.manager_Id=managerUser.id ");
		sql.append("left join Organization o ");
		sql.append("on nd.organization_Id=o.id ");
		sql.append("where nd.status=").append(status.getId());	
		//如果设备状态为领用，则查询条件为领用和审核
		if(deviceUsedState==DeviceUsedStateEnum.USED){
			sql.append(" and (nd.device_Used_State='");
			if(user!=null){
				sql.append(DeviceUsedStateEnum.TURNOVER);
			}else{
				sql.append(DeviceUsedStateEnum.USED);
			}
			sql.append("' or nd.device_Used_State='").append(DeviceUsedStateEnum.AUDIT.toString()).append("')");
		}else{
			sql.append(" and nd.device_Used_State='").append(deviceUsedState.toString()).append("'");
		}
		sql.append(" and nd.scrapped_State='").append(UsingStateEnum.NO.toString()).append("'");
		
		if(user!=null){
			sql.append(" and nd.obtain_User_Id=").append(user.getId());
		}else{
			if(organizationId!=null){
				sql.append(" and nd.organization_Id=").append(organizationId);
			}
		}
		return networkDeviceDao.findPageBySQL(page, sql.toString());
	}

	@Override
	public void netWorkDeviceObtain(Integer orgId,String type,
			List<Integer> netWorkDeviceIdList,User user) {
		for (Integer netWorkDeviceId : netWorkDeviceIdList) {
			NetworkDevice netWorkDevice=networkDeviceDao.find(netWorkDeviceId);
			
			//类型为借调
			if(type.equals("secondment")){
				netWorkDevice.setSecondmentState(UsingStateEnum.YES);
			}
						if(orgId!=null){
			Organization organization=organizationDao.find(orgId);
				netWorkDevice.setOrganizationId(organization.getId());
				netWorkDevice.setOrganizationName(organization.getOrgName());
			}
			if(user!=null){
				netWorkDevice.setObtainUserId(user.getId());
				
			}
			netWorkDevice.setDeviceUsedState(DeviceUsedStateEnum.AUDIT);

			
			//netWorkDevice.setObtainTime(new Date());
			networkDeviceDao.update(netWorkDevice);
		}
		
	}

	@Override
	public void backNetWorkDevice(List<Integer> netWorkDeviceIdList) {
		for (Integer netWorkDeviceId : netWorkDeviceIdList) {
			NetworkDevice netWorkDevice=networkDeviceDao.find(netWorkDeviceId);
			DevicePurchase devicePurchase=devicePurchaseDao.find(netWorkDevice.getPurchaseId());
			netWorkDevice.setOrganizationId(devicePurchase.getOrgId());
			netWorkDevice.setOrganizationName(devicePurchase.getOrgName());
			netWorkDevice.setDeviceUsedState(DeviceUsedStateEnum.PUTINSTORAGE);
			networkDeviceDao.update(netWorkDevice);
		}
		
	}

	@Override
	public void scrappedNetWorkDevice(List<Integer> netWorkDeviceIdList) {
		for (Integer netWorkDeviceId : netWorkDeviceIdList) {
			NetworkDevice netWorkDevice=networkDeviceDao.find(netWorkDeviceId);
			netWorkDevice.setDeviceUsedState(DeviceUsedStateEnum.SCRAP);
			networkDeviceDao.update(netWorkDevice);
		}
		
	}

	@Override
	public void netWorkDeviceAudit(Integer type,
			List<Integer> netWorkDeviceIdList) {
		for (Integer netWorkDeviceId : netWorkDeviceIdList) {
			NetworkDevice netWorkDevice=networkDeviceDao.find(netWorkDeviceId);
			DeviceUsedStateEnum deviceUsedState=DeviceUsedStateEnum.USED;
			//type=0 不通过，返回仓库状态
			if(type==0){
				deviceUsedState=DeviceUsedStateEnum.PUTINSTORAGE;
			}
			netWorkDevice.setDeviceUsedState(deviceUsedState);
			networkDeviceDao.update(netWorkDevice);
		}		
		
	}
	
	@Override
	public List<NetworkDevice> getListByDeviceUsedStateEnum(DeviceUsedStateEnum deviceUsedStateEnum) {
		String hql = " from NetworkDevice where status = " + StatusEnum.NORMAL.ordinal() + " and deviceUsedState = '" + deviceUsedStateEnum + "' ";
		return networkDeviceDao.find(hql);
	}
	
	public List<NetworkDevice> getListByObtainUser(Integer userId) {
		String hql = " from NetworkDevice where status = " + StatusEnum.NORMAL.ordinal() + " and obtainUserId = " + userId + " and deviceUsedState = '" 
				+ DeviceUsedStateEnum.TURNOVER.toString() + "' ";
		return networkDeviceDao.find(hql);
	}

	@Override
	public void updateDeviceUsedStateByDeviceIdList(
			List<Integer> netWorkDeviceIdList, DeviceUsedStateEnum deviceUsedState) {
		for (Integer netWorkDeviceId : netWorkDeviceIdList) {
			NetworkDevice netWorkDevice=networkDeviceDao.find(netWorkDeviceId);
			netWorkDevice.setDeviceUsedState(deviceUsedState);
			networkDeviceDao.update(netWorkDevice);
		}
		
	}
	
	@Override
	public List<NetworkDevice> getListByQueryMap(Map<String, Object> map) {
		return networkDeviceDao.getListByQueryMap(map);
	}
	
}
