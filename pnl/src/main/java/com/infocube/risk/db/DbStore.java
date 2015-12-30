package com.infocube.risk.db;

import com.datastax.driver.mapping.Mapper;

public class DbStore<T> implements ObjectStore<T> {
	
	private Mapper<T> mapper;

	public DbStore(Mapper<T> mapper) {
		this.mapper = mapper;
	}

	public void save(T entity) {
		mapper.save(entity);
	}

	public void save(T... entities) {
		for (T entity : entities) {
			mapper.save(entity);
		}
	}

	public T get(Object... primaryKeyValues) {
		return mapper.get(primaryKeyValues);
	}

	public T get(Object primaryKeyValue) {
		return mapper.get(primaryKeyValue);
	}

	public void delete(T entity) {
		mapper.delete(entity);
	}

	public void delete(T... entities) {
		for (T entity : entities) {
			mapper.delete(entity);
		}
	}

}
