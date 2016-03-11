package com.agama.common.dao.impl;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.internal.CriteriaImpl;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.transform.Transformers;
import org.springframework.util.Assert;

import com.agama.common.dao.IBaseDao;
import com.agama.common.dao.utils.Page;
import com.agama.common.dao.utils.PropertyFilter;
import com.agama.common.dao.utils.PropertyFilter.MatchType;
import com.agama.common.dao.utils.SimpleBaseDaoUtil;
import com.agama.tool.utils.Reflections;
import com.agama.tool.utils.string.StringUtils;


/**
 * 封装SpringSide扩展功能的Hibernat DAO泛型基类.
 * 
 * 扩展功能包括分页查询,按属性过滤条件列表查询.
 * 可在Service层直接使用,也可以扩展泛型DAO子类使用,见两个构造函数的注释.
 * 
 * @param <T> DAO操作的对象类型
 * @param <PK> 主键类型
 * 
 * @author calvin
 */
@SuppressWarnings("unchecked")
public class HibernateDaoImpl<T, PK extends Serializable> extends SimpleBaseDaoUtil<T, PK> implements IBaseDao<T, PK> {
	/**
	 * 用于Dao层子类的构造函数.
	 * 通过子类的泛型定义取得对象类型Class.
	 * eg.
	 * public class UserDao extends HibernateDao<User, Long>{
	 * }
	 */
	public HibernateDaoImpl() {
		super();
	}

	/**
	 * 用于省略Dao层, Service层直接使用通用HibernateDao的构造函数.
	 * 在构造函数中定义对象类型Class.
	 * eg.
	 * HibernateDao<User, Long> userDao = new HibernateDao<User, Long>(sessionFactory, User.class);
	 */
	public HibernateDaoImpl(final SessionFactory sessionFactory, final Class<T> entityClass) {
		super(sessionFactory, entityClass);
	}

	//-- 分页查询函数 --//

	/* (non-Javadoc)
	 * @see com.agama.authority.common.persistence.IBaseDao#getAll(com.agama.authority.common.persistence.Page)
	 */
	@Override
	public Page<T> getAll(final Page<T> page) {
		return findPage(page);
	}
	
	/* (non-Javadoc)
	 * @see com.agama.authority.common.persistence.IBaseDao#findPage(com.agama.authority.common.persistence.Page, org.hibernate.criterion.Criterion)
	 */
	@Override
	@SuppressWarnings({ "rawtypes" })
	public Page<T> findPage(final Page<T> page, final Criterion... criterions) {
		Assert.notNull(page, "page不能为空");
		Criteria c = createCriteria(criterions);
		if (page.isAutoCount()) {
			long totalCount = countCriteriaResult(c);
			page.setTotalCount(totalCount);
		}
		setPageParameterToCriteria(c, page);
		List result = c.list();
		page.setResult(result);
		return page;
	}
	
	/* (non-Javadoc)
	 * @see com.agama.authority.common.persistence.IBaseDao#findPage(com.agama.authority.common.persistence.Page, java.util.List)
	 */
	@Override
	public Page<T> findPage(final Page<T> page, final List<PropertyFilter> filters) {
		Criterion[] criterions = buildCriterionByPropertyFilter(filters);
		return findPage(page, criterions);
	}

	/**
	 * @Description：此处为执行sql语句查询，最后将查询的结果封装成一个冗余的bean对象；
	 * 注意冗余的意义：例如：当User对象中包含了一个orgId，现在要查询到orgName,
	 * 封装的User对象必须包含未持久化的orgName，并提供相应的查询结果所有字段的构造函数；
	 */
	public Page<T> findPage(final Page<T> page, final String hql, final List<PropertyFilter> filters){
		Assert.notNull(page, "page不能为空");
		String resultHql = prepareQuery(hql,filters);
		if (page.isAutoCount()) {
			long totalCount = countHqlResult(resultHql, filters);
			page.setTotalCount(totalCount);
		}
		Query q = getSession().createQuery(resultHql);
		setPageParameterToQuery(q, page);
		page.setResult(q.list());
		return page;
	}
	
	/**
	 * @Description：此处为执行sql语句查询，最后将查询的结果封装成一个冗余的bean对象；
	 * 注意冗余的意义：例如：当User对象中包含了一个orgId，现在要查询到orgName,
	 * 封装的User对象必须包含未持久化的orgName，并提供相应的查询结果所有字段的构造函数；
	 */
	public Page<T> findPageBySql(final Page<T> page, final String sql, final List<PropertyFilter> filters){
		Assert.notNull(page, "page不能为空");
		String queryString = prepareQuery(sql,filters);
		if (page.isAutoCount()) {
			long totalCount = countSqlResult(queryString, filters);
			page.setTotalCount(totalCount);
		}
		Query q = getSession().createSQLQuery(queryString);
		q = q.setResultTransformer(Transformers.aliasToBean(this.entityClass));
		setPageParameterToQuery(q, page);
		page.setResult(q.list());
		return page;
	}
	
