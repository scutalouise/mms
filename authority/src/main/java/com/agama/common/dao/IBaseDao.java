package com.agama.common.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Criterion;

import com.agama.common.dao.utils.Page;
import com.agama.common.dao.utils.PropertyFilter;
import com.agama.common.dao.utils.PropertyFilter.MatchType;

public interface IBaseDao<T, PK extends Serializable> {

	/**
	 * 保存新增对象.
	 * 
	 * @param entity
	 */
	public abstract void save(T entity);

	/**
	 * 合并对象
	 * 
	 * @param entity
	 */
	public abstract void merge(T entity);

	/**
	 * 修改对象.
	 * @param entity
	 */
	public abstract void update(T entity);

	/**
	 * 删除对象.
	 * @param entity 对象必须是session中的对象或含id属性的transient对象.
	 */
	public abstract void delete(T entity);

	/**
	 * 按id删除对象.
	 * @param id
	 */
	public abstract void delete(PK id);

	/**
	 * 按id获取对象.
	 * @param id
	 * @return 对象
	 */
	public abstract T find(PK id);
	
	/**
	 * 按Criteria查询唯一对象.
	 * @param criterions 数量可变的Criterion.
	 * @return 对象
	 */
	public abstract T findUnique(Criterion... criterions);

	/**
	 * 按HQL查询唯一对象.
	 * @param hql
	 * @param values 数量可变的参数,按顺序绑定.
	 * @return 对象
	 */
	public abstract <X> X findUnique(String hql, Object... values);

	/**
	 * 按HQL查询唯一对象.
	 * @param hql
	 * @param values 命名参数,按名称绑定.
	 * @return 对象
	 */
	public abstract <X> X findUnique(String hql, Map<String, ?> values);
	
	/**
	 * 按属性查找唯一对象, 匹配方式为相等
	 * @param propertyName 属性name
	 * @param value 属性值
	 * @return 结果对象
	 */
	public abstract T findUniqueBy(String propertyName, Object value);
	
	/**
	 * 获取全部对象.
	 * @return 对象集合.
	 */
	public abstract List<T> findAll();
	
	/**
	 * 按属性过滤条件列表查找对象列表.
	 */
	public abstract List<T> find(List<PropertyFilter> filters);
	
	/**
	 * 按Criteria查询对象列表.
	 * @param criterions 数量可变的Criterion.
	 * @return 结果集合
	 */
	public abstract List<T> find(Criterion... criterions);

	public abstract List<T> find(Boolean isCache, Criterion... criterions);

	/**
	 * 按id列表获取对象列表.
	 * @param idList
	 * @return 对象集合
	 */
	public abstract List<T> find(Collection<PK> idList);

	/**
	 * 获取全部对象.
	 * @param isCache 是否缓存
	 * @return 对象集合.
	 */
	public abstract List<T> findAll(Boolean isCache);

	/**
	 * 获取全部对象, 支持按属性行序.
	 * @param orderByProperty 排序属性name
	 * @param isAsc 是否升序排序
	 * @return 查询结果集合
	 */
	public abstract List<T> findAll(String orderByProperty, boolean isAsc);

	/**
	 * 按属性查找对象列表, 匹配方式为相等
	 * @param propertyName 属性name
	 * @param value 属性值
	 * @return 结果集合
	 */
	public abstract List<T> findBy(String propertyName, Object value);
	

	/**
	 * 按属性查找对象列表,支持多种匹配方式.
	 * 
	 * @param matchType 匹配方式,目前支持的取值见PropertyFilter的MatcheType enum.
	 */
	public abstract List<T> findBy(String propertyName, Object value, MatchType matchType);

	/**
	 * 按HQL查询对象列表.
	 * @param hql
	 * @param values 数量可变的参数,按顺序绑定.
	 * @return 结果集合
	 */
	public abstract <X> List<X> find(String hql, Object... values);

	/**
	 * 按HQL查询对象列表.
	 * @param hql
	 * @param values 命名参数,按名称绑定.
	 * @return 对象集合
	 */
	public abstract <X> List<X> find(String hql, Map<String, ?> values);



