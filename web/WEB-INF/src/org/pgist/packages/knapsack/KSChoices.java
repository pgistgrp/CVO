package org.pgist.packages.knapsack;

import java.util.ArrayList;
import java.util.List;

/**
 * Used to hold multiple mutually exclusive choices of KSItems
 * 
 * @author Matt Paulin
 */
public class KSChoices {
	
	private List<KSItem> choices = new ArrayList<KSItem>();

	/**
	 * @return the choices
	 */
	public List<KSItem> getChoices() {
		return choices;
	}

	/**
	 * @param choices the choices to set
	 */
	public void setChoices(List<KSItem> choices) {
		this.choices = choices;
	}	
}
