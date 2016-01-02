package com.infocube.risk.db;

import java.io.Closeable;

public interface Connection extends Closeable {

	<T> ObjectStore<T> getObjectStore(Class<T> clazz);

}