	/**
	 * @Description:此处为执行sql语句查询，最后将查询的结果封装成一个冗余的bean对象；
	 * 注意冗余的意义：例如：当User对象中包含了一个orgId，现在要查询到orgName,
	 * 封装的User对象必须包含未持久化的orgName，并提供相应的查询结果所有字段的构造函数；
	 * @param page
	 * @param sql
	 * @param values
	 * @return
	 * @Since :2016年2月1日 下午3:12:20
	 */
	public Page<T> findPageBySQL(final Page<T> page, final String sql, final Object... values) {
		Assert.notNull(page, "page不能为空");
		if (page.isAutoCount()) {
			long totalCount = countSqlResult(sql, values);
			page.setTotalCount(totalCount);
		}
		Query q = createSQLQuery(sql, values);
		q = q.setResultTransformer(Transformers.aliasToBean(this.entityClass));
		setPageParameterToQuery(q, page);
		page.setResult(q.list());
		return page;
	}
	
	/* (non-Javadoc)
	 * @see com.agama.authority.common.persistence.IBaseDao#findPage(com.agama.authority.common.persistence.Page, java.lang.String, java.lang.Object)
	 */
	@Override
	@SuppressWarnings({ "rawtypes" })
	public Page<T> findPage(final Page<T> page, final String hql, final Object... values) {
		Assert.notNull(page, "page不能为空");
		Query q = createQuery(hql, values);
		if (page.isAutoCount()) {
			long totalCount = countHqlResult(hql, values);
			page.setTotalCount(totalCount);
		}
		setPageParameterToQuery(q, page);
		List result = q.list();
		page.setResult(result);
		return page;
	}

	/* (non-Javadoc)
	 * @see com.agama.authority.common.persistence.IBaseDao#findPage(com.agama.authority.common.persistence.Page, java.lang.String, java.util.Map)
	 */
	@Override
	@SuppressWarnings({ "rawtypes" })
	public Page<T> findPage(final Page<T> page, final String hql, final Map<String, ?> values) {
		Assert.notNull(page, "page不能为空");
		Query q = createQuery(hql, values);
		if (page.isAutoCount()) {
			long totalCount = countHqlResult(hql, values);
			page.setTotalCount(totalCount);
		}
		setPageParameterToQuery(q, page);
		List result = q.list();
		page.setResult(result);
		return page;
	}

	
	/**
	 * @Description:单独封装为处理sql处理：此处为对语句执行查询数量的操作；
	 * @param sql
	 * @param values
	 * @return
	 * @Since :2016年2月1日 下午3:11:08
	 */
	protected long countSqlResult(final String sql, final Object... values) {
		String countSql = prepareCountSql(sql);
		try {
			BigInteger count = findUniqueBySQL(countSql, values);
			return count.intValue();
		} catch (Exception e) {
			throw new RuntimeException("sql can't be auto count, Sql is:" + countSql, e);
		}
	}
	
	/**
	 * 执行count查询获得本次Sql查询所能获得的对象总数.
	 * 本函数只能自动处理简单的sql语句,复杂的sql查询请另行编写count语句查询.
	 */
	protected long countSqlResult(final String sql, final List<PropertyFilter> filters) {
		String countSql = StringUtils.substringBefore(sql, "order by");;
		try {
			Query query = getSession().createSQLQuery(countSql);
			Long count = (long) query.list().size();
			return count;
		} catch (Exception e) {
			throw new RuntimeException("sql can't be auto count, hql is:" + countSql, e);
		}
	}
	
	
	/**
	 * 执行count查询获得本次Hql查询所能获得的对象总数.
	 * 本函数只能自动处理简单的hql语句,复杂的hql查询请另行编写count语句查询.
	 */
	protected long countHqlResult(final String hql, final Object... values) {
		String countHql = prepareCountHql(hql);
		try {
			Long count = findUnique(countHql, values);
			return count;
		} catch (Exception e) {
			throw new RuntimeException("hql can't be auto count, hql is:" + countHql, e);
		}
	}

