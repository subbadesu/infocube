package com.infocube.risk.db;

public interface Connection {

	<T> ObjectStore<T> getObjectStore(Class<T> clazz);

}