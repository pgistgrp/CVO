package org.pgist.packages;

import java.util.SortedSet;
import java.util.TreeSet;


public class FundingSourceDTO {
	String name;
	Long fundingSourceID;
	
	SortedSet<FundingSourceAlternativeDTO> fundingSourceAlternatives = new TreeSet<FundingSourceAlternativeDTO>();

	/**
	 * @return the fundingSourceAlternatives
	 */
	public SortedSet<FundingSourceAlternativeDTO> getFundingSourceAlternatives() {
		return fundingSourceAlternatives;
	}

	/**
	 * @param fundingSourceAlternatives the fundingSourceAlternatives to set
	 */
	public void setFundingSourceAlternatives(
			SortedSet<FundingSourceAlternativeDTO> fundingSourceAlternatives) {
		this.fundingSourceAlternatives = fundingSourceAlternatives;
	}

	/**
	 * @return the fundingSourceID
	 */
	public Long getFundingSourceID() {
		return fundingSourceID;
	}

	/**
	 * @param fundingSourceID the fundingSourceID to set
	 */
	public void setFundingSourceID(Long fundingSourceID) {
		this.fundingSourceID = fundingSourceID;
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

	
}
