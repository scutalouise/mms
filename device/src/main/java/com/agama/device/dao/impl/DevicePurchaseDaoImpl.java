package com.agama.device.dao.impl;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.agama.common.dao.impl.HibernateDaoImpl;
import com.agama.common.enumbean.FirstDeviceType;
import com.agama.device.dao.IDevicePurchaseDao;
import com.agama.device.domain.DevicePurchase;


@Repository
public class DevicePurchaseDaoImpl extends HibernateDaoImpl<DevicePurchase, Integer>implements IDevicePurchaseDao {

	@Override
	public Integer getObtainCountByPurchaseId(Integer purchaseId) {
		StringBuffer hql = new StringBuffer("select b.firstDeviceType from DevicePurchase dp, Brand b ");
		hql.append("where dp.brandId=b.id and dp.id=?0 ");
		
		Query query = createQuery(hql.toString(), purchaseId);
		FirstDeviceType firstDeviceType = (FirstDeviceType) query.list().get(0);
		Long count = null ;
		//TODO  需要些具体的获取采取记录已经领取的数量；
		switch(firstDeviceType.toString()){
			case "COLLECTDEVICE":count = countHqlResult(" from CollectionDevice cd where cd.purchaseId=?0 ", purchaseId);break;
			case "HOSTDEVICE": count = countHqlResult(" from HostDevice hd where hd.purchaseId=?0 ", purchaseId);break;
			case "NETWORKDEVICE": count = countHqlResult(" from NetworkDevice nd where nd.purchaseId=?0 ", purchaseId);break;
			case "PEDEVICE": count = countHqlResult(" from PeDevice pd where pd.purchaseId=?0 ", purchaseId);break;
			case "UNINTELLIGENTDEVICE": count = countHqlResult(" from UnintelligentDevice ud where ud.purchaseId=?0 ", purchaseId);
		}
		return count==null ? 0 : count.intValue();
	}

}
