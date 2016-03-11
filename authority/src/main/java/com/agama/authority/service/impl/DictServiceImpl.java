package com.agama.authority.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agama.authority.dao.IDictDao;
import com.agama.authority.entity.Dict;
import com.agama.authority.service.IDictService;
import com.agama.common.dao.IRecycleDao;
import com.agama.common.dao.utils.EntityUtils;
import com.agama.common.entity.Recycle;
import com.agama.common.enumbean.RecycleEnum;
import com.agama.common.enumbean.StatusEnum;
import com.agama.common.service.impl.BaseServiceImpl;

/**
 * @Description:字典service实现
 * @Author:scuta
 * @Since :2015年8月27日 上午9:59:38
 */
@Service
@Transactional(readOnly = true)
public class DictServiceImpl extends BaseServiceImpl<Dict, Integer> implements IDictService {

	@Autowired
	private IDictDao dictDao;

	@Autowired
	private IRecycleDao recycleDao;
	
	@Autowired
	private EntityUtils entityUtils;
	
	/**
	 * 提供支持回收站的逻辑删除；
	 */
	@Transactional(readOnly = false)
	@Override
	public void delete(Integer id, Integer opUserId) {
		/**
		 * 删除操作的步骤：
		 * 一.组合要删除记录的记录信息，进行保存，即保存删除记录到Recycle
		 * 二.修改当前实体删除的逻辑状态
		 * 三.修改列表查询中筛选的条件，将逻辑删除的记录排除掉；
		 */
		Dict dict = dictDao.find(id);

		Recycle recycle = new Recycle();
		recycle.setContent(dict.toString());
		recycle.setIsRecovery(RecycleEnum.NO);
		recycle.setOpTime(new Date());
		recycle.setOpUserId(opUserId);
		recycle.setTableName(entityUtils.getTableNameByEntity(Dict.class.getName()));
		recycle.setTableRecordId(entityUtils.getIdNameByEntityName(Dict.class.getName()));
		recycleDao.save(recycle);

		dict.setStatus(StatusEnum.DELETED);//此处要确认是使用Id还是name，还是使用string本身
		dictDao.update(dict);
	}

}
