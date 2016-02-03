package com.agama.das.helper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.query.Criteria;

/**@Description:用于封装MongoDB查询的相关对象
 * @Author:佘朝军
 * @Since :2015年11月11日 上午9:17:04
 */
public class MongoQueryHelper {
	
	/**
	 * @Description:根据不同的条件生产查询语句
	 * @param gtMap 大于
	 * @param ltMap 小于
	 * @param eqMap 等于
	 * @param gteMap 大于或等于
	 * @param lteMap 小于或等于
	 * @param regexMap 匹配正则表达式
	 * @param inMap 包含指定元素
	 * @param neMap 不包含指定元素
	 * @return Criteria 查询语句
	 * @Since :2015年11月11日 上午10:25:59
	 */
	@SuppressWarnings("rawtypes")
	public static Criteria createCriteria(Map<String, Object> gtMap, Map<String, Object> ltMap, Map<String, Object> eqMap, Map<String, Object> gteMap,
			Map<String, Object> lteMap, Map<String, String> regexMap, Map<String, Collection> inMap, Map<String, Object> neMap) {
		Criteria criteria = new Criteria();
		List<Criteria> listC = new ArrayList<Criteria>();
		Set<String> _set = null;
		if (gtMap != null && gtMap.size() > 0) {
			_set = gtMap.keySet();
			for (String _s : _set) {
				listC.add(Criteria.where(_s).gt(gtMap.get(_s)));
			}
		}
		if (ltMap != null && ltMap.size() > 0) {
			_set = ltMap.keySet();
			for (String _s : _set) {
				listC.add(Criteria.where(_s).lt(ltMap.get(_s)));
			}
		}
		if (eqMap != null && eqMap.size() > 0) {
			_set = eqMap.keySet();
			for (String _s : _set) {
				listC.add(Criteria.where(_s).is(eqMap.get(_s)));
			}
		}
		if (gteMap != null && gteMap.size() > 0) {
			_set = gteMap.keySet();
			for (String _s : _set) {
				listC.add(Criteria.where(_s).gte(gteMap.get(_s)));
			}
		}
		if (lteMap != null && lteMap.size() > 0) {
			_set = lteMap.keySet();
			for (String _s : _set) {
				listC.add(Criteria.where(_s).lte(lteMap.get(_s)));
			}
		}

		if (regexMap != null && regexMap.size() > 0) {
			_set = regexMap.keySet();
			for (String _s : _set) {
				listC.add(Criteria.where(_s).regex(regexMap.get(_s)));
			}
		}

		if (inMap != null && inMap.size() > 0) {
			_set = inMap.keySet();
			for (String _s : _set) {
				listC.add(Criteria.where(_s).in(inMap.get(_s)));
			}
		}
		if (neMap != null && neMap.size() > 0) {
			_set = neMap.keySet();
			for (String _s : _set) {
				listC.add(Criteria.where(_s).ne(neMap.get(_s)));
			}
		}
		if (listC.size() > 0) {
			Criteria[] cs = new Criteria[listC.size()];
			criteria.andOperator(listC.toArray(cs));
		}
		return criteria;
	}
	
	/**
	 * @Description:根据不同的排序条件生产排序对象
	 * @param orderMap 封装待排序的参数以及排序的类型
	 * @return Sort 排序对象
	 * @Since :2015年11月25日 下午4:38:44
	 */
	public static Sort createOrders(Map<String,Direction> orderMap){
		List<Order> orderList = new ArrayList<Order>();
		if(orderMap != null && orderMap.size() > 0){
			for (String key : orderMap.keySet()) {
				Order order = new Order(orderMap.get(key),key);
				orderList.add(order);
			}
		}
		Sort sort = null;
		if (!orderList.isEmpty()) {
			sort = new Sort(orderList);
		}
		return sort;
	}

}
