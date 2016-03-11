package com.agama.itam.dao.impl;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import com.agama.common.dao.impl.HibernateDaoImpl;
import com.agama.common.dao.utils.Page;
import com.agama.common.enumbean.StatusEnum;
import com.agama.itam.dao.IOutsideRepairDao;
import com.agama.itam.domain.OutsideRepair;
import com.agama.tool.utils.string.StringUtils;

/**
 * @Description:外修记录数据访问层实现类
 * @Author:佘朝军
 * @Since :2016年3月2日 上午11:13:28
 */
@Repository
public class OutsideRepairDaoImpl extends HibernateDaoImpl<OutsideRepair, Serializable> implements IOutsideRepairDao {

	@Override
	public Page<OutsideRepair> getListForPage(Page<OutsideRepair> page, String startTime, String endTime) {
		String hql = " select new OutsideRepair(out.id as id, out.identifier as identifier, out.firm as firm, smo.orgName as firmName,"
				+ " out.repairOpertor as repairOpertor, rou.name as repairOpertorName, out.repairReceiver as repairReceiver,"
				+ " rru.name as repairReceiverName, out.repairTime as repairTime, out.repairRemark as repairRemark, out.repairResult as repairResult,"
				+ " out.returnTime as returnTime, out.returnReceiver as returnReceiver, out.returnRemark as returnRemark) " 
				+ " from OutsideRepair out, User rou, User rru, SupplyMaintainOrg smo where rou.id = out.repairOpertor and"
				+ " rru.id = out.repairReceiver and smo.id = out.firm and out.status = '" + StatusEnum.NORMAL.toString() + "' ";
		StringBuffer sb = new StringBuffer(hql);
		if (StringUtils.isNotBlank(startTime)) {
			sb.append(" and out.repairTime >= '" + startTime + " 00:00:00' ");
		}
		if (StringUtils.isNoneBlank(endTime)) {
			sb.append(" and out.repairTime <= '" + endTime + " 23:59:59' ");
		}
		return this.findPage(page, sb.toString());
	}

}
