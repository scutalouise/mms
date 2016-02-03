package com.agama.pemm.dao.impl;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.agama.common.dao.impl.HibernateDaoImpl;
import com.agama.common.domain.StateEnum;
import com.agama.common.enumbean.DeviceInterfaceType;
import com.agama.common.enumbean.DeviceType;
import com.agama.pemm.bean.DeviceStateRecord;
import com.agama.pemm.dao.IDeviceDao;
import com.agama.pemm.domain.Device;
import com.agama.pemm.task.DataCollectionService;

@Repository
public class DeviceDaoImpl extends HibernateDaoImpl<Device, Integer>implements IDeviceDao {

	@Override
	public void updateStatusByIds(String ids) {
		// TODO Auto-generated method stub
		StringBuffer hql = new StringBuffer("update Device set status=1 where id in (").append(ids).append(")");
		this.getSession().createQuery(hql.toString()).executeUpdate();

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DeviceStateRecord> getDeviceStateRecordByOrganizationId(String organizationIdStr) {
		StringBuffer hql = new StringBuffer(
				"select gitInfo.id as gitInfoId,deviceInterfaceType as deviceInterfaceType,count(gitInfo.id) as count,currentState as currentState,stateDetails as stateDetails from Device where status=0 ");
		if (organizationIdStr != null) {
			hql.append(" and gitInfo.organizationId in (").append(organizationIdStr).append(")");
		}
		hql.append(" group by gitInfo.id,deviceInterfaceType");

		return getSession().createQuery(hql.toString())
				.setResultTransformer(Transformers.aliasToBean(DeviceStateRecord.class)).list();
	}

	@Override
	public void updateCurrentStateById(Integer id, StateEnum stateEnum) {
		if(stateEnum.ordinal()<DataCollectionService.deviceCurrentState.ordinal()){
			stateEnum=DataCollectionService.deviceCurrentState;
		}
		StringBuffer hql = new StringBuffer("update Device set currentState=").append(stateEnum.ordinal())
				.append(" where id=").append(id);
		this.batchExecute(hql.toString());

	}

	@Override
	public List<Map<String, Object>> getCurrentStateAndCount() {
		String hql = "select new map(currentState as currentState,count(currentState) as num) from Device where status=0 group by currentState";
		return this.getSession().createQuery(hql.toString()).list();
	}

	@Override
	public Integer statisticBranchStateNum(StateEnum stateEnum) {
		String str = " and o.id NOT IN ( SELECT id FROM ( SELECT o.id, d.current_state FROM organization o, git_info g, device d WHERE o.id = g.organization_id AND g.id = d.git_info_id AND g.status = 0 AND d.status = 0 GROUP BY o.id, d.current_state ) t WHERE ";
		if (stateEnum == StateEnum.good) {
			str += "t.current_state = 1 OR t.current_state = 2)";
		} else if (stateEnum == StateEnum.warning) {
			str += "t.current_state = 2)";
		} else {
			str = "";
		}

		String hql = "select count(*) as count FROM (SELECT o.id FROM organization o, git_info g, device d WHERE  o.id = g.organization_id AND d.git_info_id = g.id and g.status = 0 AND d.status = 0 AND d.current_state = "
				+ stateEnum.ordinal() + str + " group by id) t";

		return ((BigInteger) this.getSession().createSQLQuery(hql).uniqueResult()).intValue();
	}

	@Override
	public void updateStatusByGitInfoIds(String gitInfoIds, int status) {
		StringBuffer hql = new StringBuffer("update Device set status=").append(status);
		if (gitInfoIds != null) {
			hql.append(" where gitInfo.id in (").append(gitInfoIds).append(")");
		}

		this.batchExecute(hql.toString());

	}

	@Override
	public List<Device> getDeviceListByGitIdAndStatus(Integer gitInfoId, int status) {
		String hql = "from Device where status=" + status;
		if (gitInfoId != null) {
			hql += " and gitInfo.id=" + gitInfoId;
		}
		return find(hql);
	}

	@Override
	public List<Device> getDeviceListByScheduleNameAndScheduleGroup(String scheduleName, String scheduleGroup) {
		StringBuffer hql = new StringBuffer("from Device where status=0 and deviceInterfaceType=");
		hql.append(DeviceInterfaceType.UPS.ordinal())
				.append(" and id in (select deviceId from DischargeTask where status=0");
		if (scheduleGroup != null) {

			hql.append(" and scheduleGroup='").append(scheduleGroup).append("'");
		}
		if (scheduleName != null) {

			hql.append(" and scheduleName='").append(scheduleName).append("'");
		}
		hql.append(")");
		return this.find(hql.toString());
	}

	@Override
	public List<Device> getDeviceByIpAndDeviceTypeAndIndex(String ip,
			DeviceType deviceType, Integer index) {
		String hql="from Device where status=0 and deviceType="+deviceType.ordinal()+" and deviceIndex="+index+" and gitInfo.id in (select id from GitInfo where status=0 and ip='"+ip+"')";
		System.out.println(this.find(hql).size());
		return this.find(hql);
	}

	@Override
	public List<Device> getWaterDeviceByGitInfoIdAndDeviceInterfaceType(
			Integer gitInfoId, DeviceInterfaceType deviceInterfaceType) {
		String hql="from Device where status=0 and gitInfo.id="+gitInfoId+" and deviceInterfaceType="+deviceInterfaceType.ordinal();
		return this.find(hql);
	}

	@Override
	public StateEnum findSeverityStateByGitInfoId(Integer id) {
		String hql="from Device where status=0 and gitInfo.id="+id+" order by currentState desc";
		List<Device> deviceList=find(hql);
		if(deviceList.size()>0){
			return deviceList.get(0).getCurrentState();
		}
		return null;
	}
}
