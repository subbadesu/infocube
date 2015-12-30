package com.infocube.risk.entities;
import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

@Table(keyspace = "infocube", name = "inforisk_instrument")
public class Instrument {
	
	@PartitionKey
	int instrumentId;
	
	@Column
	String cusip;
	
	@Column
	String sedol;
	
	@Column
	String ric;
	
	@Column
	String isin;
	
	@Column
	int issuerId;
	
	@Column
	String symbol;
	
	@Column
	String curve;
	
	@Column
	String tenor;
	
	@Column
	String currency;
	
	public Instrument() {
		
	}
	
	public Instrument(int instrumentId) {
		this.instrumentId = instrumentId;
	}

	public Instrument(int instrumentId, String cusip, String sedol, String ric, String isin, int issuerId,
			String symbol, String curve, String tenor, String currency) {
		super();
		this.instrumentId = instrumentId;
		this.cusip = cusip;
		this.sedol = sedol;
		this.ric = ric;
		this.isin = isin;
		this.issuerId = issuerId;
		this.symbol = symbol;
		this.curve = curve;
		this.tenor = tenor;
		this.currency = currency;
	}

	public Instrument(int instrumentId, int issuerId) {
		super();
		this.instrumentId = instrumentId;
		this.issuerId = issuerId;
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

	/**
	 * @return the cusip
	 */
	public String getCusip() {
		return cusip;
	}

	/**
	 * @param cusip the cusip to set
	 */
	public void setCusip(String cusip) {
		this.cusip = cusip;
	}

	/**
	 * @return the sedol
	 */
	public String getSedol() {
		return sedol;
	}

	/**
	 * @param sedol the sedol to set
	 */
	public void setSedol(String sedol) {
		this.sedol = sedol;
	}

	/**
	 * @return the ric
	 */
	public String getRic() {
		return ric;
	}

	/**
	 * @param ric the ric to set
	 */
	public void setRic(String ric) {
		this.ric = ric;
	}

	/**
	 * @return the isin
	 */
	public String getIsin() {
		return isin;
	}

	/**
	 * @param isin the isin to set
	 */
	public void setIsin(String isin) {
		this.isin = isin;
	}

	/**
	 * @return the issuerId
	 */
	public int getIssuerId() {
		return issuerId;
	}

	/**
	 * @param issuerId the issuerId to set
	 */
	public void setIssuerId(int issuerId) {
		this.issuerId = issuerId;
	}

	/**
	 * @return the symbol
	 */
	public String getSymbol() {
		return symbol;
	}

	/**
	 * @param symbol the symbol to set
	 */
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	/**
	 * @return the curve
	 */
	public String getCurve() {
		return curve;
	}

	/**
	 * @param curve the curve to set
	 */
	public void setCurve(String curve) {
		this.curve = curve;
	}

	/**
	 * @return the tenor
	 */
	public String getTenor() {
		return tenor;
	}

	/**
	 * @param tenor the tenor to set
	 */
	public void setTenor(String tenor) {
		this.tenor = tenor;
	}

	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

}
