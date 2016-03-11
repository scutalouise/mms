package com.agama.device.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agama.authority.dao.IOrganizationDao;
import com.agama.authority.entity.Organization;
import com.agama.authority.entity.User;
import com.agama.common.dao.utils.Page;
import com.agama.common.enumbean.DeviceUsedStateEnum;
import com.agama.common.enumbean.StatusEnum;
import com.agama.common.enumbean.UsingStateEnum;
import com.agama.common.service.impl.BaseServiceImpl;
import com.agama.device.dao.ICollectionDeviceDao;
import com.agama.device.dao.IDevicePurchaseDao;
import com.agama.device.domain.CollectionDevice;
import com.agama.device.domain.DevicePurchase;
import com.agama.device.domain.UnintelligentDevice;
import com.agama.device.service.ICollectionDeviceService;

@Service
@Transactional
public class CollectionDeviceServiceImpl extends BaseServiceImpl<CollectionDevice, Integer>
		implements ICollectionDeviceService {

	@Autowired
	private ICollectionDeviceDao collectionDeviceDao;
	@Autowired
	private IOrganizationDao organizationDao;
	@Autowired
	private IDevicePurchaseDao devicePurchaseDao;
	
	@Override
	public List<CollectionDevice> getListByStatus(StatusEnum status) {
		// TODO Auto-generated method stub
		return collectionDeviceDao.getListByStatus(status);
	}
	
	public CollectionDevice getCollectionDeviceByIdentifier(String identifier) {
		String hql = "from CollectionDevice where identifier = '" + identifier + "'";
		List<CollectionDevice> list = collectionDeviceDao.find(hql);
		if (!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<CollectionDevice> getCollectionDeviceByOrgId(int orgId) {
		String hql = "from CollectionDevice where organizationId = " + orgId + " and status = " + StatusEnum.NORMAL.ordinal();
		return collectionDeviceDao.find(hql);
	}

	@Override
	public List<Object> getNameAndIdentifierByOrgId(int orgId) {
		String hql = "select new Map(name as name,identifier as identifier) from CollectionDevice where status = " + StatusEnum.NORMAL.ordinal() + " and organizationId = " + orgId;
		return collectionDeviceDao.find(hql);
	}

	@Override
	public List<CollectionDevice> getAllList() {
		String hql = "from CollectionDevice where status = " + StatusEnum.NORMAL.ordinal();
		return collectionDeviceDao.find(hql);
	}

	@Override
	public Map<String, Object> getDetailByIdentifierForHandset(String identifier) {
		String hql = "select new Map(b.name as brand,di.firstDeviceType as firstDeviceType, di.secondDeviceType as secondDeviceType, "
				+ " o.orgName as hallName, hd.name as name, hd.enable as enable, hd.remark as description, hd.model as model, hd.managerId as manager, "
				+ " CONCAT(hd.manufactureDate) as manufactureDate, CONCAT(hd.warrantyDate) as warrantyDate, hd.identifier as identifier) "
				+ " from CollectionDevice hd, Brand b, DevicePurchase dp, Organization o, DeviceInventory di "
				+ " where hd.identifier ='" + identifier + "' and hd.organizationId = o.id and hd.purchaseId = dp.id "
				+ " and dp.deviceInventoryId = di.id and di.brandId = b.id";
		List<Map<String, Object>> list = collectionDeviceDao.find(hql);
		if (!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public Page<CollectionDevice> searchObtainCollectionDeviceList(
			Page<CollectionDevice> page, String organizationId,
			StatusEnum status, DeviceUsedStateEnum deviceUsedState,User user) {
		StringBuffer sql=new StringBuffer("select cd.id as id,");
		sql.append("cd.identifier as identifier,");
		sql.append("cd.manufacture_Date as manufactureDate,");
		sql.append("cd.warranty_Date as warrantyDate,");
		sql.append("cd.name as name,");
		sql.append("cd.remark as remark,");
		sql.append("cd.model as model,");
		sql.append("cd.update_Time as updateTime,");
		sql.append("cd.manager_Id as managerId,");
		sql.append("managerUser.name as managerName,");
		sql.append("cd.organization_Id as organizationId,");
		sql.append("o.org_Name as organizationName,");
		sql.append("cd.device_Used_State as deviceUsedStateValue,");
		sql.append("cd.obtain_User_Id as obtainUserId,");
		sql.append("obtainUser.name as obtainUserName,");
		sql.append("cd.device_Type as deviceTypeValue ");
		sql.append("from collection_Device cd ");
		
		sql.append("left join User obtainUser ");
		sql.append("on cd.obtain_User_Id=obtainUser.id ");
		sql.append("left join User managerUser ");
		sql.append("on cd.manager_Id=managerUser.id ");
		sql.append("left join Organization o ");
		sql.append("on cd.organization_Id=o.id ");
		sql.append("where cd.status=").append(status.getId());
		
		//如果设备状态为领用，则查询条件为领用和审核
		if(deviceUsedState==DeviceUsedStateEnum.USED){
			sql.append(" and (cd.device_Used_State='");
			if(user!=null){
				sql.append(DeviceUsedStateEnum.TURNOVER);
			}else{
				sql.append(DeviceUsedStateEnum.USED);
			}
			sql.append("' or cd.device_Used_State='").append(DeviceUsedStateEnum.AUDIT.toString()).append("')");
		}else{
			sql.append(" and cd.device_Used_State='").append(deviceUsedState.toString()).append("'");
		}
		sql.append(" and cd.scrapped_State='").append(UsingStateEnum.NO.toString()).append("'");
		if(user!=null){
			sql.append(" and cd.obtain_User_Id=").append(user.getId());
		}else{
			if(organizationId!=null){
				sql.append(" and cd.organization_Id=").append(organizationId);
			}
		}
		return collectionDeviceDao.findPageBySQL(page, sql.toString());
	}

	@Override
	public void collectionDeviceObtain(Integer orgId,String type,
			List<Integer> collectionDeviceIdList,User user) {
		for (Integer collectionDeviceId : collectionDeviceIdList) {
			CollectionDevice collectionDevice=collectionDeviceDao.find(collectionDeviceId);
			
			if(type.equals("secondment")){
				collectionDevice.setSecondmentState(UsingStateEnum.YES);
			}
			if(orgId!=null){
				Organization organization=organizationDao.find(orgId);
				
				collectionDevice.setOrganizationId(organization.getId());
				collectionDevice.setOrganizationName(organization.getOrgName());
			}
			if(user!=null){
				collectionDevice.setObtainUserId(user.getId());
				
			}
			collectionDevice.setDeviceUsedState(DeviceUsedStateEnum.AUDIT); //状态改为领用

			
			//collectionDevice.setObtainTime(new Date());
			collectionDeviceDao.update(collectionDevice);
		}
		
	}

	@Override
	public void backCollectionDevice(List<Integer> collectionDeviceIdList) {
		for (Integer collectionDeviceId : collectionDeviceIdList) {
			CollectionDevice collectionDevice=collectionDeviceDao.find(collectionDeviceId);
			DevicePurchase devicePurchase=devicePurchaseDao.find(collectionDevice.getPurchaseId());
			collectionDevice.setOrganizationId(devicePurchase.getOrgId());
			collectionDevice.setOrganizationName(devicePurchase.getOrgName());
			collectionDevice.setDeviceUsedState(DeviceUsedStateEnum.PUTINSTORAGE);
			collectionDeviceDao.update(collectionDevice);
		}
		
	}

	@Override
	public void scrappedCollectionDevice(List<Integer> collectionDeviceIdList) {
		for (Integer collectionDeviceId : collectionDeviceIdList) {
			CollectionDevice collectionDevice=collectionDeviceDao.find(collectionDeviceId);
			collectionDevice.setDeviceUsedState(DeviceUsedStateEnum.SCRAP);
			collectionDeviceDao.update(collectionDevice);
		}
		
	}

	@Override
	public void collectionDeviceAudit(Integer type,
			List<Integer> collectionDeviceIdList) {
		for (Integer collectionDeviceId : collectionDeviceIdList) {
			CollectionDevice collectionDevice=collectionDeviceDao.find(collectionDeviceId);
			DeviceUsedStateEnum deviceUsedState=DeviceUsedStateEnum.USED;
			//type=0 不通过，返回仓库状态
			if(type==0){
				deviceUsedState=DeviceUsedStateEnum.PUTINSTORAGE;
			}
			collectionDevice.setDeviceUsedState(deviceUsedState);
			collectionDeviceDao.update(collectionDevice);
		}		
		
	}

	@Override
	public List<CollectionDevice> getListByDeviceUsedStateEnum(DeviceUsedStateEnum deviceUsedStateEnum) {
		String hql = " from CollectionDevice where status = " + StatusEnum.NORMAL.ordinal() + " and deviceUsedState = '" + deviceUsedStateEnum + "' ";
		return collectionDeviceDao.find(hql);
	}
	
	public List<CollectionDevice> getListByObtainUser(Integer userId) {
		String hql = " from CollectionDevice where status = " + StatusEnum.NORMAL.ordinal() + " and obtainUserId = " + userId + " and deviceUsedState = '" 
				+ DeviceUsedStateEnum.TURNOVER.toString() + "' ";
		return collectionDeviceDao.find(hql);
	}

	@Override
	public List<CollectionDevice> getCollectionListByOrgId(
			Integer organizationId) {
		
		return collectionDeviceDao.getCollectionListByOrgId(organizationId);
	}

	@Override
	public List<CollectionDevice> getCollectionListByObtainUserId(
			Integer obtainUserId) {
		
		return collectionDeviceDao.getCollectionListByObtainUserId(
		 obtainUserId) ;
	}

	@Override
	public void updateDeviceUsedStateByDeviceIdList(
			List<Integer> collectionDeviceIdList,
			DeviceUsedStateEnum deviceUsedState) {
		for (Integer collectionDeviceId : collectionDeviceIdList) {
			CollectionDevice collectionDevice=collectionDeviceDao.find(collectionDeviceId) ;
			collectionDevice.setDeviceUsedState(deviceUsedState);
			collectionDeviceDao.update(collectionDevice);

		}
		
	}

	@Override
	public List<CollectionDevice> getListByQueryMap(Map<String, Object> map) {
		return collectionDeviceDao.getListByQueryMap(map);
	}

}
