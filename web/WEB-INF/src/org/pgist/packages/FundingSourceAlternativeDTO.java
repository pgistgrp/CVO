package org.pgist.packages;

public class FundingSourceAlternativeDTO {

	private String name;
	private Long fundingSourceAlternativeId;
	
	private float estCost;
	private float avgCost;
	private float yourCost;
	
	
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
	
	
}
