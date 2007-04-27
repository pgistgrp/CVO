package org.pgist.tests.packages.knapsack;

import org.pgist.packages.knapsack.KSItem;

/**
 * Used to mock a knapsack item in the algorithm
 * 
 * @author Matt Paulin
 */
public class MockKSItem extends KSItem {
	private String name;
	
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


	public String toString() {
		return "NAME " + name + ":: profit [" + this.getProfit() + "] cost [" + this.getCost() + "]";
	}
}
