package com.agama.common.service.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agama.common.dao.IRecycleDao;
import com.agama.common.dao.utils.Page;
import com.agama.common.dao.utils.PropertyFilter;
import com.agama.common.entity.Recycle;
import com.agama.common.enumbean.RecycleEnum;
import com.agama.common.enumbean.StatusEnum;
import com.agama.common.service.IRecycleService;
import com.agama.tool.utils.string.StringUtils;

/**
 * @Description:回收站实现类
 * @Author:杨远高
 * @Since :2016年1月29日 上午10:43:39
 */
@Transactional(readOnly = true)
@Service
public class RecycleServiceImpl extends BaseServiceImpl<Recycle, Serializable> implements IRecycleService {

	@Autowired
	private IRecycleDao recycleDao;
	
	@Override
	public Page<Recycle> findPage(Page<Recycle> page, List<PropertyFilter> propertyFilters) {
		StringBuilder sql = new StringBuilder("select r.id as id,opUser.name as opUserName,recoveryUser.name as recoveryUserName, ");
										sql.append("r.content as content, ");
										sql.append("r.op_time as opTime,r.recovery_time as recoveryTime,r.table_name as tableName, ");
										sql.append("r.table_record_id as tableRecordId,r.is_recovery as recoveryString ");
									sql.append("from recycle r ");
									sql.append("left join user opUser on r.op_user_id=opUser.id ");
									sql.append("left join user recoveryUser on r.recovery_user_id=recoveryUser.id ");
		return recycleDao.findPageBySql(page, sql.toString(), propertyFilters);
	}

	@Override
	@Transactional(readOnly = false)
	public int execRecycle(Integer id,Integer opUserId) {
		
		Recycle recycle = recycleDao.find(id);
		StringBuilder sb = new StringBuilder(" update " + recycle.getTableName());
		sb.append(" set status='" + StatusEnum.NORMAL+"' ");
		sb.append(" where " + recycle.getTableRecordId() + " in ");
		String idContent = recycle.getContent();
		idContent = StringUtils.replaceChars(idContent, "[", "");
		idContent = StringUtils.replaceChars(idContent, "]", "");
		idContent = "(" + idContent + ")";
		sb.append(idContent);
		
		
		//更新恢复时间、状态
		recycle.setRecoveryString(idContent);
		recycle.setRecoveryTime(new Date());
		recycle.setRecoveryUserId(opUserId);
		recycle.setIsRecovery(RecycleEnum.YES);
		
		//返回更新的表记录数；
		return recycleDao.batchExecuteSql(sb.toString(), null);
	}
	
	
	
}
