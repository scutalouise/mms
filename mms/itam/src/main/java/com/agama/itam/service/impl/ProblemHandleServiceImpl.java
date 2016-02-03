package com.agama.itam.service.impl;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import com.agama.aws.dao.MongoDao;
import com.agama.itam.mongo.domain.ProblemHandle;
import com.agama.itam.service.IProblemHandleService;

/**@Description:问题处理过程记录实现类
 * @Author:佘朝军
 * @Since :2016年1月27日 下午4:05:54
 */
@Service
public class ProblemHandleServiceImpl implements IProblemHandleService{
	
	@Autowired
	private MongoDao mongoDao;

	@Override
	public ProblemHandle get(ObjectId objectId) throws Exception {
		return mongoDao.queryOneByObjectId(objectId, ProblemHandle.class);
	}

	@Override
	public List<ProblemHandle> findListByProblemId(Long problemId) throws Exception {
		Sort sort = new Sort(Direction.DESC,"handleTime");
		Criteria criteria = Criteria.where("problemId").is(problemId);
		return mongoDao.queryListByCriteriaAndSort(criteria, sort, ProblemHandle.class);
	}

	@Override
	public ProblemHandle getLatestByProblemId(Long problemId) throws Exception {
		List<ProblemHandle> list = findListByProblemId(problemId);
		if (!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public void saveProblemHandle(ProblemHandle problemHandle) throws Exception {
		mongoDao.save(problemHandle);
		
	}

}
