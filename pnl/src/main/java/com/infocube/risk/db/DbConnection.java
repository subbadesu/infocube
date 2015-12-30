package com.infocube.risk.db;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.MappingManager;

public class DbConnection implements Connection {
	
	private MappingManager mappingManager;
	private String host;
	private String keyspace;

	public DbConnection(Session s) {
	}
	
	public DbConnection(String host, String keyspace) {
		this.host = host;
		this.keyspace = keyspace;
	}

	/* (non-Javadoc)
	 * @see com.infocube.risk.db.Connection#getObjectStore(java.lang.Class)
	 */
	public <T> ObjectStore<T> getObjectStore(Class<T> clazz) {
		if (mappingManager == null) {
			mappingManager = new MappingManager(getSession());
		}
		
		return new DbStore<T>(mappingManager.mapper(clazz));
	}
	
	private Session getSession() {
		Cluster cluster;
		Session session;
		cluster = Cluster.builder().addContactPoint(host).build();
		session = cluster.connect(keyspace);
		
		return session;
	}

}
