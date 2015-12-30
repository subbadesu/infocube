package com.infocube.risk.db;

public interface ObjectStore<T> {

	void save(T entity);
	void save(T... entities);
	T get(Object... primaryKeyValues);
	T get(Object primaryKeyValue);
	void delete(T entity);
	void delete(T... entities);
	
}
