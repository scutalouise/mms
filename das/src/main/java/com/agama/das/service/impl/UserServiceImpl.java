package com.agama.das.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import com.agama.aws.dao.MongoDao;
import com.agama.aws.helper.MongoQueryHelper;
import com.agama.das.model.entity.User;
import com.agama.das.service.UserService;

/**
 * @Description:实现类，用于实现用户操作的方法，包括增、删、改、查
 * @Author:佘朝军
 * @Since :2015年11月11日 下午4:30:44
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private MongoDao mongoDao;

	@Override
	public void insert(User user) {
		mongoDao.save(user);

	}

	@Override
	public void removeByObjectId(ObjectId objectId) {
		mongoDao.removeByObjectId(objectId, User.class);

	}

	@Override
	public void update(User user) {
		mongoDao.updateBeanByObjectId(user.getId(), user, User.class);
	}

	@Override
	public User findOneByObjectId(ObjectId objectId) {
		return mongoDao.queryOneByObjectId(objectId, User.class);
	}

	@Override
	public User findOneByUserNameAndPassword(String userName, String password) {
		Map<String, Object> eqMap = new HashMap<String, Object>();
		eqMap.put("userName", userName);
		eqMap.put("password", password);
		Criteria criteria = MongoQueryHelper.createCriteria(null, null, eqMap, null, null, null, null, null);
		return mongoDao.queryOneByCriteria(criteria, User.class);
	}

	@Override
	public User findOneByUserName(String userName) {
		Map<String, Object> eqMap = new HashMap<String, Object>();
		eqMap.put("userName", userName);
		Criteria criteria = MongoQueryHelper.createCriteria(null, null, eqMap, null, null, null, null, null);
		return mongoDao.queryOneByCriteria(criteria, User.class);
	}

}
