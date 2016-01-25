package com.infocube.risk.db;

import java.util.List;

public interface ObjectStore<T> {

	void save(T entity);
	void save(T... entities);
	T get(Object... primaryKeyValues);
	T get(Object primaryKeyValue);

    List<T> getAll(Object... partitionKeyValues);
    List<T> getAll();
    List<T> getByQuery(String query);
	void delete(T entity);
	void delete(T... entities);
	
}
