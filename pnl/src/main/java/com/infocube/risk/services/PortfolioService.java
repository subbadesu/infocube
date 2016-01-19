package com.infocube.risk.services;

import java.util.ArrayList;
import java.util.List;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import com.infocube.risk.db.ConnectionProperties;
import com.infocube.risk.db.DbConnection;
import com.infocube.risk.db.ObjectStore;
import com.infocube.risk.entities.Instrument;
import com.infocube.risk.entities.Portfolio;

public class PortfolioService implements Service {

//	private static final String PORTFOLIO_TABLE_NAME = "inforisk_portfolio";
	private static final String DBSCHEMA_NAME = "infocube";
	private static final String DBHOST_NAME = "localhost";

	public  ArrayList<Portfolio>  getPortfolios()
	{
		ArrayList<Portfolio>  portfolioList = new ArrayList<Portfolio>();
		  DbConnection dbConnection = new DbConnection(new ConnectionProperties());
		  
		  try {
			  ObjectStore<Portfolio> portfolioStore = dbConnection.getObjectStore(Portfolio.class);
		      Portfolio p = portfolioStore.get(1);
		      portfolioList.add(p);
		  }
		  catch(Exception e) {
			  e.printStackTrace();
		  }
	      dbConnection.close();
		
	      return portfolioList;
	}
	
	
	public  ArrayList<Portfolio>  getPortfoliostmp()
	{
	      ArrayList<Portfolio>  portfolioList = new ArrayList<Portfolio>();
		  Portfolio p = new Portfolio(1,"Equity");
	      portfolioList.add(p);
		
	      return portfolioList;
	        
		
	}

	public ArrayList<Portfolio> getPortfolio() {
		Portfolio p1 = new Portfolio(1, "Equities");
		Portfolio p2 = new Portfolio(1, "Credit");
		Portfolio p3 = new Portfolio(1, "Rates");
		ArrayList<Portfolio> p = new ArrayList<Portfolio>();
		p.add(p1);
		p.add(p2);
		p.add(p3);

		return p;

	}
}
