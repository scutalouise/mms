package com.agama.common.dao.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.agama.common.dao.utils.PropertyFilter.MatchType;
import com.agama.tool.utils.Reflections;
import com.agama.tool.utils.string.StringUtils;



/**
 * 封装Hibernate原生API的DAO泛型基类.<br>
 * 可在Service层直接使用, 也可以扩展泛型DAO子类使用, 见两个构造函数的注释.
 * 取消了HibernateTemplate, 直接使用Hibernate原生API.
 * 
 * @param <T> DAO操作的对象类型
 * @param <PK> 主键类型
 * 
 * @author calvin
 */
public class SimpleBaseDaoUtil<T, PK extends Serializable> {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	protected SessionFactory sessionFactory;

	protected Class<T> entityClass;

	/**
	 * 用于Dao层子类使用的构造函数.
	 * 通过子类的泛型定义取得对象类型Class.
	 * eg.
	 * public class UserDao extends SimpleHibernateDao<User, Long>
	 */
	public SimpleBaseDaoUtil() {
		this.entityClass = Reflections.getClassGenricType(getClass());
	}

	/**
	 * 用于用于省略Dao层, 在Service层直接使用通用SimpleHibernateDao的构造函数.
	 * 在构造函数中定义对象类型Class.
	 * eg.
	 * SimpleHibernateDao<User, Long> userDao = new SimpleHibernateDao<User, Long>(sessionFactory, User.class);
	 */
	public SimpleBaseDaoUtil(final SessionFactory sessionFactory, final Class<T> entityClass) {
		this.sessionFactory = sessionFactory;
		this.entityClass = entityClass;
	}

	/**
	 * 取得当前Session.
	 * @return Session
	 */
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	
	/**
	 * 设置分页参数到Query对象,辅助函数.
	 */
	protected Query setPageParameterToQuery(final Query q, final Page<T> page) {
		Assert.isTrue(page.getPageSize() > 0, "Page Size must larger than zero");
		//hibernate的firstResult的序号从0开始
		q.setFirstResult(page.getFirst() - 1);
		q.setMaxResults(page.getPageSize());
		return q;
	}

	/**
	 * 设置分页参数到Criteria对象,辅助函数.
	 */
	protected Criteria setPageParameterToCriteria(final Criteria c, final Page<T> page) {

		Assert.isTrue(page.getPageSize() > 0, "Page Size must larger than zero");

		//hibernate的firstResult的序号从0开始
		c.setFirstResult(page.getFirst() - 1);
		c.setMaxResults(page.getPageSize());

		if (page.isOrderBySetted()) {
			String[] orderByArray = StringUtils.split(page.getOrderBy(), ',');
			String[] orderArray = StringUtils.split(page.getOrder(), ',');

			Assert.isTrue(orderByArray.length == orderArray.length, "分页多重排序参数中,排序字段与排序方向的个数不相等");

			for (int i = 0; i < orderByArray.length; i++) {
				if (Page.ASC.equals(orderArray[i])) {
					c.addOrder(Order.asc(orderByArray[i]));
				} else {
					c.addOrder(Order.desc(orderByArray[i]));
				}
			}
		}
		return c;
	}

	/**
	 * @Description:单独封装为处理sql查询：此处查询对象数量的sql语句封装；
	 * @param orgHql
	 * @return
	 * @Since :2016年3月9日 上午9:43:55
	 */
	protected String prepareCountHql(String orgHql) {
		String fromHql = orgHql;
		//select子句与order by子句会影响count查询,进行简单的排除.
		fromHql = "from " + StringUtils.substringAfter(fromHql, "from");
		fromHql = StringUtils.substringBefore(fromHql, "order by");
		String countHql = "select count(*) " + fromHql;
		return countHql;
	}
	
