package com.agama.aws.dao;

import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;

/**
 * @Description:支持mongoDB数据库操作的接口类
 * @Author:佘朝军
 * @Since :2016年1月7日 下午5:43:42
 */
public interface MongoDao {

	/**
	 * @Description:保存实体对象
	 * @param bean 实体对象
	 * @Since :2016年1月14日 下午4:16:25
	 */
	public <T> void save(T bean);

	/**
	 * @Description:根据objectId删除一个对象
	 * @param objectId 对象id
	 * @param clz 对象类的class
	 * @Since :2016年1月14日 下午4:16:47
	 */
	public <T> void removeByObjectId(ObjectId objectId, Class<? extends T> clz);

	/**
	 * @Description: 根据自定义条件删除一个对象
	 * @param criteria 条件类
	 * @param clz 对象类的class
	 * @Since :2016年1月14日 下午4:17:37
	 */
	public <T> void removeByCriteria(Criteria criteria, Class<? extends T> clz);

	/**
	 * @Description: 根据ObjectId修改一个对象的属性（需要先从数据库执行query方法查找到该对象）
	 * @param objectId 对象id
	 * @param t 实体对象bean
	 * @param clz 对象类的class
	 * @Since :2016年1月14日 下午4:19:03
	 */
	public <T> void updateBeanByObjectId(ObjectId objectId, T t, Class<T> clz);
	
	/**
	 * @Description: 根据ObjectId查找一个对象
	 * @param objectId 对象id
	 * @param clz 对象类的class
	 * @return 实体对象
	 * @Since :2016年1月14日 下午4:27:12
	 */
	public <T> T queryOneByObjectId(ObjectId objectId, Class<? extends T> clz);
	
	/**
	 * @Description:根据等值条件查找实体对象
	 * @param eqMap 等值条件构成的map
	 * @param clz 实体对象类的class
	 * @return 实体对象
	 * @Since :2016年1月14日 下午4:28:50
	 */
	public <T> T queryOneByEqualMap(Map<String, Object> eqMap, Class<? extends T> clz);
	
	/**
	 * @Description: 根据条件查找一个对象
	 * @param criteria 条件类
	 * @param clz 对象类的class
	 * @return 实体对象
	 * @Since :2016年1月14日 下午4:28:08
	 */
	public <T> T queryOneByCriteria(Criteria criteria, Class<? extends T> clz);

	/**
	 * @Description:根据条件和排序查找对象列表
	 * @param criteria 条件类
	 * @param sort 排序类
	 * @param clz 实体对象类的class
	 * @return 实体对象列表
	 * @Since :2016年1月14日 下午4:30:08
	 */
	public <T> List<T> queryListByCriteriaAndSort(Criteria criteria, Sort sort, Class<T> clz);

	/**
	 * @Description:根据条件和排序，分页查找对象列表
	 * @param criteria 条件类
	 * @param sort 排序类
	 * @param clz 实体对象类的class
	 * @param page 页码
	 * @param pageSize 每页显示最大记录数
	 * @return 实体对象列表
	 * @Since :2016年1月14日 下午4:31:04
	 */
	public <T> List<T> queryListByCriteriaAndSortForPage(Criteria criteria, Sort sort, Class<T> clz, int page, int pageSize);

	/**
	 * @Description:根据条件查找对象列表的总记录数
	 * @param criteria 条件类
	 * @param clz 实体对象类的class
	 * @return 总记录数
	 * @Since :2016年1月14日 下午4:32:19
	 */
	public <T> long queryCountByCriteria(Criteria criteria, Class<T> clz);

}
