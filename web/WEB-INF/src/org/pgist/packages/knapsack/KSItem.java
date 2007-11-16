package org.pgist.packages.knapsack;

/**
 * Represents an item to put in the knapsack engine
 * 
 * @author Matt Paulin
 */
public abstract class KSItem implements Comparable<KSItem>{

	private float cost;
	private float profit;
	private float ratio;
	
	/**
	 * @param cost the cost to set
	 */
	public final void setCost(float cost) {
		this.cost = cost;
		calcRatio();
	}

	/**
	 * @param profit the profit to set
	 */
	public final void setProfit(float profit) {
		this.profit = profit;
		calcRatio();
	}

	/**
	 * Returns the cost of the item.  In the temps of the knapsack problem this is the amount of resources
	 * that this one item will use to add it to the solution.  So if the limiting factor for the knapsack is 
	 * weight, then this would be how heavy the item is.
	 * 
	 * @return	The cost for the item
	 */
	public float getCost() {
		return cost;
	}
	
	/**
	 * Returns the profit of the item.  In the temps of the knapsack problem this is the benifit of having
	 * this item.
	 * 
	 * @return	The cost for the item
	 */
	public float getProfit() {
		return profit;
	}

	/**
	 * Calculates the ratio of profit to cost
	 */
	public void calcRatio() {
		//Deal with divide by zero errors
		if(cost == 0) {
			ratio = 0;
		} else {
			ratio = (profit/cost);					
		}
	}
	
	/**
	 * Returns the ratio of profit to cost
	 * 
	 * @return	The profit divided by the cost
	 */
	public float getRatio() {
		return ratio;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(KSItem o) {
		if(this.getRatio() > o.getRatio()) return 1;
		if(this.getRatio() < o.getRatio()) return -1;		
		return 0;
	}	
}
