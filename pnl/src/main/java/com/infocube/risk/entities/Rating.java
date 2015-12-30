package com.infocube.risk.entities;
import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

@Table(keyspace = "infocube", name = "inforisk_instrument")
public class Rating {
	
	@PartitionKey
	int ratingId;
	
	@Column
	String moodyRating;	
	
	@Column
	String spRating;
	
	@Column
	String fitchRating;
	
	@Column
	String internalRating;


	public Rating() {
		
	}

	public Rating(int ratingId) {
		this.ratingId = ratingId;
	}

	/**
	 * @return the ratingId
	 */
	public int getRatingId() {
		return ratingId;
	}

	/**
	 * @param ratingId the ratingId to set
	 */
	public void setRatingId(int ratingId) {
		this.ratingId = ratingId;
	}

	/**
	 * @return the moodyRating
	 */
	public String getMoodyRating() {
		return moodyRating;
	}

	/**
	 * @param moodyRating the moodyRating to set
	 */
	public void setMoodyRating(String moodyRating) {
		this.moodyRating = moodyRating;
	}

	/**
	 * @return the spRating
	 */
	public String getSpRating() {
		return spRating;
	}

	/**
	 * @param spRating the spRating to set
	 */
	public void setSpRating(String spRating) {
		this.spRating = spRating;
	}

	/**
	 * @return the fitchRating
	 */
	public String getFitchRating() {
		return fitchRating;
	}

	/**
	 * @param fitchRating the fitchRating to set
	 */
	public void setFitchRating(String fitchRating) {
		this.fitchRating = fitchRating;
	}

	/**
	 * @return the internalRating
	 */
	public String getInternalRating() {
		return internalRating;
	}

	/**
	 * @param internalRating the internalRating to set
	 */
	public void setInternalRating(String internalRating) {
		this.internalRating = internalRating;
	}
	
	
}
