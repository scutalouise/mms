package com.agama.itam.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agama.common.dao.IRecycleDao;
import com.agama.common.dao.utils.EntityUtils;
import com.agama.common.entity.Recycle;
import com.agama.common.enumbean.RecycleEnum;
import com.agama.common.enumbean.StatusEnum;
import com.agama.common.service.impl.BaseServiceImpl;
import com.agama.itam.dao.IKnowledgeDetailsDao;
import com.agama.itam.domain.KnowledgeDetails;
import com.agama.itam.service.IKnowledgeDetailsService;

/**
 * @Description:
 * @Author:杨远高
 * @Since :2016年3月7日 下午4:43:58
 */
@Transactional(readOnly = true)
@Service("knowledgeDetailsService")
public class KnowledgeDetailsServiceImpl extends BaseServiceImpl<KnowledgeDetails, Serializable> implements IKnowledgeDetailsService {
	@Autowired
	private IKnowledgeDetailsDao knowledgeDetailsDao;
	
	@Autowired
	private IRecycleDao recycleDao;
	
	@Autowired
	private EntityUtils  entityUtils;

	/**
	 * 提供一个逻辑删除，之后再结合回收站做处理；
	 */
	@Override
	@Transactional(readOnly = false)
	public void delete(Integer id, Integer userId) {
		KnowledgeDetails details = knowledgeDetailsDao.find(id);
		details.setStatus(StatusEnum.DELETED);
		knowledgeDetailsDao.update(details);
		List<Integer> ids = new ArrayList<Integer>();//保存到recycle中的ids；以便还原；
		ids.add(id);
		Recycle recycle = new Recycle();
		recycle.setContent(ids.toString());
		recycle.setIsRecovery(RecycleEnum.NO);
		recycle.setOpTime(new Date());
		recycle.setOpUserId(userId);
		recycle.setTableName(entityUtils.getTableNameByEntity(KnowledgeDetails.class.getName()));
		recycle.setTableRecordId(entityUtils.getIdNameByEntityName(KnowledgeDetails.class.getName()));
		recycleDao.save(recycle);
	}

}
