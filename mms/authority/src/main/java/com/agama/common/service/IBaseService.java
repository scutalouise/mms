package com.agama.common.service;

import java.io.Serializable;
import java.util.List;

import com.agama.common.dao.utils.Page;
import com.agama.common.dao.utils.PropertyFilter;

public interface IBaseService<T, PK extends Serializable> {
	
	public T get(final PK id) ;

	public void save(final T entity) ;

	public void update(final T entity);
	
	public void merge(final T entity);

	public void delete(final T entity);
	
	public void delete(final PK id);
	
	public List<T> getAll();
	
	public List<T> getAll(Boolean isCache);
	
	public List<T> search(final List<PropertyFilter> filters);
	
	public Page<T> search(final Page<T> page, final List<PropertyFilter> filters) ;
	
}