	/**
	 * @Description:单独封装为处理sql查询：此处查询对象数量的sql语句封装；
	 * @param orgSql
	 * @return
	 * @Since :2016年2月1日 下午3:10:39
	 */
	protected String prepareCountSql(String orgSql) {
		String fromSql = orgSql;
		//select子句与order by子句会影响count查询,进行简单的排除.
		fromSql = "from " + StringUtils.substringAfter(fromSql, "from");
		fromSql = StringUtils.substringBefore(fromSql, "order by");
		String countSql = "select count(*) " + fromSql;
		return countSql;
	}
	
	/**
	 * @Description:根据propertyFilter封装成hql；
	 * @param queryString
	 * @param propertyFilters
	 * @return
	 * @Since :2016年3月4日 下午2:59:34
	 */
	/*protected String prepareQuery(final String queryString, final List<PropertyFilter> propertyFilters) {
		//实例SQL："selcet fjsfjsl,* from  a, b where sfsdf and sdfsdf and dfsf order by dxdsf " 
		String hqlPrefix = StringUtils.substringBefore(queryString, "where");
		String hqlSuffix = StringUtils.substringAfter(queryString, "where");
		String returnHql = "";
		int size = propertyFilters.size();
		String andOperator = size >0?" and " : "";
		if(queryString.contains("where")){//包含where语句时,主要判断是否包含order by 
			hqlPrefix += " where ";
			if(hqlSuffix.contains("order by")){//包含order by 语句；在order前面加条件
				String tempOrderByHqlPrefix = StringUtils.substringBefore(hqlSuffix, "order by");
				String tempOrderByHqlSufffix = StringUtils.substringAfter(hqlSuffix, "order by");
				for(PropertyFilter filter : propertyFilters){
					tempOrderByHqlPrefix += andOperator + buildOperator(filter);//处理中间的连接符；
				}
				hqlSuffix = tempOrderByHqlPrefix + " order by " + tempOrderByHqlSufffix;
			}else{//不包含order by 语句；直接在where后面加条件；
				for(PropertyFilter filter : propertyFilters){
					hqlSuffix += andOperator + buildOperator(filter);//处理中间的连接符；
				}
			}
			returnHql = hqlPrefix + hqlSuffix;
		}else{//没有包含where语句时的处理；
			if(hqlSuffix.contains("order by")){//包含order by 语句；根据是否有条件存在分别加上条件
				String tempOrderByHqlPrefix = StringUtils.substringBefore(hqlSuffix, "order by");
				String tempOrderByHqlSufffix = StringUtils.substringAfter(hqlSuffix, "order by");
				for(int i=0;i<size;i++){
					if(i==0){
						tempOrderByHqlPrefix += " where " +  buildOperator(propertyFilters.get(i));
					}else{
						tempOrderByHqlPrefix += andOperator + buildOperator(propertyFilters.get(i));
					}
				}
				returnHql = tempOrderByHqlPrefix + " order by " + tempOrderByHqlSufffix;
			}else{//不包含order by 语句,直接根据是否有条件存在分别加上条件
				returnHql = queryString;
				for(int i=0;i<size;i++){
					if(i==0){
						returnHql += " where " +  buildOperator(propertyFilters.get(i));
					}else{
						returnHql += andOperator + buildOperator(propertyFilters.get(i));
					}
				}
			}
		}
		return returnHql;
	}*/
	
