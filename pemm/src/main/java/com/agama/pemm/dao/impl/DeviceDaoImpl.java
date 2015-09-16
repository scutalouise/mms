package com.agama.pemm.dao.impl;

import java.util.List;

import javax.persistence.Query;

import javassist.convert.Transformer;



import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.agama.common.dao.impl.HibernateDaoImpl;
import com.agama.pemm.bean.DeviceStateRecord;
import com.agama.pemm.dao.IDeviceDao;
import com.agama.pemm.domain.Device;

@Repository
public class DeviceDaoImpl extends HibernateDaoImpl<Device, Integer> implements
		IDeviceDao {

	@Override
	public void updateStatusByIds(String ids) {
		// TODO Auto-generated method stub
		StringBuffer hql = new StringBuffer(
				"update Device set status=1 where id in (").append(ids).append(
				")");
		this.getSession().createQuery(hql.toString()).executeUpdate();

	}

	/* (non-Javadoc)
	 * @see com.agama.pemm.dao.IDeviceDao#getDeviceStateRecordByAreaInfoId(java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DeviceStateRecord> getDeviceStateRecordByAreaInfoId(
			String areaInfoIdStr) {
		StringBuffer hql = new StringBuffer("select gitInfo.id as gitInfoId,deviceType as deviceType,count(gitInfo.id) as count,currentState as currentState,stateDetails as stateDetails from Device where status=0 ");
		if(areaInfoIdStr!=null){
			hql.append(" and gitInfo.areaInfoId in (").append(areaInfoIdStr).append(")");
		}
	 hql.append(" group by gitInfo.id,deviceType");
		
		
	
		return getSession().createQuery(hql.toString()).setResultTransformer(Transformers.aliasToBean(DeviceStateRecord.class)).list();
	}
}
