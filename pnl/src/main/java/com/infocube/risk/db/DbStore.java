package com.infocube.risk.db;

import java.util.List;

import com.datastax.driver.core.ColumnMetadata;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.TableMetadata;
import com.datastax.driver.core.querybuilder.Clause;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import com.datastax.driver.core.querybuilder.Select.Where;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.Result;

public class DbStore<T> implements ObjectStore<T> {
	
	private Mapper<T> mapper;

	public DbStore(Mapper<T> mapper) {
		this.mapper = mapper;
	}

	@Override
    public void save(T entity) {
		mapper.save(entity);
	}

	@Override
    public void save(T... entities) {
		for (T entity : entities) {
			mapper.save(entity);
		}
	}

	@Override
    public T get(Object... primaryKeyValues) {
		return mapper.get(primaryKeyValues);
	}

	@Override
    public T get(Object primaryKeyValue) {
		return mapper.get(primaryKeyValue);
	}

    @Override
    public List<T> getAll(Object... partitionKeyValues) {
        TableMetadata tableMetadata = mapper.getTableMetadata();
        List<ColumnMetadata> partitionKeys = tableMetadata.getPartitionKey();
        Select select = QueryBuilder.select().from(tableMetadata.getKeyspace().getName(), tableMetadata.getName());
        Where where = select.where();
        for (int i = 0; i < partitionKeyValues.length; i++) {

            Clause eq = QueryBuilder.eq(partitionKeys.get(i).getName(), partitionKeyValues[i]);
            where = where.and(eq);
        }
        ResultSet resultSet = mapper.getManager().getSession().execute(where);
        Result<T> mappedObjects = mapper.map(resultSet);

        return mappedObjects.all();
    }

    @Override
    public List<T> getAll() {
        TableMetadata tableMetadata = mapper.getTableMetadata();
        Select select = QueryBuilder.select().from(tableMetadata.getKeyspace().getName(), tableMetadata.getName());
        ResultSet resultSet = mapper.getManager().getSession().execute(select);
        Result<T> mappedObjects = mapper.map(resultSet);

        return mappedObjects.all();
    }

    @Override
    public List<T> getByQuery(String query) {
        ResultSet resultSet = mapper.getManager().getSession().execute(query);
        Result<T> mappedObjects = mapper.map(resultSet);

        return mappedObjects.all();
    }

	@Override
    public void delete(T entity) {
		mapper.delete(entity);
	}

	@Override
    public void delete(T... entities) {
		for (T entity : entities) {
			mapper.delete(entity);
		}
	}

}
