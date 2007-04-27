package org.pgist.packages.knapsack;

/**
 * Represents an item to put in the knapsack engine
 * 
 * @author Matt Paulin
 */
public abstract class KSItem {

	private double cost;
	private double profit;

	
	/**
	 * @param cost the cost to set
	 */
	public final void setCost(double cost) {
		this.cost = cost;
	}

	/**
	 * @param profit the profit to set
	 */
	public final void setProfit(double profit) {
		this.profit = profit;
	}

	/**
	 * Returns the cost of the item.  In the temps of the knapsack problem this is the amount of resources
	 * that this one item will use to add it to the solution.  So if the limiting factor for the knapsack is 
	 * weight, then this would be how heavy the item is.
	 * 
	 * @return	The cost for the item
	 */
	public double getCost() {
		return cost;
	}
	
	/**
	 * Returns the profit of the item.  In the temps of the knapsack problem this is the benifit of having
	 * this item.
	 * 
	 * @return	The cost for the item
	 */
	public double getProfit() {
		return profit;
	}

	public boolean isZeroed() {
		if(this.getCost() == 0 && this.getProfit() == 0) return true;
		return false;
	}

	public boolean dominates(KSItem nextItem) {
		if(this.getCost() >= nextItem.getCost() && this.getProfit() <= nextItem.getProfit()) return true;
		return false;
	}	
}
