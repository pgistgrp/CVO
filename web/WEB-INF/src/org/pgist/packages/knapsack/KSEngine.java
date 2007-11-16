package org.pgist.packages.knapsack;

import java.util.Collection;

/**
 * The class used to find the solution to the knapsack problem
 * 
 * @author Matt Paulin
 */
public class KSEngine {
	
	/**
	 * Runs the full algorithm and provides the results
	 * 
	 * @param	items	A collection of the choices with the items to use
	 * @param	limit	The limit for the total cost of the items
	 * @return	results	A collection of KSItems that make up the results
	 */
	public static Collection<KSItem> mcknap(Collection<KSChoices> choices, float limit) {
		//Find any choices that dominate all other options
		//Remove any choices that are dominated
		//Sort the remaining items
		//Add them to the results
		return null;
	}

}
