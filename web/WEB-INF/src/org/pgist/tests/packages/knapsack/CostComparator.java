package org.pgist.tests.packages.knapsack;

import java.util.Comparator;

import org.pgist.packages.knapsack.KSItem;

/**
 * Used to sort KSItems by cost
 * @author Matt Paulin
 */
public class CostComparator implements Comparator<KSItem> {

	public int compare(KSItem o1, KSItem o2) {
		if(o1.getCost() > o2.getCost()) return -1;
		if(o1.getCost() < o2.getCost()) return 1;		
		return 0;
	}
}
