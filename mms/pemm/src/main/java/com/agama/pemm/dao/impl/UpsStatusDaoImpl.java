package com.agama.pemm.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.agama.common.dao.impl.HibernateDaoImpl;
import com.agama.common.enumbean.DeviceInterfaceType;
import com.agama.common.enumbean.DeviceType;
import com.agama.pemm.bean.UpsChartBean;
import com.agama.pemm.dao.IUpsStatusDao;
import com.agama.pemm.domain.Device;
import com.agama.pemm.domain.UpsStatus;

@Repository(value = "upsStatusDao")
public class UpsStatusDaoImpl extends HibernateDaoImpl<UpsStatus, Integer>
		implements IUpsStatusDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<UpsStatus> findLatestDataByGitInfoId(Integer gitInfoId) {
		StringBuffer sql = new StringBuffer("select s.id as id,");
		sql.append("s.version as version,");
		sql.append("s.name as name,");
		sql.append("s.interface_type as interfaceType,");
		sql.append("s.communication_status as communicationStatus,");
		sql.append("s.discharge_patterns as dischargePatterns,");
		sql.append("s.ups_type as upsType,");
		sql.append("s.model_number as modelNumber,");
		sql.append("s.brand as brand,");
		sql.append("s.version_number as versionNumber,");
		sql.append("s.rate_voltage as rateVoltage,");
		sql.append("s.rated_current as ratedCurrent,");
		sql.append("s.rated_frequency as ratedFrequency,");
		sql.append("s.battery_voltage as batteryVoltage,");
		sql.append("s.power as power,");
		sql.append("s.ups_status as upsStatus,");
		sql.append("s.frequency as frequency,");
		sql.append("s.internal_temperature as internalTemperature,");
		sql.append("s.bypass_voltage as bypassVoltage,");
		sql.append("s.bypass_frequency as bypassFrequency,");
		sql.append("s.input_voltage as inputVoltage,");
		sql.append("s.output_voltage as outputVoltage,");
		sql.append("s.error_voltage as errorVoltage,");
		sql.append("s.ups_load as upsLoad,");
		sql.append("s.output_frenquency as outputFrenquency,");
		sql.append("s.single_voltage as singleVoltage,");
		sql.append("s.total_voltage as totalVoltage,");
		sql.append("s.electric_quantity as electricQuantity,");
		sql.append("s.pass_current as passCurrent,");
		sql.append("s.remaining_time as remainingTime,");
		sql.append("s.collect_time as collectTime,");
		sql.append("s.status as status,");
		sql.append("s.city_Voltage_Status as cityVoltageStatus,");
		sql.append("s.battery_Voltage_Status as batteryVoltageStatus,");
		sql.append("s.running_Status as runningStatus,");
		sql.append("s.test_Status as testStatus,");
		sql.append("s.patterns_Status as patternsStatus,");
		sql.append("s.shutdown_Status as shutdownStatus,");
		sql.append("s.buzzer_Status as buzzerStatus,");
		sql.append("d.device_id as deviceId,");
		sql.append("d.device_index as deviceIndex,");
		sql.append("s.link_State as linkState, ");
		sql.append("d.current_State as currentState,");
		sql.append("d.name as deviceName");
		sql.append(" from ups_status s right join (select device_index, device_id, collect_time,current_state,name from device,(select device_id,max(collect_time) as collect_time from ups_status  where device_id in (select id from device where device_interface_type=")
				.append(DeviceInterfaceType.UPS.ordinal()).append(" and git_info_id=");
		sql.append(gitInfoId)
				.append(")  group by device_id) t where device.id=t.device_id and device.status=0) d on d.device_id=s.device_id and d.collect_time=s.collect_time order by d.device_index asc");
		return getSession()
				.createSQLQuery(sql.toString())
				.setResultTransformer(Transformers.aliasToBean(UpsStatus.class))
				.list();

	}
	

	@Override
	public void updateStatusByIds(String ids) {
		StringBuffer hql = new StringBuffer(
				"update UpsStatus set status=1 where id in (").append(ids).append(
				") where status=0");
		this.getSession().createQuery(hql.toString()).executeUpdate();
		
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<UpsChartBean> getUpsChartListByDeviceId(Integer deviceId,
			Date beginDate, Date endDate) {
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		StringBuffer hql=new StringBuffer("select inputVoltage as inputVoltage,outputVoltage as outputVoltage,upsLoad as upsLoad,batteryVoltage as batteryVoltage,collectTime as collectTime ,upsType as upsType from UpsStatus where status=0");
		if(deviceId!=null){
			hql.append(" and device.id=").append(deviceId);
		}
		if(beginDate!=null){
			hql.append(" and collectTime>='").append(simpleDateFormat.format(beginDate)).append("'");
		}
		if(endDate!=null){
			hql.append(" and collectTime<='").append(simpleDateFormat.format(endDate)).append("'");
		}
		hql.append(" order by collectTime asc");
		
		return this.getSession().createQuery(hql.toString()).setResultTransformer(Transformers.aliasToBean(UpsChartBean.class)).list();
	}


	@Override
	public void updateStatusByGitInfoIds(String gitInfoIds, int status) {
		StringBuffer deviceHql=new StringBuffer("select id from Device where deviceType=").append(DeviceType.UPS.ordinal()).append(" and gitInfo.id in (").append(gitInfoIds).append(")");
		List<Device> deviceList=find(deviceHql.toString());
		
	
		if(deviceList.size()>0){
			StringBuffer hql=new StringBuffer("update UpsStatus set status=").append(status).append(" where status!=").append(status);
				
			hql.append(" and device.id in (").append(StringUtils.join(deviceList.toArray(),",")).append(")");
		
			this.batchExecute(hql.toString());
		
		}
	}


	@Override
	public void updateStatusDeviceIds(String deviceIds, int status) {
		StringBuffer hql=new StringBuffer("update UpsStatus set status=").append(status).append(" where status!=").append(status);
		if(deviceIds!=null){
			hql.append(" and device.id in (").append(deviceIds).append(")");
		}
		this.batchExecute(hql.toString());
	}


	@SuppressWarnings("unchecked")
	@Override
	public UpsStatus findNewData(Integer deviceId) {
		String hql="from UpsStatus where status=0 and device.id="+deviceId+" order by collectTime desc";
		Query query=this.getSession().createQuery(hql);
		query.setFirstResult(0);
		query.setFetchSize(1);
		List<UpsStatus> list=query.list();
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}

}
