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
    
    private double avgCost;
    
    
    /**
     * 
     * @param choices
     * @param fundingSourceAltRef
     * @param cost
     * @param avgCost cost to average person
     */
    public FundingSourceKSItem(KSChoices choices, FundingSourceAltRef fundingSourceAltRef, double cost, double avgCost) {
        super(choices, cost, fundingSourceAltRef.getAlternative().getRevenue());
        this.avgCost = avgCost;
        this.fundingSourceAltRef = fundingSourceAltRef;
    }
    
    
	/**
	 * @return the fundingSourceAltRef
	 */
	public FundingSourceAltRef getFundingSourceAltRef() {
		return fundingSourceAltRef;
	}
	
	
    public double getAvgCost() {
        return avgCost;
    }


}//class FundingSourceKSItem
