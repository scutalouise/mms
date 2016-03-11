package com.agama.aws.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.util.Assert;

import com.agama.tool.utils.ConvertUtils;
import com.agama.tool.utils.date.DateUtils;
import com.agama.tool.utils.string.StringUtils;

/**
 * @Description:用于封装高级条件查询的类
 * @Author:佘朝军
 * @Since :2016年2月23日 上午10:24:15
 */
@SuppressWarnings("rawtypes")
public class MongoQueryFilter {

	private Map<String, Object> gtMap = new HashMap<String, Object>();
	private Map<String, Object> ltMap = new HashMap<String, Object>();
	private Map<String, Object> eqMap = new HashMap<String, Object>();
	private Map<String, Object> gteMap = new HashMap<String, Object>();
	private Map<String, Object> lteMap = new HashMap<String, Object>();
	private Map<String, String> regexMap = new HashMap<String, String>();
	private Map<String, Collection> inMap = new HashMap<String, Collection>();
	private Map<String, Object> neMap = new HashMap<String, Object>();

	/** 属性比较类型. */
	public enum MongoMatchType {
		EQ, LIKE, LT, GT, LE, GE, IN, NE;
	}

	/** 属性数据类型. */
	public enum MongoPropertyType {
		S(String.class), I(Integer.class), L(Long.class), N(Double.class), D(Date.class), B(Boolean.class);

		private Class<?> clazz;

		private MongoPropertyType(Class<?> clazz) {
			this.clazz = clazz;
		}

		public Class<?> getValue() {
			return clazz;
		}
	}

	/**
	 * 从HttpRequest中创建Criteria列表, 默认Filter属性名前缀为filter.
	 * 
	 * @see #buildFromHttpRequest(HttpServletRequest, String)
	 */
	public static Criteria buildFromHttpRequest(final HttpServletRequest request) {
		return buildFromHttpRequest(request, "filter");
	}

	/**
	 * 从HttpRequest中创建Criteria列表 MongoQueryFilter命名规则为Filter属性前缀_比较类型_属性类型_属性名.
	 * 
	 * eg. filter_EQ_S_name
	 */
	public static Criteria buildFromHttpRequest(final HttpServletRequest request, final String filterPrefix) {
		// 从request中获取含属性前缀名的参数,构造去除前缀名后的参数Map.
		Map<String, Object> filterParamMap = getParametersStartingWith(request, filterPrefix + "_");

		Criteria criteria = new Criteria();
		MongoQueryFilter mqf = new MongoQueryFilter();
		List<Criteria> andCriteriaList = new ArrayList<Criteria>();
		// 分析参数Map,构造PropertyFilter列表
		for (Map.Entry<String, Object> entry : filterParamMap.entrySet()) {
			String filterName = entry.getKey();
			String value = (String) entry.getValue();
			// 如果value值为空,则忽略此filter.
			if (!StringUtils.isBlank(value)) {
				String[] namePart = filterName.split("_OR_");
				if (namePart.length < 2) { // 处理非或条件的查询参数
					String[] nameArray = filterName.split("_");
					Object matchValue = mqf.getMatchValueByClass(nameArray[1], value);
					mqf.putParameterToMap(nameArray[0], nameArray[2], matchValue);
				} else { // 处理或条件的查询参数
					List<Criteria> singleCriteriaList = new ArrayList<Criteria>();
					for (int i = 0; i < namePart.length; i++) {
						singleCriteriaList.add(createCriteriaByParam(namePart[i], value));
					}
					andCriteriaList.add(new Criteria().orOperator(singleCriteriaList.toArray(new Criteria[singleCriteriaList.size()])));
				}
			}
		}
		criteria = MongoQueryHelper.createCriteria(mqf.getGtMap(), mqf.getLtMap(), mqf.getEqMap(), mqf.gteMap, mqf.getLteMap(), mqf.getRegexMap(),
				mqf.getInMap(), mqf.getNeMap());
		if (!andCriteriaList.isEmpty()) {
			andCriteriaList.add(criteria);
			criteria = new Criteria().andOperator(andCriteriaList.toArray(new Criteria[andCriteriaList.size()]));
		}
		return criteria;
	}

