package com.agama.das.service.impl;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import com.agama.aws.dao.MongoDao;
import com.agama.das.model.entity.InspectedResult;
import com.agama.das.service.InspectedResultService;

/**@Description:实现类，实现巡检数据操作的各种方法
 * @Author:佘朝军
 * @Since :2015年11月23日 下午2:11:57
 */
@Service
public class InspectedResultServiceImpl implements InspectedResultService{
	
	@Autowired
	private MongoDao mongoDao;

	@Override
	public void insert(InspectedResult inspectedResult) {
		mongoDao.save(inspectedResult);
	}

	@Override
	public List<InspectedResult> getAll() {
		return mongoDao.queryListByCriteriaAndSort(new Criteria(), null, InspectedResult.class);
	}

	@Override
	public void removeOneByObjectId(ObjectId objectId) {
		mongoDao.removeByObjectId(objectId, InspectedResult.class);
	}

	@Override
	public void removeByList(List<InspectedResult> resultList) {
		for (InspectedResult inspectedResult : resultList) {
			removeOneByObjectId(inspectedResult.getId());
		}
	}

}