	/**
	 * 执行count查询获得本次Hql查询所能获得的对象总数.
	 * 本函数只能自动处理简单的hql语句,复杂的hql查询请另行编写count语句查询.
	 */
	protected long countHqlResult(final String hql, final List<PropertyFilter> filters) {
		String countHql = StringUtils.substringBefore(hql, "order by");;
		try {
			Query query = getSession().createQuery(countHql);
			Long count = (long) query.list().size();
			return count;
		} catch (Exception e) {
			throw new RuntimeException("hql can't be auto count, hql is:" + countHql, e);
		}
	}
	
	/**
	 * 执行count查询获得本次Hql查询所能获得的对象总数.
	 * 本函数只能自动处理简单的hql语句,复杂的hql查询请另行编写count语句查询.
	 */
	protected long countHqlResult(final String hql, final Map<String, ?> values) {
		String countHql = prepareCountHql(hql);
		try {
			Long count = findUnique(countHql, values);
			return count;
		} catch (Exception e) {
			throw new RuntimeException("hql can't be auto count, hql is:" + countHql, e);
		}
	}

	/**
	 * 执行count查询获得本次Criteria查询所能获得的对象总数.
	 */
	@SuppressWarnings({ "rawtypes" })
	protected long countCriteriaResult(final Criteria c) {
		CriteriaImpl impl = (CriteriaImpl) c;

		// 先把Projection、ResultTransformer、OrderBy取出来,清空三者后再执行Count操作
		Projection projection = impl.getProjection();
		ResultTransformer transformer = impl.getResultTransformer();

		List<CriteriaImpl.OrderEntry> orderEntries = null;
		try {
			orderEntries = (List) Reflections.getFieldValue(impl, "orderEntries");
			Reflections.setFieldValue(impl, "orderEntries", new ArrayList());
		} catch (Exception e) {
			logger.error("不可能抛出的异常:{}", e.getMessage());
		}

		// 执行Count查询
		Long totalCountObject = (Long) c.setProjection(Projections.rowCount()).uniqueResult();
		long totalCount = (totalCountObject != null) ? totalCountObject : 0;

		// 将之前的Projection,ResultTransformer和OrderBy条件重新设回去
		c.setProjection(projection);

		if (projection == null) {
			c.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		}
		if (transformer != null) {
			c.setResultTransformer(transformer);
		}
		try {
			Reflections.setFieldValue(impl, "orderEntries", orderEntries);
		} catch (Exception e) {
			logger.error("不可能抛出的异常:{}", e.getMessage());
		}

		return totalCount;
	}

	
	/* (non-Javadoc)
	 * @see com.agama.authority.common.persistence.IBaseDao#findBy(java.lang.String, java.lang.Object, com.agama.authority.common.persistence.PropertyFilter.MatchType)
	 */
	@Override
	public List<T> findBy(final String propertyName, final Object value, final MatchType matchType) {
		Criterion criterion = buildCriterion(propertyName, value, matchType);
		return find(criterion);
	}

	/* (non-Javadoc)
	 * @see com.agama.authority.common.persistence.IBaseDao#find(java.util.List)
	 */
	@Override
	public List<T> find(List<PropertyFilter> filters) {
		Criterion[] criterions = buildCriterionByPropertyFilter(filters);
		return find(criterions);
	}

	/**
	 * 保存新增或修改的对象.
	 * @param entity
	 */
	public void save(final T entity) {
		// getSession().saveOrUpdate(entity);
		getSession().save(entity);
	}

	/**
	 * 删除对象.
	 * @param entity 对象必须是session中的对象或含id属性的transient对象.
	 */
	public void delete(final T entity) {
		getSession().delete(entity);
	}

	/**
	 * 按id删除对象.
	 * @param id
	 */
	public void delete(final PK id) {
		delete(find(id));
	}

	/**
	 * 按id获取对象.
	 * @param id
	 * @return 对象
	 */
	public T find(final PK id) {
		return (T) getSession().load(entityClass, id);
	}

	/**
	 * 按id列表获取对象列表.
	 * @param idList
	 * @return 对象集合
	 */
	public List<T> find(final Collection<PK> idList) {
		return find(Restrictions.in(getIdName(), idList));
	}

	/**
	 * 获取全部对象.
	 * @return 对象集合.
	 */
	public List<T> findAll() {
		return find();
	}
	
	/**
	 * 获取全部对象.
	 * @param isCache 是否缓存
	 * @return 对象集合.
	 */
	public List<T> findAll(Boolean isCache) {
		return find(isCache);
	}

	/**
	 * 按Criteria查询对象列表.
	 * @param criterions 数量可变的Criterion.
	 * @return 结果集合
	 */
	public List<T> find(final Criterion... criterions) {
		return createCriteria(criterions).list();
	}

	public List<T> find(Boolean isCache,final Criterion... criterions) {
		return createCriteria(isCache,criterions).list();
	}
	
