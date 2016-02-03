package com.agama.pemm.dao.impl;

import java.util.List;

import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.agama.common.dao.impl.HibernateDaoImpl;
import com.agama.common.enumbean.DeviceInterfaceType;
import com.agama.pemm.dao.ISwitchInputStatusDao;
import com.agama.pemm.domain.SwitchInputStatus;

/**
 * @Description:开关量输入信息状态数据访问层
 * @Author:ranjunfeng
 * @Since :2015年10月15日 下午3:52:26
 */
@Repository
public class SwitchInputStatusDaoImpl extends
		HibernateDaoImpl<SwitchInputStatus, Integer> implements
		ISwitchInputStatusDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<SwitchInputStatus> findLatestData(Integer gitInfoId,DeviceInterfaceType deviceInterfaceType) {
		StringBuffer sql = new StringBuffer("select s.id as id,");
		sql.append("s.version as version,");
		sql.append("s.name as name,");
		sql.append("s.input_signal as inputSignal,");
		sql.append("s.current_state as currentState,");
		sql.append("d.device_id as deviceId,");
		sql.append("d.device_index as deviceIndex");
		sql.append(
				" from switch_input_status s right join (select device_index,device_id,collect_time from device,(select device_id,max(collect_time) as collect_time from switch_input_status where device_id in (select id from device where device_interface_type=")
				.append(deviceInterfaceType.ordinal())
				.append(" and git_info_id=")
				.append(gitInfoId)
				.append(")  group by device_id) t where device.id=t.device_id and device.status=0) d on d.device_id=s.device_id and d.collect_time=s.collect_time order by d.device_index asc");
		return getSession()
				.createSQLQuery(sql.toString())
				.setResultTransformer(
						Transformers.aliasToBean(SwitchInputStatus.class))
				.list();
	}

	@Override
	public void updateStatusByGitInfoIds(String gitInfoIds, int status) {
		// TODO Auto-generated method stub
		StringBuffer hql=new StringBuffer("update SwitchInputStatus set status=").append(status).append(" where status!=").append(status);
		if(gitInfoIds!=null){
			hql.append(" and deviceId in (select id from Device where gitInfo.id in (").append(gitInfoIds).append("))");
			
		}
		this.batchExecute(hql.toString());
		
	}

	@Override
	public void updateStatusDeviceIds(String deviceIds, int status) {
		StringBuffer hql=new StringBuffer("update SwitchInputStatus set status=").append(status).append(" where status!=").append(status);
		if(deviceIds!=null){
			hql.append(" and deviceId in (").append(deviceIds).append(")");
			
		}
		this.batchExecute(hql.toString());
		
	}

}
