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
import com.agama.common.enumbean.DeviceType;
import com.agama.common.enumbean.DeviceUsedStateEnum;
import com.agama.common.enumbean.SecondDeviceType;
import com.agama.common.enumbean.StatusEnum;
import com.agama.common.enumbean.UsingStateEnum;
import com.agama.common.service.impl.BaseServiceImpl;
import com.agama.device.dao.IDevicePurchaseDao;
import com.agama.device.dao.IPeDeviceDao;
import com.agama.device.domain.DevicePurchase;
import com.agama.device.domain.PeDevice;
import com.agama.device.service.IPeDeviceService;

@Service
@Transactional
public class PeDeviceServiceImpl extends BaseServiceImpl<PeDevice, Integer> implements IPeDeviceService {
	@Autowired
	private IPeDeviceDao peDeviceDao;
	@Autowired
	private IOrganizationDao organizationDao;
	@Autowired
	private IDevicePurchaseDao devicePurchaseDao;

	@Override
	public List<PeDevice> getPeDeviceByIpAndDeviceTypeAndIndex(String ip, DeviceType switchinput, Integer index) {

		return peDeviceDao.getPeDeviceByIpAndDeviceTypeAndIndex(ip, switchinput, index);
	}
	@Override
	public List<Map<String, Object>> getCurrentStateAndCount() {
		return peDeviceDao.getCurrentStateAndCount();
	}