	/**
	 * 获取全部对象, 支持按属性行序.
	 * @param orderByProperty 排序属性name
	 * @param isAsc 是否升序排序
	 * @return 查询结果集合
	 */
	public List<T> findAll(String orderByProperty, boolean isAsc) {
		Criteria c = createCriteria();
		if (isAsc) {
			c.addOrder(Order.asc(orderByProperty));
		} else {
			c.addOrder(Order.desc(orderByProperty));
		}
		return c.list();
	}

	/**
	 * 按属性查找对象列表, 匹配方式为相等
	 * @param propertyName 属性name
	 * @param value 属性值
	 * @return 结果集合
	 */
	public List<T> findBy(final String propertyName, final Object value) {
		Criterion criterion = Restrictions.eq(propertyName, value);
		return find(criterion);
	}

	/**
	 * 按HQL查询对象列表.
	 * @param hql
	 * @param values 数量可变的参数,按顺序绑定.
	 * @return 结果集合
	 */
	public <X> List<X> find(final String hql, final Object... values) {
		return createQuery(hql, values).list();
	}

	/**
	 * 按HQL查询对象列表.
	 * @param hql
	 * @param values 命名参数,按名称绑定.
	 * @return 对象集合
	 */
	public <X> List<X> find(final String hql, final Map<String, ?> values) {
		return createQuery(hql, values).list();
	}

	/**
	 * 按Criteria查询唯一对象.
	 * @param criterions 数量可变的Criterion.
	 * @return 对象
	 */
	public T findUnique(final Criterion... criterions) {
		return (T) createCriteria(criterions).uniqueResult();
	}
	
	/**
	 * 按属性查找唯一对象, 匹配方式为相等
	 * @param propertyName 属性name
	 * @param value 属性值
	 * @return 结果对象
	 */
	public T findUniqueBy(final String propertyName, final Object value) {
		Criterion criterion = Restrictions.eq(propertyName, value);
		return (T) createCriteria(criterion).uniqueResult();
	}
	
	/**
	 * @Description:按SQL查询唯一对象.
	 * @param sql
	 * @param values
	 * @return
	 * @Since :2016年2月1日 下午3:46:13
	 */
	public <X> X findUniqueBySQL(final String sql, Object... values) {
		return (X) createSQLQuery(sql, values).uniqueResult();
	}
	
	
	/**
	 * 按HQL查询唯一对象.
	 * @param hql
	 * @param values 数量可变的参数,按顺序绑定.
	 * @return 对象
	 */
	public <X> X findUnique(final String hql, final Object... values) {
		return (X) createQuery(hql, values).uniqueResult();
	}

	
	/**
	 * 按HQL查询唯一对象.
	 * @param hql
	 * @param values 数量可变的参数,按顺序绑定.
	 * @return 对象
	 */
	public <X> X findUnique(final String hql, final List<PropertyFilter> filters) {
		return (X) createQuery(hql, filters).uniqueResult();
	}
	
	/**
	 * 按HQL查询唯一对象.
	 * @param hql
	 * @param values 命名参数,按名称绑定.
	 * @return 对象
	 */
	public <X> X findUnique(final String hql, final Map<String, ?> values) {
		return (X) createQuery(hql, values).uniqueResult();
	}

	/**
	 * 执行HQL进行批量修改/删除操作.
	 * @param hql
	 * @param values 数量可变的参数,按顺序绑定.
	 * @return 更新记录数.
	 */
	public int batchExecute(final String hql, final Object... values) {
		return createQuery(hql, values).executeUpdate();
	}

	/**
	 * 执行SQL进行批量修改/删除操作.
	 * @param sql
	 * @param values 数量可变的参数,按顺序绑定.
	 * @return 更新记录数.
	 */
	@Override
	public int batchExecuteSql(String sql, Object... values) {
		return createSQLQuery(sql, values).executeUpdate();
	}
	
	/**
	 * 执行HQL进行批量修改/删除操作.
	 * @param hql
	 * @param values 命名参数,按名称绑定.
	 * @return 更新记录数.
	 */
	public int batchExecute(final String hql, final Map<String, ?> values) {
		return createQuery(hql, values).executeUpdate();
	}

	/**
	 * Flush当前Session.
	 */
	public void flush() {
		getSession().flush();
	}

	/**
	 * 取得对象的主键名.
	 * @return 对象的主键名
	 */
	public String getIdName() {
		ClassMetadata meta = sessionFactory.getClassMetadata(entityClass);
		return meta.getIdentifierPropertyName();
	}

