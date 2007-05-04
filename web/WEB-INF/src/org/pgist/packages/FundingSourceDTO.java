package org.pgist.packages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.pgist.funding.FundingSource;


public class FundingSourceDTO implements Comparable<FundingSourceDTO> {
	private String name;
	private Long fundingSourceID;
	
	private List<FundingSourceAlternativeDTO> fundingSourceAlternatives = new ArrayList<FundingSourceAlternativeDTO>();

	/**
	 * Creates this DTO
	 */
	public FundingSourceDTO(FundingSource source) {
		super();
		this.name = source.getName();
		this.fundingSourceID = source.getId();
	}
	
	/**
	 * @return the fundingSourceAlternatives
	 */
	public List<FundingSourceAlternativeDTO> getFundingSourceAlternatives() {
		return fundingSourceAlternatives;
	}

	/**
	 * @param fundingSourceAlternatives the fundingSourceAlternatives to set
	 */
	public void setFundingSourceAlternatives(
			List<FundingSourceAlternativeDTO> fundingSourceAlternatives) {
		this.fundingSourceAlternatives = fundingSourceAlternatives;
	}

	/**
	 * Sorts the alternatives
	 */
	public void sort() {
		Collections.sort(this.fundingSourceAlternatives);
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
	
	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(FundingSourceDTO o) {
		return this.getName().compareTo(o.getName());
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj != null && obj instanceof FundingSourceDTO) {
			FundingSourceDTO temp = (FundingSourceDTO)obj;
			return this.getFundingSourceID().equals(temp.getFundingSourceID());
		}
		return false;
	}	
}
