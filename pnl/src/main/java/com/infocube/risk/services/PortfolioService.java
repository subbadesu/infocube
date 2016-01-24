package com.infocube.risk.services;

import java.util.ArrayList;
import java.util.List;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Result;
import com.infocube.risk.db.ConnectionProperties;
import com.infocube.risk.db.DbConnection;
import com.infocube.risk.db.ObjectStore;
import com.infocube.risk.entities.Instrument;
import com.infocube.risk.entities.Portfolio;
import com.infocube.risk.entities.Trade;

public class PortfolioService implements Service {

	// private static final String PORTFOLIO_TABLE_NAME = "inforisk_portfolio";
	private static final String DBSCHEMA_NAME = "infocube";
	private static final String DBHOST_NAME = "localhost";

	public ArrayList<Portfolio> getPortfolio(int portfolioID) {
		ArrayList<Portfolio> portfolioList = new ArrayList<Portfolio>();
		DbConnection dbConnection = new DbConnection(new ConnectionProperties());

		try {
			ObjectStore<Portfolio> portfolioStore = dbConnection.getObjectStore(Portfolio.class);
			Portfolio p = portfolioStore.get(1);
			portfolioList.add(p);
		} catch (Exception e) {
			e.printStackTrace();
		}
		dbConnection.close();

		return portfolioList;
	}

	public ArrayList<Portfolio> getPortfolios() {
		ArrayList<Portfolio> portfolioList = new ArrayList<Portfolio>();
		DbConnection dbConnection = new DbConnection(new ConnectionProperties());
		Session session = dbConnection.getSession();
		MappingManager manager = new MappingManager(session);
		ResultSet results = session.execute("SELECT * FROM inforisk_portfolio");
		Mapper<Portfolio> mapper = manager.mapper(Portfolio.class);
		Result<Portfolio> portfolios = mapper.map(results);
		for (Portfolio u : portfolios) {
			portfolioList.add(u);
		}
		dbConnection.close();
		return portfolioList;

	}

	
}