	@Override
	public PeDevice getPeDeviceByIdentifier(String identifier) {
		String hql = "from PeDevice where identifier = '" + identifier + "'";
		List<PeDevice> list = peDeviceDao.find(hql);
		if (!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<PeDevice> getPeDeviceByOrgId(int orgId) {
		String hql = "from PeDevice where organizationId = " + orgId + " and status = " + StatusEnum.NORMAL.ordinal();
		return peDeviceDao.find(hql);
	}

	@Override
	public List<Object> getNameAndIdentifierByOrgId(int orgId) {
		String hql = "select new Map(name as name,identifier as identifier) from PeDevice where status = " + StatusEnum.NORMAL.ordinal() + " and organizationId = " + orgId;
		return peDeviceDao.find(hql);
	}

	@Override
	public List<PeDevice> getAllList() {
		String hql = "from PeDevice where status = " + StatusEnum.NORMAL.ordinal();
		return peDeviceDao.find(hql);
	}

	@Override
	public Map<String, Object> getDetailByIdentifierForHandset(String identifier) {
		String hql = "select new Map(b.name as brand,di.firstDeviceType as firstDeviceType, di.secondDeviceType as secondDeviceType, "
				+ " o.orgName as hallName, hd.name as name, hd.enable as enable, hd.remark as description, hd.model as model, hd.managerId as manager, "
				+ " CONCAT(hd.manufactureDate) as manufactureDate, CONCAT(hd.warrantyDate) as warrantyDate, hd.identifier as identifier) "
				+ " from PeDevice hd, Brand b, DevicePurchase dp, Organization o, DeviceInventory di "
				+ " where hd.identifier ='" + identifier + "' and hd.organizationId = o.id and hd.purchaseId = dp.id "
				+ " and dp.deviceInventoryId = di.id and di.brandId = b.id";
		List<Map<String, Object>> list = peDeviceDao.find(hql);
		if (!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}
	@Override
	public Page<PeDevice> searchObtainPeDeviceList(Page<PeDevice> page,
			String organizationId, StatusEnum status, DeviceUsedStateEnum deviceUsedState,User user) {
		StringBuffer sql=new StringBuffer("select pd.id as id,");
		sql.append("pd.identifier as identifier,");
		sql.append("pd.manufacture_Date as manufactureDate,");
		sql.append("pd.warranty_Date as warrantyDate,");
		sql.append("pd.name as name,");
		sql.append("pd.remark as remark,");
		sql.append("pd.model as model,");
		sql.append("pd.update_Time as updateTime,");
		sql.append("pd.manager_Id as managerId,");
		sql.append("managerUser.name as managerName,");
		sql.append("pd.organization_Id as organizationId,");
		sql.append("o.org_Name as organizationName,");
		sql.append("pd.device_Used_State as deviceUsedStateValue,");
		sql.append("pd.obtain_User_Id as obtainUserId,");
		sql.append("obtainUser.name as obtainUserName ");
		sql.append("from pe_Device pd ");
		
		sql.append("left join User obtainUser ");
		sql.append("on pd.obtain_User_Id=obtainUser.id ");
		sql.append("left join User managerUser ");
		sql.append("on pd.manager_Id=managerUser.id ");
		sql.append("left join Organization o ");
		sql.append("on pd.organization_Id=o.id ");
		sql.append("where pd.status=").append(status.getId());
		
		//如果设备状态为领用，则查询条件为领用和审核
		if(deviceUsedState==DeviceUsedStateEnum.USED){
			sql.append(" and (pd.device_Used_State='");
			if(user!=null){
				sql.append(DeviceUsedStateEnum.TURNOVER);
			}else{
				sql.append(DeviceUsedStateEnum.USED);
			}

			sql.append("' or pd.device_Used_State='").append(DeviceUsedStateEnum.AUDIT.toString()).append("')");
		}else{
			sql.append(" and pd.device_Used_State='").append(deviceUsedState.toString()).append("'");
		}
		sql.append(" and pd.scrapped_State='").append(UsingStateEnum.NO.toString()).append("'");
		if(user!=null){
			sql.append(" and pd.obtain_User_Id=").append(user.getId());
		}else{
			if(organizationId!=null){
				sql.append(" and pd.organization_Id=").append(organizationId);
			}
		}
		return peDeviceDao.findPageBySQL(page, sql.toString());
	}
	@Override
	public void peDeviceObtain(Integer orgId,String type, List<Integer> peDeviceIdList,User user) {
		for (Integer peDeviceId : peDeviceIdList) {
			PeDevice peDevice=peDeviceDao.find(peDeviceId);
			
			if(type.equals("secondment")){
				peDevice.setSecondmentState(UsingStateEnum.YES);
			}
			if(orgId!=null){
				Organization organization=organizationDao.find(orgId);	
				peDevice.setOrganizationId(organization.getId());
				peDevice.setOrganizationName(organization.getOrgName());
			}
			if(user!=null){
				peDevice.setObtainUserId(user.getId());
				

			}
			peDevice.setDeviceUsedState(DeviceUsedStateEnum.AUDIT); //状态改为领用

			
			//peDevice.setObtainTime(new Date());
			peDeviceDao.update(peDevice);
		}
		
	}
	@Override
	public void backPeDevice(List<Integer> peDeviceIdList) {
		for (Integer peDeviceId : peDeviceIdList) {
			PeDevice peDevice=peDeviceDao.find(peDeviceId);
			DevicePurchase devicePurchase=devicePurchaseDao.find(peDevice.getPurchaseId());
			peDevice.setOrganizationId(devicePurchase.getOrgId());
			peDevice.setOrganizationName(devicePurchase.getOrgName());
			peDevice.setDeviceUsedState(DeviceUsedStateEnum.PUTINSTORAGE);
			peDeviceDao.update(peDevice);
		}
		
	}
	@Override
	public void scrappedPeDevice(List<Integer> peDeviceIdList) {
		for (Integer peDeviceId : peDeviceIdList) {
			PeDevice peDevice=peDeviceDao.find(peDeviceId);
			peDevice.setDeviceUsedState(DeviceUsedStateEnum.SCRAP);
			peDeviceDao.update(peDevice);
		}
		
	}
	@Override
	public void peDeviceAudit(Integer type, List<Integer> peDeviceIdList) {
		for (Integer peDeviceId : peDeviceIdList) {
			PeDevice petDevice=peDeviceDao.find(peDeviceId);
			DeviceUsedStateEnum deviceUsedState=DeviceUsedStateEnum.USED;
			//type=0 不通过，返回仓库状态
			if(type==0){
				deviceUsedState=DeviceUsedStateEnum.PUTINSTORAGE;
			}
			petDevice.setDeviceUsedState(deviceUsedState);
			peDeviceDao.update(petDevice);
		}	
		
	}
	
	@Override
	public List<PeDevice> getListByDeviceUsedStateEnum(DeviceUsedStateEnum deviceUsedStateEnum) {
		String hql = " from PeDevice where status = " + StatusEnum.NORMAL.ordinal() + " and deviceUsedState = '" + deviceUsedStateEnum + "' ";
		return peDeviceDao.find(hql);
	}
	
	public List<PeDevice> getListByObtainUser(Integer userId) {
		String hql = " from PeDevice where status = " + StatusEnum.NORMAL.ordinal() + " and obtainUserId = " + userId + " and deviceUsedState = '" 
				+ DeviceUsedStateEnum.TURNOVER.toString() + "' ";
		return peDeviceDao.find(hql);
	}
	@Override
	public Long getInventoryCountBySecondDeviceType(Integer orgId,
			SecondDeviceType secondType) {
		// TODO Auto-generated method stub
		return peDeviceDao.getInventoryCountBySecondDeviceType(orgId, secondType);
	}
	@Override
	public void updateDeviceUsedStateByDeviceIdList(List<Integer> peDeviceIdList,DeviceUsedStateEnum deviceUsedState) {
		for (Integer peDeviceId : peDeviceIdList) {
			PeDevice peDevice=peDeviceDao.find(peDeviceId);
			peDevice.setDeviceUsedState(deviceUsedState);
			peDeviceDao.update(peDevice);
		}
		
	}

	
	public List<PeDevice> getListByQueryMap(Map<String, Object> map) {
		return peDeviceDao.getListByQueryMap(map);
	}

}
