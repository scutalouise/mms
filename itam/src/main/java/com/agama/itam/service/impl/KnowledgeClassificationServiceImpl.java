package com.agama.itam.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agama.common.dao.IRecycleDao;
import com.agama.common.dao.utils.EntityUtils;
import com.agama.common.domain.TreeBean;
import com.agama.common.entity.Recycle;
import com.agama.common.enumbean.EnabledStateEnum;
import com.agama.common.enumbean.RecycleEnum;
import com.agama.common.enumbean.StatusEnum;
import com.agama.common.service.impl.BaseServiceImpl;
import com.agama.itam.dao.IKnowledgeClassificationDao;
import com.agama.itam.domain.KnowledgeClassification;
import com.agama.itam.service.IKnowledgeClassificationService;

/**
 * @Description:
 * @Author:杨远高
 * @Since :2016年3月7日 下午4:43:58
 */
@Transactional(readOnly = true)
@Service("knowledgeClassificationService")
public class KnowledgeClassificationServiceImpl extends BaseServiceImpl<KnowledgeClassification, Integer> implements IKnowledgeClassificationService {
	@Autowired
	private IKnowledgeClassificationDao knowledgeClassificationDao;
	
	@Autowired
	private IRecycleDao recycleDao;
	
	@Autowired
	private EntityUtils  entityUtils;

	/**
	 * 提供支持回收站的逻辑删除；
	 */
	@Override
	@Transactional(readOnly = false)
	public void delete(Integer id, Integer userId) {
		KnowledgeClassification classification = knowledgeClassificationDao.find(id);
		classification.setStatus(StatusEnum.DELETED);
		knowledgeClassificationDao.update(classification);
		List<KnowledgeClassification> list = recursiveKnowledgeClassificationsByPid(id);
		list.add(0,knowledgeClassificationDao.find(id));//需要包含当前选中的项
		List<Integer> ids = new ArrayList<Integer>();//保存到recycle中的ids；以便还原；
		if(list.size() > 0){
			for(KnowledgeClassification knowledgeClassification : list){
				knowledgeClassification.setStatus(StatusEnum.DELETED);// 此处要确认是使用Id还是name，还是使用string本身
				knowledgeClassification.setOperateTime(new Date());
				knowledgeClassification.setOperator(userId);
				knowledgeClassificationDao.update(knowledgeClassification);
				ids.add(knowledgeClassification.getId());
			}
			Recycle recycle = new Recycle();
			recycle.setContent(ids.toString());
			recycle.setIsRecovery(RecycleEnum.NO);
			recycle.setOpTime(new Date());
			recycle.setOpUserId(userId);
			recycle.setTableName(entityUtils.getTableNameByEntity(KnowledgeClassification.class.getName()));
			recycle.setTableRecordId(entityUtils.getIdNameByEntityName(KnowledgeClassification.class.getName()));
			recycleDao.save(recycle);
		}
	}
	
	
	/**
	 * 提供根据pid返回树结构的接口
	 */
	@Override
	public List<TreeBean> getTreeByPid(Integer pid) {
		List<KnowledgeClassification> classifications = knowledgeClassificationDao.findListByPid(pid);
		List<TreeBean> treeBeans = new ArrayList<TreeBean>();
		for (KnowledgeClassification classification : classifications) {
			TreeBean treeBean = new TreeBean();
			treeBean.setId(classification.getId());
			treeBean.setText(classification.getName());
			Criterion  pidExpression = Restrictions.eq("pid", classification.getId());
			Criterion  enableExpression = Restrictions.eq("enable", EnabledStateEnum.ENABLED);
			Criterion  statusExpression = Restrictions.eq("status", StatusEnum.NORMAL);
			if (knowledgeClassificationDao.find(pidExpression,enableExpression,statusExpression).size() > 0) {
				treeBean.setState("closed");
			} else {
				treeBean.setState("open");
			}
			treeBeans.add(treeBean);
		}
		return treeBeans;
	}

	/**
	 * @Description:递归调用，循环查找到当前pid下的所有子集；
	 * @param pid
	 * @return
	 * @Since :2016年3月7日 下午6:11:57
	 */
	private List<KnowledgeClassification> recursiveKnowledgeClassificationsByPid(Integer pid) {
		List<KnowledgeClassification> list = new ArrayList<KnowledgeClassification>();
		List<KnowledgeClassification> classificationList = knowledgeClassificationDao.findListByPid(pid);
		for (KnowledgeClassification classification : classificationList) {
			list.add(classification);
			list.addAll(recursiveKnowledgeClassificationsByPid(classification.getId()));
		}
		return list;
	}
	
}