	protected String prepareQuery(final String queryString, final List<PropertyFilter> propertyFilters) {
		//实例SQL："selcet fjsfjsl,* from  a, b where sfsdf and sdfsdf and dfsf order by dxdsf " 
		int size = propertyFilters.size();
		String returnHql = "";
		//判断是否存在查询条件。1.无则不做任何处理，直接返回原hql；2.有，则需进一步判断处理
		if (size > 0) {
			String hqlPrefix = StringUtils.substringBefore(queryString, "where");
			String hqlSuffix = StringUtils.substringAfter(queryString, "where");
			String andOperator = " and ";
			hqlPrefix += " where ";
			//判断原hql是否存在where条件。1.有，则直接在hqlPrefix后追加PropertyFilter的条件
			//						  2.无，则需要进一步对hql其它关键词进行判断
			if (queryString.contains("where") ) { //eg: select * from User where id = 5 order by name desc;
				for(PropertyFilter filter : propertyFilters){
					hqlPrefix += buildOperator(filter) + andOperator;//处理中间的连接符；
				}
				returnHql = hqlPrefix + hqlSuffix;
			} else { // 此处可能存在order by, group by等多种关键语句需要进行判断
				// 在原hql中没有where的情况下，判断是否存在order by语句。
				//		1.无。则直接在hqlPrefix后拼接PropertyFilter条件参数
				//		2.有。则对原hql进行进一步拆分处理
				if (!queryString.contains("order by")) { // eg: select * from User;
					for(PropertyFilter filter : propertyFilters){
						hqlPrefix += buildOperator(filter) + andOperator;//处理中间的连接符；
					}
					returnHql = hqlPrefix + " 1 = 1 ";
				} else { // eg: select * from User order by name desc;
					String orderPrefix = StringUtils.substringBefore(queryString, "order by");
					String orderSuffix = StringUtils.substringAfter(queryString, "order by");
					orderPrefix += " where ";
					for(PropertyFilter filter : propertyFilters){
						orderPrefix += buildOperator(filter) + andOperator;//处理中间的连接符；
					}
					returnHql = orderPrefix + " 1 = 1 order by " + orderSuffix;
				}
			}
		} else {
			returnHql = queryString;
		}
		return returnHql;
	}

	/**
	 * 按属性条件PropertyFilter参数,拼接创建sql操作符与字符串,辅助函数.
	 */
	private String buildOperator(final PropertyFilter propertyFilter) {
		Assert.notNull(propertyFilter);
		String fragment = "";
		switch (propertyFilter.getMatchType()) {//根据MatchType构造操作符与
		case EQ:
			fragment = propertyFilter.getPropertyName() + "='" + propertyFilter.getMatchValue() +"' ";
			break;
		case LIKE:
			fragment = propertyFilter.getPropertyName() + " like '%" + propertyFilter.getMatchValue() +"%' ";
			break;
		case LE:
			fragment = propertyFilter.getPropertyName() + " <='" + propertyFilter.getMatchValue() +"' ";
			break;
		case LT:
			fragment = propertyFilter.getPropertyName() + " <'" + propertyFilter.getMatchValue() +"' ";
			break;
		case GE:
			fragment = propertyFilter.getPropertyName() + " >='" + propertyFilter.getMatchValue() +"' ";
			break;
		case GT:
			fragment = propertyFilter.getPropertyName() + " >'" + propertyFilter.getMatchValue() +"' ";
		}
		return fragment;
	}
	
	
	/**
	 * 按属性条件参数创建Criterion,辅助函数.
	 */
	protected Criterion buildCriterion(final String propertyName, final Object propertyValue, final MatchType matchType) {
		Assert.hasText(propertyName, "propertyName不能为空");
		Criterion criterion = null;
		//根据MatchType构造criterion
		switch (matchType) {
		case EQ:
			criterion = Restrictions.eq(propertyName, propertyValue);
			break;
		case LIKE:
			criterion = Restrictions.like(propertyName, (String) propertyValue, MatchMode.ANYWHERE);
			break;

		case LE:
			criterion = Restrictions.le(propertyName, propertyValue);
			break;
		case LT:
			criterion = Restrictions.lt(propertyName, propertyValue);
			break;
		case GE:
			criterion = Restrictions.ge(propertyName, propertyValue);
			break;
		case GT:
			criterion = Restrictions.gt(propertyName, propertyValue);
		}
		return criterion;
	}

	/**
	 * 按属性条件列表创建Criterion数组,辅助函数.
	 */
	protected Criterion[] buildCriterionByPropertyFilter(final List<PropertyFilter> filters) {
		List<Criterion> criterionList = new ArrayList<Criterion>();
		for (PropertyFilter filter : filters) {
			if (!filter.hasMultiProperties()) { //只有一个属性需要比较的情况.
				Criterion criterion = buildCriterion(filter.getPropertyName(), filter.getMatchValue(), filter
						.getMatchType());
				criterionList.add(criterion);
			} else {//包含多个属性需要比较的情况,进行or处理.
				Disjunction disjunction = Restrictions.disjunction();
				for (String param : filter.getPropertyNames()) {
					Criterion criterion = buildCriterion(param, filter.getMatchValue(), filter.getMatchType());
					disjunction.add(criterion);
				}
				criterionList.add(disjunction);
			}
		}
		return criterionList.toArray(new Criterion[criterionList.size()]);
	}
	