	/**
	 * 执行HQL进行批量修改/删除操作.
	 * @param hql
	 * @param values 数量可变的参数,按顺序绑定.
	 * @return 更新记录数.
	 */
	public abstract int batchExecute(String hql, Object... values);
	
	
	/**
	 * 执行SQL进行批量修改/删除操作.
	 * @param sql
	 * @param values 数量可变的参数,按顺序绑定.
	 * @return 更新记录数.
	 */
	public abstract int batchExecuteSql(String sql, Object... values);

	/**
	 * 执行HQL进行批量修改/删除操作.
	 * @param hql
	 * @param values 命名参数,按名称绑定.
	 * @return 更新记录数.
	 */
	public abstract int batchExecute(String hql, Map<String, ?> values);

	/**
	 * 分页获取全部对象.
	 */
	public abstract Page<T> getAll(Page<T> page);

	/**
	 * 按HQL分页查询.
	 * 
	 * @param page 分页参数. 注意不支持其中的orderBy参数.
	 * @param hql hql语句.
	 * @param values 数量可变的查询参数,按顺序绑定.
	 * 
	 * @return 分页查询结果, 附带结果列表及所有查询输入参数.
	 */
	public abstract Page<T> findPage(Page<T> page, String hql, Object... values);
	
	
	/**
	 * 按SQL分页查询.
	 * 
	 * @param page 分页参数. 
	 * @param sql sql语句.
	 * @param values 数量可变的查询参数,按顺序绑定.
	 * 
	 * @return 分页查询结果, 附带结果列表及所有查询输入参数.
	 */
	public abstract Page<T> findPageBySQL(Page<T> page, String sql, Object... values);

	/**
	 * 按HQL分页查询.
	 * 
	 * @param page 分页参数. 注意不支持其中的orderBy参数.
	 * @param hql hql语句.
	 * @param values 命名参数,按名称绑定.
	 * 
	 * @return 分页查询结果, 附带结果列表及所有查询输入参数.
	 */
	public abstract Page<T> findPage(Page<T> page, String hql, Map<String, ?> values);

	/**
	 * 按Criteria分页查询.
	 * 
	 * @param page 分页参数.
	 * @param criterions 数量可变的Criterion.
	 * 
	 * @return 分页查询结果.附带结果列表及所有查询输入参数.
	 */
	public abstract Page<T> findPage(Page<T> page, Criterion... criterions);

	/**
	 * 按属性过滤条件列表分页查找对象.
	 */
	public abstract Page<T> findPage(Page<T> page, List<PropertyFilter> filters);
	
	/**
	 * 按属性过滤条件列表分页查找对象.
	 */
	public abstract Page<T> findPage(Page<T> page, String hql, List<PropertyFilter> filters);
	
	
	/**
	 * 按属性过滤条件列表分页查找对象.
	 */
	public abstract Page<T> findPageBySql(Page<T> page, String sql, List<PropertyFilter> filters);
	
	/**
	 * 判断对象的属性值在数据库内是否唯一.
	 * 在修改对象的情景下,如果属性新修改的值(value)等于属性原来的值(orgValue)则不作比较.
	 * @param propertyName 属性name
	 * @param newValue 新值
	 * @param oldValue 旧值
	 * @return 是否唯一
	 */
	public abstract boolean isPropertyUnique(String propertyName, Object newValue, Object oldValue);
	
	/**
	 * 初始化对象.
	 * 使用load()方法得到的仅是对象Proxy, 在传到View层前需要进行初始化.
	 * 如果传入entity, 则只初始化entity的直接属性,但不会初始化延迟加载的关联集合和属性.
	 * 如需初始化关联属性,需执行:
	 * Hibernate.initialize(user.getRoles())，初始化User的直接属性和关联集合.
	 * Hibernate.initialize(user.getDescription())，初始化User的直接属性和延迟加载的Description属性.
	 */
	public abstract void initProxyObject(Object proxy);

	/**
	 * Flush当前Session.
	 */
	public abstract void flush();


	/**
	 * 取得对象的主键名.
	 * @return 对象的主键名
	 */
	public abstract String getIdName();

}