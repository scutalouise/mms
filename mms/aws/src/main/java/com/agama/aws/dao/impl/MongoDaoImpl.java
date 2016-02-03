package com.agama.aws.dao.impl;

import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.agama.aws.dao.MongoDao;
import com.agama.aws.helper.MongoQueryHelper;
import com.agama.aws.utils.MongoBeanUtils;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**@Description:MongoDB数据访问接口的实现类
 * @Author:佘朝军
 * @Since :2016年1月7日 下午5:44:02
 */
@Component
public class MongoDaoImpl implements MongoDao{

	@Autowired
	private MongoTemplate mongoTemplate;

	public <T> void save(T bean) {
		mongoTemplate.insert(bean);
	}

	public <T> void removeByObjectId(ObjectId objectId, Class<? extends T> clz) {
		Query query = new Query(Criteria.where(MongoBeanUtils._ID).is(objectId));
		mongoTemplate.findAndRemove(query, clz);
	}
	
	public <T> void removeByCriteria(Criteria criteria, Class<? extends T> clz) {
		Query query = new Query(criteria);
		mongoTemplate.remove(query, clz);
	}

	public <T> void updateBeanByObjectId(ObjectId objectId, T t, Class<T> clz) {
		try {
			Map<String, Object> updateMap = MongoBeanUtils.buildUpdateMapFromBean(t, clz);
			Query query = new Query(Criteria.where(MongoBeanUtils._ID).is(objectId));
			DBObject updateDoc = new BasicDBObject();
			updateDoc.putAll(updateMap);
			mongoTemplate.findAndModify(query, Update.fromDBObject(updateDoc), clz);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public <T> T queryOneByObjectId(ObjectId objectId, Class<? extends T> clz) {
		return mongoTemplate.findById(objectId, clz);
	}

	public <T> T queryOneByEqualMap(Map<String, Object> eqMap, Class<? extends T> clz) {
		Criteria criteria = MongoQueryHelper.createCriteria(null, null, eqMap, null, null, null, null, null);
		return queryOneByCriteria(criteria, clz);
	}
	
	public <T> T queryOneByCriteria(Criteria criteria, Class<? extends T> clz) {
		Query query = new Query(criteria);
		return mongoTemplate.findOne(query, clz);
	}

	public <T> List<T> queryListByCriteriaAndSort(Criteria criteria, Sort sort, Class<T> clz) {
		Query query = new Query(criteria);
		if (sort != null) {
			query.with(sort);
		}
		return mongoTemplate.find(query, clz);
	}

	public <T> List<T> queryListByCriteriaAndSortForPage(Criteria criteria, Sort sort, Class<T> clz, int page, int pageSize) {
		Query query = new Query(criteria);
		if (sort != null) {
			query.with(sort);
		}
		query.skip((page - 1) * pageSize);
		query.limit(pageSize);
		return mongoTemplate.find(query, clz);
	}

	public <T> long queryCountByCriteria(Criteria criteria, Class<T> clz) {
		Query query = new Query(criteria);
		return mongoTemplate.count(query, clz);
	}

}
