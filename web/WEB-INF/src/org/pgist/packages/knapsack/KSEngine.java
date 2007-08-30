package org.pgist.packages.knapsack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;


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
		ArrayList<KSItem> result = new ArrayList<KSItem>();
		
		//Go through and add a 0,0 item to every choice
		Iterator<KSChoices> i = choices.iterator();
		KSChoices tempChoice;
		ZeroItem zItem;
		SelectedItemHolder holder = new SelectedItemHolder();
		
		//Initialize the holder with the zero item and remove the dominated items
		while(i.hasNext()) {
			tempChoice = i.next();
	
			zItem = new ZeroItem(tempChoice);
			tempChoice.getChoices().add(zItem);
			//tempChoice.removeDominatedItems();

			holder.setChoice(tempChoice, zItem);
		}
		
		griterate(holder, choices, limit);
		
		return holder.getResults();				
	}
	
	/**
	 * A gradient iteration
	 * @param holder
	 * @param choices
	 * @param limit
	 */
	public static void griterate(SelectedItemHolder holder, Collection<KSChoices> choices, float limit) {
		KSChoices tempChoice;
		Iterator<KSChoices> i = choices.iterator();
		
		//Calculate all the gradients
		ArrayList<Gradient> gradients = new ArrayList<Gradient>();
		Gradient tempGradient;
		KSItem selected;
		KSItem tempItem;
		i = choices.iterator();
		Iterator<KSItem> itemIter;
		//System.out.println("Start Gradient");
		while(i.hasNext()) {
			tempChoice = i.next();
			selected = holder.getSelected(tempChoice);
			itemIter = tempChoice.getChoices().iterator();
			while(itemIter.hasNext()) {
				tempItem = itemIter.next();
				if(!(tempItem instanceof ZeroItem)) {
					tempGradient = new Gradient(tempItem, selected, tempChoice);
					//System.out.println("Slope = " + tempGradient.getSlope());
					gradients.add(tempGradient);
				}
			}
		}
		//System.out.println("End Gradient");

		//Sort the gradients
		System.out.println("I Have " + gradients.size() + " gradietns");
		
		/*
		 * Commented by Zhong:
		 *     This sorting actually is in decreasing order.
		 */
		Collections.sort(gradients);
		
		Iterator<Gradient> gIter = gradients.iterator();
		KSItem oldSelected;
		while(gIter.hasNext()) {
			tempGradient = gIter.next();
			oldSelected = holder.getSelected(tempGradient.getChoices());
			if(oldSelected instanceof ZeroItem) {
				holder.setChoice(tempGradient.getChoices(), tempGradient.getChallenger());
				if(holder.getTotalCost() > limit) {
					holder.setChoice(tempGradient.getChoices(), oldSelected);				
				}				
			}
		}		
	}
	
	public static Collection<KSItem> mcLame(Collection<KSChoices> choices, float limit) {
		//Lame algorithm, just add the first item from each choice
		ArrayList<KSItem> result = new ArrayList<KSItem>();
		
		Iterator<KSChoices> i = choices.iterator();
		KSChoices tempChoice;
		while(i.hasNext()) {
			tempChoice = i.next();
			result.add(tempChoice.getChoices().get(0));
		}
		return result;		
	}
}
