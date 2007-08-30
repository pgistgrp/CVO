package org.pgist.packages;

import org.pgist.funding.FundingSourceAltRef;
import org.pgist.packages.knapsack.KSChoices;
import org.pgist.packages.knapsack.KSItem;

/**
 * A funding source that can be used in the knap sack algorithms
 * 
 * @author Matt Paulin
 */
public class FundingSourceKSItem extends KSItem {
    
    
    FundingSourceAltRef fundingSourceAltRef;
    
    
    public FundingSourceKSItem(KSChoices choices, FundingSourceAltRef fundingSourceAltRef, double cost) {
        super(choices, cost, fundingSourceAltRef.getAlternative().getRevenue());
        this.fundingSourceAltRef = fundingSourceAltRef;
    }
    
    
	/**
	 * @return the fundingSourceAltRef
	 */
	public FundingSourceAltRef getFundingSourceAltRef() {
		return fundingSourceAltRef;
	}
	
	
}//class FundingSourceKSItem
