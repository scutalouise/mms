package com.agama.itam.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.agama.common.dao.impl.HibernateDaoImpl;
import com.agama.common.dao.utils.Page;
import com.agama.common.enumbean.ProblemStatusEnum;
import com.agama.common.enumbean.StatusEnum;
import com.agama.itam.dao.IProblemDao;
import com.agama.itam.domain.Problem;

/**
 * @Description:问题记录DAO实现类
 * @Author:佘朝军
 * @Since :2016年1月19日 下午2:56:50
 */
@SuppressWarnings("unchecked")
@Repository
public class ProblemDaoImpl extends HibernateDaoImpl<Problem, Serializable> implements IProblemDao {

	@Override
	public List<Problem> getAllList() throws Exception {
		String hql = "from Problem where status = " + StatusEnum.NORMAL.ordinal();
		return (List<Problem>) getSession().createQuery(hql).list();
	}

	@Override
	public Page<Problem> searchListForHandling(String identifier, Integer problemTypeId, Integer enable, String recordTime, String recordEndTime,
			Page<Problem> page) throws Exception {
		String hql = "from Problem where status = " + StatusEnum.NORMAL.ordinal();
		if (!StringUtils.isBlank(identifier)) {
			hql += " and identifier like '%" + identifier.trim() + "%' ";
		}
		if (problemTypeId != null) {
			hql += " and problemTypeId = " + problemTypeId;
		}
		if (enable != null) {
			hql += " and enable = " + enable;
		} else {
			hql += " and enable in (" + ProblemStatusEnum.RESPONSED.ordinal() + "," + ProblemStatusEnum.HANDLING.ordinal() + ","
					+ ProblemStatusEnum.RESOLVED.ordinal() + "," + ProblemStatusEnum.ASSIGNED.ordinal() + ") ";
		}
		if (!StringUtils.isBlank(recordTime)) {
			hql += " and recordTime >= " + recordTime.trim() + " 00:00:00 ";
		}
		if (!StringUtils.isBlank(recordEndTime)) {
			hql += " and recordTime <= " + recordEndTime.trim() + " 23:59:59 ";
		}
		return this.findPage(page, hql);
	}

}
