package com.infocube.risk.entities;

import com.datastax.driver.mapping.annotations.Column;
//import com.datastax.driver.mapping.annotations.Frozen;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;
//import com.google.common.base.Objects;

@Table(keyspace = "infocube", name = "inforisk_portfolio")
public class Portfolio {

	@PartitionKey
	private int portfolioId;
	
	@Column
	private String portfolioName;
	
	public Portfolio() {
		
	}
	
	public Portfolio(int portfolioId, String portfolioName) {
		this.portfolioId = portfolioId;
		this.portfolioName = portfolioName;
	}

	public int getPortfolioId() {
		return portfolioId;
	}

	public String getPortfolioName() {
		return portfolioName;
	}

	public void setPortfolioName(String portfolioName) {
		this.portfolioName = portfolioName;
	}

	public void setPortfolioId(int portfolioId) {
		this.portfolioId = portfolioId;
	}
	
	
}
