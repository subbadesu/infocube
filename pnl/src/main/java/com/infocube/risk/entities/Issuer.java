package com.infocube.risk.entities;
import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

@Table(keyspace = "infocube", name = "inforisk_issuer")
public class Issuer {

	@Column
	int issuerId;

	@Column
	String issuerLongName;

	@Column
	String issuerShortName;

	@Column
	String issuerRating;

	@Column
	int sectorId;

	public Issuer() {
		
	}
	
	public Issuer(int issuerId, String issuerLongName, String issuerShortName, String issuerRating, int sectorId) {
		this.issuerId = issuerId;
		this.issuerLongName = issuerLongName;
		this.issuerShortName = issuerShortName;
		this.issuerRating = issuerRating;
		this.sectorId = sectorId;
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
	 * @return the issuerLongName
	 */
	public String getIssuerLongName() {
		return issuerLongName;
	}

	/**
	 * @param issuerLongName the issuerLongName to set
	 */
	public void setIssuerLongName(String issuerLongName) {
		this.issuerLongName = issuerLongName;
	}

	/**
	 * @return the issuerShortName
	 */
	public String getIssuerShortName() {
		return issuerShortName;
	}

	/**
	 * @param issuerShortName the issuerShortName to set
	 */
	public void setIssuerShortName(String issuerShortName) {
		this.issuerShortName = issuerShortName;
	}

	/**
	 * @return the issuerRating
	 */
	public String getIssuerRating() {
		return issuerRating;
	}

	/**
	 * @param issuerRating the issuerRating to set
	 */
	public void setIssuerRating(String issuerRating) {
		this.issuerRating = issuerRating;
	}

	/**
	 * @return the sectorId
	 */
	public int getSectorId() {
		return sectorId;
	}

	/**
	 * @param sectorId the sectorId to set
	 */
	public void setSectorId(int sectorId) {
		this.sectorId = sectorId;
	}
	
	
}