	public static Criteria createCriteriaByParam(String filterName, String paramValue) {
		String[] nameArray = filterName.split("_");
		MongoQueryFilter mqf = new MongoQueryFilter();
		Object matchValue = mqf.getMatchValueByClass(nameArray[1], paramValue);
		mqf.putParameterToMap(nameArray[0], nameArray[2], matchValue);
		return MongoQueryHelper.createCriteria(mqf.getGtMap(), mqf.getLtMap(), mqf.getEqMap(), mqf.gteMap, mqf.getLteMap(), mqf.getRegexMap(), mqf.getInMap(),
				mqf.getNeMap());
	}

	/**
	 * @Description:获取待比较参数的值
	 * @param classString
	 *            Class名的字符串
	 * @param value
	 *            待过滤参数值的字符串
	 * @return Object
	 * @Since :2016年2月24日 下午3:52:50
	 */
	private Object getMatchValueByClass(String classString, String value) {
		Class cls = null;
		try {
			cls = Enum.valueOf(MongoPropertyType.class, classString).getValue();
		} catch (Exception e) {
			throw new IllegalArgumentException("filter名称没有按规则编写,无法得到属性值类型.", e);
		}
		return ConvertUtils.convertStringToObject(value, cls);
	}

	/**
	 * @Description:将比较参数和待比较值放入散列集合中
	 * @param matchTypeString
	 *            比较参数类型的字符串
	 * @param paramName
	 *            比较参数名
	 * @param matchValue
	 *            待过滤参数值
	 * @Since :2016年2月24日 下午4:26:41
	 */
	private void putParameterToMap(String matchTypeString, String paramName, Object matchValue) {
		MongoMatchType matchType = null;
		try {
			matchType = Enum.valueOf(MongoMatchType.class, matchTypeString);
		} catch (Exception e) {
			throw new IllegalArgumentException("filter名称" + paramName + "没有按规则编写,无法得到属性比较类型.", e);
		}
		switch (matchType) {
		case EQ:
			eqMap.put(paramName, matchValue);
			break;
		case LIKE:
			regexMap.put(paramName, String.valueOf(matchValue));
			break;
		case GT:
			gtMap.put(paramName, matchValue);
			break;
		case LT:
			if (matchValue.getClass().equals(Date.class)) {
				Date resultValue = DateUtils.calculateDate((Date) matchValue, 1);
				ltMap.put(paramName, resultValue);	
			} else {
				ltMap.put(paramName, matchValue);				
			}
			break;
		case GE:
			gteMap.put(paramName, matchValue);
			break;
		case LE:
			if (matchValue.getClass().equals(Date.class)) {
				Date resultValue = DateUtils.calculateDate((Date) matchValue, 1);
				lteMap.put(paramName, resultValue);	
			} else {
				lteMap.put(paramName, matchValue);				
			}
			break;
		case IN:
			String[] strArr = (String.valueOf(matchValue)).split(",");
			Collection collection = Arrays.asList(strArr);
			inMap.put(paramName, collection);
			break;
		case NE:
			neMap.put(paramName, matchValue);
		default:
			break;
		}

	}

	public static Map<String, Object> getParametersStartingWith(ServletRequest request, String prefix) {
		Assert.notNull(request, "Request must not be null");
		Enumeration paramNames = request.getParameterNames();
		Map<String, Object> params = new TreeMap<String, Object>();
		if (prefix == null) {
			prefix = "";
		}
		while (paramNames != null && paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			if ("".equals(prefix) || paramName.startsWith(prefix)) {
				String unprefixed = paramName.substring(prefix.length());
				String[] values = request.getParameterValues(paramName);
				if (values == null || values.length == 0) {
					// Do nothing, no values found at all.
				} else if (values.length > 1) {
					params.put(unprefixed, values);
				} else {
					params.put(unprefixed, values[0]);
				}
			}
		}
		return params;
	}

	public Map<String, Object> getGtMap() {
		return gtMap;
	}

	public Map<String, Object> getLtMap() {
		return ltMap;
	}

	public Map<String, Object> getEqMap() {
		return eqMap;
	}

	public Map<String, Object> getGteMap() {
		return gteMap;
	}

	public Map<String, Object> getLteMap() {
		return lteMap;
	}

	public Map<String, String> getRegexMap() {
		return regexMap;
	}

	public Map<String, Collection> getInMap() {
		return inMap;
	}

	public Map<String, Object> getNeMap() {
		return neMap;
	}

}
