package com.agama.common.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.agama.common.dao.IBaseDao;
import com.agama.common.dao.utils.Page;
import com.agama.common.dao.utils.PropertyFilter;
import com.agama.common.service.IBaseService;

public class BaseServiceImpl<T, PK extends Serializable> implements IBaseService<T, PK> {

	@Autowired
	protected IBaseDao<T, PK> baseDao;

	@Override
	@Transactional(readOnly = true)
	public T get(PK id) {
		return baseDao.find(id);
	}

	@Override
	@Transactional(readOnly = false)
	public void save(T entity) {
		baseDao.save(entity);
	}

	@Override
	@Transactional(readOnly = false)
	public void update(T entity) {
		baseDao.update(entity);
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(T entity) {
		baseDao.delete(entity);
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(PK id) {
		baseDao.delete(id);
	}

	@Override
	public List<T> getAll() {
		return baseDao.findAll();
	}

	@Override
	public List<T> getAll(Boolean isCache) {
		return baseDao.findAll(isCache);
	}

	@Override
	public List<T> search(List<PropertyFilter> filters) {
		return baseDao.find(filters);
	}

	@Override
	public Page<T> search(Page<T> page, List<PropertyFilter> filters) {
		return baseDao.findPage(page, filters);
	}

	@Override
	public void merge(T entity) {
		baseDao.merge(entity);

	}

}
