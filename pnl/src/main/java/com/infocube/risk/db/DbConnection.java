package com.infocube.risk.db;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.MappingManager;

public class DbConnection implements Connection {
	
    private static String DEFAULT_HOST_NAME = "localhost";
    private static String DEFAULT_PORT_NUM = "9042";
    private static String DEFAULT_SCHEMA_NAME = "infocube";

	private MappingManager mappingManager;
    private String[] hosts;
	private String keyspace;
    private int port;

    private Session session;
    private Cluster cluster;

    public DbConnection(Session session) {
        this.session = session;
	}

    public DbConnection(ConnectionProperties connectionProperties) {
        String hostNames = connectionProperties.getProperty(ConnectionProperties.DBHOST_NAMES, DEFAULT_HOST_NAME);
        if (hostNames == null || hostNames.isEmpty()) {
            throw new IllegalArgumentException(
                    "Connection properties must have a non-empty value for: " + ConnectionProperties.DBHOST_NAMES);
        }
        hosts = hostNames.split(",");

        String sPort = connectionProperties.getProperty(ConnectionProperties.DBPORT_NUM, DEFAULT_PORT_NUM);
        if (sPort == null || sPort.isEmpty()) {
            throw new IllegalArgumentException(
                    "Connection properties must have a value for: " + ConnectionProperties.DBPORT_NUM);
        }
        port = Integer.parseInt(sPort);

        keyspace = connectionProperties.getProperty(ConnectionProperties.DBSCHEMA_NAME, DEFAULT_SCHEMA_NAME);
        if (keyspace == null || keyspace.isEmpty()) {
            throw new IllegalArgumentException(
                    "Connection properties must have a value for: " + ConnectionProperties.DBSCHEMA_NAME);
        }
    }
	
	public DbConnection(String host, String keyspace) {
        this(new String[] { host }, keyspace, Integer.parseInt(DEFAULT_PORT_NUM));
	}

    public DbConnection(String[] hosts, String keyspace, int port) {
        this.hosts = hosts;
        this.keyspace = keyspace;
        this.port = port;
    }


	/* (non-Javadoc)
	 * @see com.infocube.risk.db.Connection#getObjectStore(java.lang.Class)
	 */
	@Override
    public <T> ObjectStore<T> getObjectStore(Class<T> clazz) {
		if (mappingManager == null) {
			mappingManager = new MappingManager(getSession());
		}
		
		return new DbStore<T>(mappingManager.mapper(clazz));
	}
	
    public Session getSession() {
        if (session == null) {
            cluster = Cluster.builder().addContactPoints(hosts).withPort(port).build();
            session = cluster.connect(keyspace);
        }

		return session;
	}

    @Override
    public void close() {
        if (cluster != null) {
            try {
                cluster.close();
            } finally {

            }
        }
 else if (session != null) {
            try {
                session.close();
            } finally {

            }

        }
    }

}