	/**
	 * 判断对象的属性值在数据库内是否唯一.
	 * 在修改对象的情景下,如果属性新修改的值(value)等于属性原来的值(orgValue)则不作比较.
	 * @param propertyName 属性name
	 * @param newValue 新值
	 * @param oldValue 旧值
	 * @return 是否唯一
	 */
	public boolean isPropertyUnique(final String propertyName, final Object newValue, final Object oldValue) {
		if (newValue == null || newValue.equals(oldValue)) {
			return true;
		}
		Object object = findUniqueBy(propertyName, newValue);
		return (object == null);
	}
	
	
	/**
	 * 修改对象.
	 * @param entity
	 */
	@Override
	public void update(T entity) {
		getSession().update(entity);
	}

	@Override
	public void merge(T entity) {
		getSession().merge(entity);

	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*全文检索
	*//**
	 * 获取全文Session
	 *//*
	public FullTextSession getFullTextSession(){
		return Search.getFullTextSession(getSession());
	}
	
	*//**
	 * 建立索引
	 *//*
	public void createIndex(){
		try {
			getFullTextSession().createIndexer(entityClass).startAndWait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	*//**
	 * 全文检索
	 * @param page 分页对象
	 * @param query 关键字查询对象
	 * @param queryFilter 查询过滤对象
	 * @param sort 排序对象
	 * @return 分页对象
	 *//*
	@SuppressWarnings("unchecked")
	public Page<T> search(Page<T> page, BooleanQuery query, BooleanQuery queryFilter, Sort sort){
		
		// 按关键字查询
		FullTextQuery fullTextQuery = getFullTextSession().createFullTextQuery(query, entityClass);
        
		// 过滤无效的内容
		if (queryFilter!=null){
			fullTextQuery.setFilter(new CachingWrapperFilter(new QueryWrapperFilter(queryFilter)));
		}
        
        // 设置排序
		if (sort!=null){
			fullTextQuery.setSort(sort);
		}

		// 定义分页
		page.setTotalCount(fullTextQuery.getResultSize());
		fullTextQuery.setFirstResult(page.getFirst() - 1);
		fullTextQuery.setMaxResults(page.getPageSize()); 

		// 先从持久化上下文中查找对象，如果没有再从二级缓存中查找
        fullTextQuery.initializeObjectsWith(ObjectLookupMethod.SECOND_LEVEL_CACHE, DatabaseRetrievalMethod.QUERY); 
        
		// 返回结果
		page.setResult(fullTextQuery.list());
        
		return page;
	}
	
	*//**
	 * 获取全文查询对象
	 *//*
	public BooleanQuery getFullTextQuery(BooleanClause... booleanClauses){
		BooleanQuery booleanQuery = new BooleanQuery();
		for (BooleanClause booleanClause : booleanClauses){
			booleanQuery.add(booleanClause);
		}
		return booleanQuery;
	}

	*//**
	 * 获取全文查询对象
	 * @param q 查询关键字
	 * @param fields 查询字段
	 * @return 全文查询对象
	 *//*
	public BooleanQuery getFullTextQuery(String q, String... fields){
		Analyzer analyzer = new IKAnalyzer();
		BooleanQuery query = new BooleanQuery();
		try {
			if (StringUtils.isNotBlank(q)){
				for (String field : fields){
					QueryParser parser = new QueryParser(Version.LUCENE_36, field, analyzer);   
					query.add(parser.parse(q), Occur.SHOULD);
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return query;
	}
	
	*//**
	 * 设置关键字高亮
	 * @param query 查询对象
	 * @param list 设置高亮的内容列表
	 * @param subLength 截取长度
	 * @param fields 字段名
	 * @return 结果集合
	 *//*
	public List<T> keywordsHighlight(BooleanQuery query, List<T> list, int subLength, String... fields){
		Analyzer analyzer = new IKAnalyzer();
		Formatter formatter = new SimpleHTMLFormatter("<span class=\"highlight\">", "</span>");   
		Highlighter highlighter = new Highlighter(formatter, new QueryScorer(query)); 
		highlighter.setTextFragmenter(new SimpleFragmenter(subLength)); 
		for(T entity : list){ 
			try {
				for (String field : fields){
					String text = StringUtils.replaceHtml((String)Reflections.invokeGetter(entity, field));
					String description = highlighter.getBestFragment(analyzer,field, text);
					if(description!=null){
						Reflections.invokeSetter(entity, fields[0], description);
						break;
					}
					Reflections.invokeSetter(entity, fields[0], StringUtils.abbr(text, subLength*2));
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InvalidTokenOffsetsException e) {
				e.printStackTrace();
			} 
		}
		return list;
	}*/
}
