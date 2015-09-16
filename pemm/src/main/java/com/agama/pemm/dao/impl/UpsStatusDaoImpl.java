package com.agama.pemm.dao.impl;

import java.util.List;

import org.hibernate.transform.ResultTransformer;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.agama.common.dao.impl.HibernateDaoImpl;
import com.agama.pemm.bean.DeviceType;
import com.agama.pemm.dao.IUpsStatusDao;
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
		sql.append("d.device_index as deviceIndex ");
		
		sql.append(
				"from ups_status s right join (select device_index, device_id, collect_time from device,(select device_id,max(collect_time) as collect_time from ups_status  where device_id in (select id from device where device_type=")
				.append(DeviceType.UPS.ordinal()).append(" and git_info_id=");
		sql.append(gitInfoId)
				.append(")  group by device_id) t where device.id=t.device_id) d on d.device_id=s.device_id and d.collect_time=s.collect_time");
		return getSession()
				.createSQLQuery(sql.toString())
				.setResultTransformer(Transformers.aliasToBean(UpsStatus.class))
				.list();

	}

	@Override
	public void updateStatusByIds(String ids) {
		StringBuffer hql = new StringBuffer(
				"update UpsStatus set status=1 where id in (").append(ids).append(
				")");
		this.getSession().createQuery(hql.toString()).executeUpdate();
		
	}

}
