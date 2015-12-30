package com.infocube.risk.entities;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

@Table(keyspace = "infocube", name = "inforisk_trade")
public class Trade {
	
	@PartitionKey(value = 0)
	private int portfolioId;

	@PartitionKey(value = 1)
	private int tradeId;

	@Column
	private int quantity;

	@Column
	private double notional;

	@Column
	private double notionalUSD;

	@Column
	private double marketValue;
	
	@Column
	private double marketValueUSD;

	@Column
	private int instrumentId;
//	PRIMARY KEY (PortfolioId, TradeId));
	
	public Trade() {
		
	}

	public Trade(int portfolioId, int tradeId, int instrumentId) {
		this.portfolioId = portfolioId;
		this.tradeId = tradeId;
		this.instrumentId = instrumentId;
	}

	public Trade(int portfolioId, int tradeId, int instrumentId, int quantity) {
		this.portfolioId = portfolioId;
		this.tradeId = tradeId;
		this.instrumentId = instrumentId;
		this.quantity = quantity;
	}

	public Trade(int portfolioId, int tradeId, int instrumentId, int quantity, double notional, double notionalUSD,
			double marketValue, double marketValueUSD) {
		this(portfolioId, tradeId, instrumentId);
		this.quantity = quantity;
		this.notional = notional;
		this.notionalUSD = notionalUSD;
		this.marketValue = marketValue;
		this.marketValueUSD = marketValueUSD;
	}

	/**
	 * @return the portfolioId
	 */
	public int getPortfolioId() {
		return portfolioId;
	}

	/**
	 * @param portfolioId the portfolioId to set
	 */
	public void setPortfolioId(int portfolioId) {
		this.portfolioId = portfolioId;
	}

	/**
	 * @return the tradeId
	 */
	public int getTradeId() {
		return tradeId;
	}

	/**
	 * @param tradeId the tradeId to set
	 */
	public void setTradeId(int tradeId) {
		this.tradeId = tradeId;
	}

	/**
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the notional
	 */
	public double getNotional() {
		return notional;
	}

	/**
	 * @param notional the notional to set
	 */
	public void setNotional(double notional) {
		this.notional = notional;
	}

	/**
	 * @return the notionalUSD
	 */
	public double getNotionalUSD() {
		return notionalUSD;
	}

	/**
	 * @param notionalUSD the notionalUSD to set
	 */
	public void setNotionalUSD(double notionalUSD) {
		this.notionalUSD = notionalUSD;
	}

	/**
	 * @return the marketValue
	 */
	public double getMarketValue() {
		return marketValue;
	}

	/**
	 * @param marketValue the marketValue to set
	 */
	public void setMarketValue(double marketValue) {
		this.marketValue = marketValue;
	}

	/**
	 * @return the marketValueUSD
	 */
	public double getMarketValueUSD() {
		return marketValueUSD;
	}

	/**
	 * @param marketValueUSD the marketValueUSD to set
	 */
	public void setMarketValueUSD(double marketValueUSD) {
		this.marketValueUSD = marketValueUSD;
	}

	/**
	 * @return the instrumentId
	 */
	public int getInstrumentId() {
		return instrumentId;
	}

	/**
	 * @param instrumentId the instrumentId to set
	 */
	public void setInstrumentId(int instrumentId) {
		this.instrumentId = instrumentId;
	}

	public Pnl getPnl() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
