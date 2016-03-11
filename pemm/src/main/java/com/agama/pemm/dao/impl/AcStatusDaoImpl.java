package com.agama.pemm.dao.impl;

import java.util.List;

import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.agama.common.dao.impl.HibernateDaoImpl;
import com.agama.common.enumbean.DeviceInterfaceType;
import com.agama.pemm.dao.IAcStatusDao;
import com.agama.pemm.domain.AcStatus;
import com.agama.pemm.domain.ThStatus;
@Repository
public class AcStatusDaoImpl extends HibernateDaoImpl<AcStatus, Integer>
		implements IAcStatusDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<AcStatus> findLastestDataByGitInfoId(Integer gitInfoId) {
		StringBuffer sql=new StringBuffer("select s.id as id,");
		sql.append("d.name as name,");
		sql.append("s.version as version,");
		sql.append("s.indoor_temperature as indoorTemperature,");
		sql.append("s.indoor_humidity as indoorHumidity,");
		sql.append("s.outdoor_temperature as outdoorTemperature,");
		sql.append("s.run_state as runStateOrdinal,");
		sql.append("s.unit_state as unitStateOrdinal,");
		sql.append("d.device_index as deviceIndex,");
		sql.append("d.device_id as deviceId");
		sql.append(" from ac_status s right join (select device_index,device_id,collect_time,name from device,(select device_id,max(collect_time) as collect_time from ac_status  where device_id in (select id from device where device_interface_type=")
				.append(DeviceInterfaceType.AC.ordinal())
				.append(" and git_info_id=")
				.append(gitInfoId)
				.append(")  group by device_id) t where device.id=t.device_id and device.status=0) d on d.device_id=s.device_id and d.collect_time=s.collect_time order by d.device_index asc");
		return getSession().createSQLQuery(sql.toString()).setResultTransformer(Transformers.aliasToBean(AcStatus.class))
				.list();
	}

	

}
