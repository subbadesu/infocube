package com.infocube.risk.services;

import java.util.ArrayList;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Result;
import com.infocube.risk.db.ConnectionProperties;
import com.infocube.risk.db.DbConnection;
import com.infocube.risk.entities.Trade;

public class TradeService implements Service {
	private static final String DBSCHEMA_NAME = "infocube";
	private static final String DBHOST_NAME = "localhost";

	public ArrayList<Trade> getTrades(int portfolioId) {
		ArrayList<Trade> tradeList = new ArrayList<Trade>();
		DbConnection dbConnection = new DbConnection(new ConnectionProperties());
		Session session = dbConnection.getSession();
		MappingManager manager = new MappingManager(session);
		ResultSet results = session.execute("SELECT * FROM inforisk_trade");
		Mapper<Trade> mapper = manager.mapper(Trade.class);
		Result<Trade> trades = mapper.map(results);
		for (Trade t : trades) {
			tradeList.add(t);
		}
		dbConnection.close();
		return tradeList;
	}
}
