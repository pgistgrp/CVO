package org.pgist.packages;

import org.pgist.funding.FundingSourceAltRef;
import org.pgist.packages.knapsack.KSItem;

/**
 * A funding source that can be used in the knap sack algorithms
 * 
 * @author Matt Paulin
 */
public class FundingSourceKSItem extends KSItem {

	FundingSourceAltRef fundingSourceAltRef;

	/**
	 * @return the fundingSourceAltRef
	 */
	public FundingSourceAltRef getFundingSourceAltRef() {
		return fundingSourceAltRef;
	}

	/**
	 * @param fundingSourceAltRef the fundingSourceAltRef to set
	 */
	public void setFundingSourceAltRef(FundingSourceAltRef fundingSourceAltRef) {
		this.fundingSourceAltRef = fundingSourceAltRef;
	}	
}
