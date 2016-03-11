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
import com.agama.device.dao.IUnintelligentDeviceDao;
import com.agama.device.domain.DevicePurchase;
import com.agama.device.domain.UnintelligentDevice;
import com.agama.device.service.IUnintelligentDeviceService;

@Service
@Transactional
public class UnintelligentDeviceServiceImpl extends
		BaseServiceImpl<UnintelligentDevice, Integer> implements
		IUnintelligentDeviceService {

	@Autowired
	private IUnintelligentDeviceDao unintelligentDeviceDao;
	@Autowired
	private IOrganizationDao organizationDao;
	@Autowired
	private IDevicePurchaseDao devicePurchaseDao;

	@Override
	public void updateManager(Integer id, Integer managerId, String managerName) {
		String hql = "update UnintelligentDevice set managerId=?0,managerName=?1 where id=?2";
		unintelligentDeviceDao.batchExecute(hql, managerId, managerName, id);
	}

	@Override
	public void updateRole(Integer id, Integer roleId, String roleName) {
		String hql = "update UnintelligentDevice set roleId=?0,roleName=?1 where id=?2";
		unintelligentDeviceDao.batchExecute(hql, roleId, roleName, id);
	}

	@Override
	public void updateUserDeviceType(Integer id, Integer userDeviceTypeId,
			String userDeviceTypeName) {
		String hql = "update UnintelligentDevice set userDeviceTypeId=?0,userDeviceTypeName=?1 where id=?2";
		unintelligentDeviceDao.batchExecute(hql, userDeviceTypeId,
				userDeviceTypeName, id);
	}

	@Override
	public UnintelligentDevice getUnintelligentDeviceByIdentifier(
			String identifier) {
		String hql = "from UnintelligentDevice where identifier = '"
				+ identifier + "'";
		List<UnintelligentDevice> list = unintelligentDeviceDao.find(hql);
		if (!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<UnintelligentDevice> getUnintelligentDeviceByOrgId(int orgId) {
		String hql = "from UnintelligentDevice where organizationId = " + orgId
				+ " and status = " + StatusEnum.NORMAL.ordinal();
		return unintelligentDeviceDao.find(hql);
	}

	@Override
	public List<Object> getNameAndIdentifierByOrgId(int orgId) {
		String hql = "select new Map(name as name,identifier as identifier) from UnintelligentDevice where status = "
				+ StatusEnum.NORMAL.ordinal()
				+ " and organizationId = "
				+ orgId;
		return unintelligentDeviceDao.find(hql);
	}

	@Override
	public List<UnintelligentDevice> getAllList() {
		String hql = "from UnintelligentDevice where status = "
				+ StatusEnum.NORMAL.ordinal();
		return unintelligentDeviceDao.find(hql);
	}

	@Override
	public Map<String, Object> getDetailByIdentifierForHandset(String identifier) {
		String hql = "select new Map(b.name as brand,di.firstDeviceType as firstDeviceType, di.secondDeviceType as secondDeviceType, "
				+ " o.orgName as hallName, hd.name as name, hd.enable as enable, hd.remark as description, hd.model as model, hd.managerId as manager, "
				+ " CONCAT(hd.manufactureDate) as manufactureDate, CONCAT(hd.warrantyDate) as warrantyDate, hd.identifier as identifier) "
				+ " from UnintelligentDevice hd, Brand b, DevicePurchase dp, Organization o, DeviceInventory di "
				+ " where hd.identifier ='"
				+ identifier
				+ "' and hd.organizationId = o.id and hd.purchaseId = dp.id "
				+ " and dp.deviceInventoryId = di.id and di.brandId = b.id";
		List<Map<String, Object>> list = unintelligentDeviceDao.find(hql);
		if (!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public Page<UnintelligentDevice> searchObtainUnintelligentDeviceList(
			Page<UnintelligentDevice> page, String organizationId,
			StatusEnum status, DeviceUsedStateEnum deviceUsedState,User user) {
		StringBuffer sql=new StringBuffer("select ud.id as id,");
		sql.append("ud.identifier as identifier,");
		sql.append("ud.manufacture_Date as manufactureDate,");
		sql.append("ud.warranty_date as warrantyDate,");
		sql.append("ud.name as name,");
		sql.append("ud.remark as remark,");
		sql.append("ud.model as model,");
		sql.append("ud.update_time as updateTime,");
		sql.append("ud.manager_id as managerId,");
		sql.append("managerUser.name as managerName,");
		sql.append("ud.organization_Id as organizationId,");
		sql.append("o.org_Name as organizationName,");
		sql.append("ud.device_Used_State as deviceUsedStateValue,");
		sql.append("ud.obtain_User_Id as obtainUserId,");
		sql.append("obtainUser.name as obtainUserName ");
		sql.append("from unintelligent_Device ud ");
		
		sql.append("left join User obtainUser ");
		sql.append("on ud.obtain_User_Id=obtainUser.id ");
		sql.append("left join User managerUser ");
		sql.append("on ud.manager_Id=managerUser.id ");
		sql.append("left join Organization o ");
		sql.append("on ud.organization_Id=o.id ");
		sql.append("where ud.status=").append(status.getId());
		//如果设备状态为领用，则查询条件为领用和审核
		if(deviceUsedState==DeviceUsedStateEnum.USED){
			sql.append(" and (ud.device_Used_State='");
			if(user!=null){
				sql.append(DeviceUsedStateEnum.TURNOVER);
			}else{
				sql.append(DeviceUsedStateEnum.USED);
			}
			
			sql.append("' or ud.device_Used_State='").append(DeviceUsedStateEnum.AUDIT.toString()).append("')");
		}else{
			sql.append(" and ud.device_Used_State='").append(deviceUsedState.toString()).append("'");
		}
		sql.append(" and ud.scrapped_State='").append(UsingStateEnum.NO.toString()).append("'");

		if(user!=null){
			sql.append(" and ud.obtain_User_Id=").append(user.getId());
		}else{
			if(organizationId!=null){
				sql.append(" and ud.organization_Id=").append(organizationId);
			}
		}
		return unintelligentDeviceDao.findPageBySQL(page, sql.toString());
	}

	@Override
	public void unintelligentDeviceObtain(Integer orgId,String type,
			List<Integer> unintelligentDeviceIdList,User user) {
		for (Integer unintelligentDeviceId : unintelligentDeviceIdList) {
			UnintelligentDevice unintelligentDevice=unintelligentDeviceDao.find(unintelligentDeviceId);
				
			if(type.equals("secondment")){
				unintelligentDevice.setSecondmentState(UsingStateEnum.YES);
			}
			if(orgId!=null){
				Organization organization=organizationDao.find(orgId);	
				unintelligentDevice.setOrganizationId(organization.getId());
				unintelligentDevice.setOrganizationName(organization.getOrgName());
			}
			if(user!=null){
				unintelligentDevice.setObtainUserId(user.getId());
				

			}
				unintelligentDevice.setDeviceUsedState(DeviceUsedStateEnum.AUDIT);  //状态改为领用

			
			//unintelligentDevice.setObtainTime(new Date());
			unintelligentDeviceDao.update(unintelligentDevice);
		}

	}

	@Override
	public void backUnintelligentDevice(List<Integer> unintelligentDeviceIdList) {
		for (Integer unintelligentDeviceId : unintelligentDeviceIdList) {
			UnintelligentDevice unintelligentDevice=unintelligentDeviceDao.find(unintelligentDeviceId);
			DevicePurchase devicePurchase=devicePurchaseDao.find(unintelligentDevice.getPurchaseId());
			unintelligentDevice.setOrganizationId(devicePurchase.getOrgId());
			unintelligentDevice.setOrganizationName(devicePurchase.getOrgName());
			unintelligentDevice.setDeviceUsedState(DeviceUsedStateEnum.PUTINSTORAGE); 
			unintelligentDeviceDao.update(unintelligentDevice);

		}

	}

	@Override
	public void scrappedUnintelligentDevice(
			List<Integer> unintelligentDeviceIdList) {
		for (Integer unintelligentDeviceId : unintelligentDeviceIdList) {
			UnintelligentDevice unintelligentDevice=unintelligentDeviceDao.find(unintelligentDeviceId);
			unintelligentDevice.setDeviceUsedState(DeviceUsedStateEnum.SCRAP);
			unintelligentDeviceDao.update(unintelligentDevice);

		}
		
	}

	@Override
	public void unintelligentDeviceAudit(Integer type,
			List<Integer> unintelligentDeviceIdList) {
		for (Integer unintelligentDeviceId : unintelligentDeviceIdList) {
			UnintelligentDevice unintelligentDevice=unintelligentDeviceDao.find(unintelligentDeviceId);
			DeviceUsedStateEnum deviceUsedState=DeviceUsedStateEnum.USED;
			//type=0 不通过，返回仓库状态
			if(type==0){
				deviceUsedState=DeviceUsedStateEnum.PUTINSTORAGE;
			}
			unintelligentDevice.setDeviceUsedState(deviceUsedState);
			unintelligentDeviceDao.update(unintelligentDevice);
		}		
		
	}
	
	@Override
	public List<UnintelligentDevice> getListByDeviceUsedStateEnum(DeviceUsedStateEnum deviceUsedStateEnum) {
		String hql = " from UnintelligentDevice where status = " + StatusEnum.NORMAL.ordinal() + " and deviceUsedState = '" + deviceUsedStateEnum + "' ";
		return unintelligentDeviceDao.find(hql);
	}
	
	public List<UnintelligentDevice> getListByObtainUser(Integer userId) {
		String hql = " from UnintelligentDevice where status = " + StatusEnum.NORMAL.ordinal() + " and obtainUserId = " + userId + " and deviceUsedState = '" 
				+ DeviceUsedStateEnum.TURNOVER.toString() + "' ";
		return unintelligentDeviceDao.find(hql);
	}
	
	public List<UnintelligentDevice> getListByQueryMap(Map<String, Object> map) {
		return unintelligentDeviceDao.getListByQueryMap(map);
	}

	@Override
	public void updateDeviceUsedStateByDeviceIdList(
			List<Integer> unintelligentDeviceIdList,
			DeviceUsedStateEnum deviceUsedState) {
		for (Integer unintelligentDeviceId : unintelligentDeviceIdList) {
			UnintelligentDevice unintelligentDevice=unintelligentDeviceDao.find(unintelligentDeviceId) ;
			unintelligentDevice.setDeviceUsedState(deviceUsedState);
			unintelligentDeviceDao.update(unintelligentDevice);
		}
		
	}

}
