package org.pgist.packages.knapsack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Used to hold all the selected items and allow the user to swap it in and out
 * 
 * @author Matt Paulin
 */
public class SelectedItemHolder {

	private HashMap<KSChoices, KSItem> selected = new HashMap<KSChoices, KSItem>();
	
	public KSItem getSelected(KSChoices choice) {
		return this.selected.get(choice);
	}
	
	public void setChoice(KSChoices choices, KSItem item) {
		this.selected.put(choices, item);
	}
	
	/**
	 * Calculates the total cost for all the items
	 */
	public double getTotalCost() {
		double total = 0;
		Iterator<KSItem> items = this.selected.values().iterator();
		while(items.hasNext()) {
			total = total + items.next().getCost();
		}
		
		return total;
	}
	
	/**
	 * Forms a collection of all the choices made, removing any 0,0 choices
	 */
	public Collection<KSItem> getResults() {
		ArrayList<KSItem> result = new ArrayList<KSItem>();
		Iterator<KSItem> items = this.selected.values().iterator();
		KSItem tempItem;
		while(items.hasNext()) {
			tempItem = items.next();
			if(!(tempItem instanceof ZeroItem)) {
				result.add(tempItem);				
			}
		}
		return result;
	}
}