	/**
	 * 根据查询HQL与参数列表创建Query对象.
	 * @param queryString 
	 * @param values 数量可变的参数,按顺序绑定.
	 * @return Query
	 */
	public Query createQuery(final String queryString, final Object... values) {
		Assert.hasText(queryString, "queryString不能为空");
		Query query = getSession().createQuery(queryString);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(String.valueOf(i), values[i]);
			}
		}
		return query;
	}
	
	/**
	 * 根据查询HQL与参数列表创建Query对象.
	 * 与find()函数可进行更加灵活的操作.
	 * @param queryString
	 * @param values 命名参数,按名称绑定.
	 * @return Query
	 */
	public Query createQuery(final String queryString, final Map<String, ?> values) {
		Assert.hasText(queryString, "queryString不能为空");
		Query query = getSession().createQuery(queryString);
		if (values != null) {
			query.setProperties(values);
		}
		return query;
	}
	
	/**
	 * 根据查询SQL与参数列表创建Query对象.
	 * @param queryString
	 * @param values 数量可变的参数,按顺序绑定.
	 * @return SQLQuery
	 */
	public SQLQuery createSQLQuery(final String queryString, final Object... values){
		SQLQuery sqlQuery = getSession().createSQLQuery(queryString);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				sqlQuery.setParameter(String.valueOf(i), values[i]);
			}
		}
		return sqlQuery;
	}
	
	/**
	 * 根据查询SQL与参数列表创建Query对象.
	 * @param queryString
	 * @param values 命名参数,按名称绑定.
	 * @return SQLQuery
	 */
	public SQLQuery createSQLQuery(final String queryString, final Map<String, ?> values) {
		SQLQuery sqlQuery = getSession().createSQLQuery(queryString);
		if (values != null) {
			sqlQuery.setProperties(values);
		}
		return sqlQuery;
	}

	
	
	/**
	 * 根据Criterion条件创建Criteria.
	 * 与find()函数可进行更加灵活的操作.
	 * @param criterions 数量可变的Criterion.
	 * @return Criteria
	 */
	public Criteria createCriteria(final Criterion... criterions) {
		Criteria criteria = getSession().createCriteria(entityClass);
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		return criteria;
	}
	
	public Criteria createCriteria(Boolean isCache,final Criterion... criterions) {
		Criteria criteria = getSession().createCriteria(entityClass);
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		criteria.setCacheable(isCache);
		return criteria;
	}

	/**
	 * 初始化对象.
	 * 使用load()方法得到的仅是对象Proxy, 在传到View层前需要进行初始化.
	 * 如果传入entity, 则只初始化entity的直接属性,但不会初始化延迟加载的关联集合和属性.
	 * 如需初始化关联属性,需执行:
	 * Hibernate.initialize(user.getRoles())，初始化User的直接属性和关联集合.
	 * Hibernate.initialize(user.getDescription())，初始化User的直接属性和延迟加载的Description属性.
	 */
	public void initProxyObject(Object proxy) {
		Hibernate.initialize(proxy);
	}
	
	
	/**
	 * 为Query添加distinct transformer.
	 * 预加载关联对象的HQL会引起主对象重复, 需要进行distinct处理.
	 * @param query
	 * @return Query
	 */
	public Query distinct(Query query) {
		query.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return query;
	}

	/**
	 * 为Criteria添加distinct transformer.
	 * 预加载关联对象的HQL会引起主对象重复, 需要进行distinct处理.
	 * @param criteria
	 * @return Criteria
	 */
	public Criteria distinct(Criteria criteria) {
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return criteria;
	}
}