package com.agama.pemm.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.agama.common.dao.impl.HibernateDaoImpl;
import com.agama.common.domain.StateEnum;
import com.agama.common.enumbean.DeviceInterfaceType;
import com.agama.pemm.bean.ThChartBean;
import com.agama.pemm.dao.IThStatusDao;
import com.agama.pemm.domain.ThStatus;

/**
 * @Description:温湿度状态数据访问层
 * @Author:ranjunfeng
 * @Since :2015年10月12日 上午11:18:27
 */
@Repository
public class ThStatusDaoImpl extends HibernateDaoImpl<ThStatus, Integer>
		implements IThStatusDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<ThStatus> findLatestDataByGitInfoId(Integer gitInfoId) {
		StringBuffer sql = new StringBuffer("select s.id,");
		sql.append("s.version as version,");
		sql.append("s.name as name,");
		sql.append("s.interface_type as interfaceType,");
		sql.append("s.model_number as modelNumber,");
		sql.append("s.temperature as temperature,");
		sql.append("s.humidity as humidity,");
		sql.append("d.device_id as deviceId,");
		sql.append("d.device_index as deviceIndex,");
		sql.append("s.temperature_state as temperatureState,");
		sql.append("s.humidity_state as humidityState");
		sql.append(
				" from th_status s right join (select device_index,device_id,collect_time from device,(select device_id,max(collect_time) as collect_time from th_status  where device_id in (select id from device where device_interface_type=")
				.append(DeviceInterfaceType.TH.ordinal())
				.append(" and git_info_id=")
				.append(gitInfoId)
				.append(")  group by device_id) t where device.id=t.device_id and device.status=0) d on d.device_id=s.device_id and d.collect_time=s.collect_time order by d.device_index asc");
		return getSession().createSQLQuery(sql.toString())
				.setResultTransformer(Transformers.aliasToBean(ThStatus.class))
				.list();
	}

	@Override
	public List<ThChartBean> getListByDeviceId(Integer deviceId,
			Date beginDate, Date endDate) {
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		StringBuffer hql=new StringBuffer("select temperature as temperature,humidity as humidity,collectTime as collectTime from ThStatus where status=0");
		if(deviceId!=null){
			hql.append(" and deviceId=").append(deviceId);
		}
		if(beginDate!=null){
			hql.append(" and collectTime>='").append(simpleDateFormat.format(beginDate)).append("'");
		}
		if(endDate!=null){
			hql.append(" and collectTime<='").append(simpleDateFormat.format(endDate)).append("'");
		}
		hql.append(" order by collectTime asc");
		
		return this.getSession().createQuery(hql.toString()).setResultTransformer(Transformers.aliasToBean(ThChartBean.class)).list();
	}

	@Override
	public void updateStatusByGitInfoIds(String gitInfoIds, int status) {
		StringBuffer hql=new StringBuffer("update ThStatus set status=").append(status).append(" where status!=").append(status);
		if(gitInfoIds!=null){
			hql.append(" and deviceId in (select id from Device where gitInfo.id in (").append(gitInfoIds).append("))");
		}
		this.batchExecute(hql.toString());
	}

	@Override
	public void updateStatusDeviceIds(String deviceIds, int status) {
		StringBuffer hql=new StringBuffer("update ThStatus set status=").append(status).append(" where status!=").append(status);
		if(deviceIds!=null){
			hql.append(" and deviceId in (").append(deviceIds).append(")");
			
		}
		this.batchExecute(hql.toString());
	}

}
