package org.pgist.packages;

import org.pgist.funding.FundingSourceAlternative;

public class FundingSourceAlternativeDTO implements Comparable<FundingSourceAlternativeDTO> {

	private String name;
	private Long fundingSourceAlternativeId;
	
	private float estCost;
	private float avgCost;
	private float yourCost;
	
	/**
	 * Constructs the DTO
	 * 
	 * @param tempAlt
	 */
	public FundingSourceAlternativeDTO(FundingSourceAlternative tempAlt, float yourCost) {
		super();
		this.name = tempAlt.getName();
		this.fundingSourceAlternativeId = tempAlt.getId();
		this.estCost = tempAlt.getRevenue();
		this.avgCost = tempAlt.getAvgCost();
		this.yourCost = yourCost;
	}
	
	/**
	 * @return the fundingSourceAlternativeId
	 */
	public Long getFundingSourceAlternativeId() {
		return fundingSourceAlternativeId;
	}
	/**
	 * @param fundingSourceAlternativeId the fundingSourceAlternativeId to set
	 */
	public void setFundingSourceAlternativeId(Long fundingSourceAlternativeId) {
		this.fundingSourceAlternativeId = fundingSourceAlternativeId;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the avgCost
	 */
	public float getAvgCost() {
		return avgCost;
	}
	/**
	 * @param avgCost the avgCost to set
	 */
	public void setAvgCost(float avgCost) {
		this.avgCost = avgCost;
	}
	/**
	 * @return the estCost
	 */
	public float getEstCost() {
		return estCost;
	}
	/**
	 * @param estCost the estCost to set
	 */
	public void setEstCost(float estCost) {
		this.estCost = estCost;
	}
	/**
	 * @return the yourCost
	 */
	public float getYourCost() {
		return yourCost;
	}
	/**
	 * @param yourCost the yourCost to set
	 */
	public void setYourCost(float yourCost) {
		this.yourCost = yourCost;
	}
	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(FundingSourceAlternativeDTO o) {		
		return this.getName().compareTo(o.getName());
	}	
}
