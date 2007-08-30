package org.pgist.packages.knapsack;

/**
 * Used to determine the best solution
 * 
 * @author Matt Paulin
 */
public class Gradient implements Comparable<Gradient>{
	private KSItem challenger;
	private KSItem currentlySelected;
	private KSChoices choices;
	private double slope;
	public Gradient(KSItem challenger, KSItem currentlySelected, KSChoices choices) {
		this.challenger = challenger;
		this.currentlySelected = currentlySelected;
		this.choices = choices;
		if((challenger.getCost() - currentlySelected.getCost()) == 0) {
			//This prevents infinte slopes
			this.slope = Double.MAX_VALUE;
		} else {			
			this.slope = (challenger.getProfit() - currentlySelected.getProfit())/(challenger.getCost() - currentlySelected.getCost());		
		}
	}
	
	/**
	 * @return the choices
	 */
	public KSChoices getChoices() {
		return choices;
	}

	/**
	 * @return the challenger
	 */
	public KSItem getChallenger() {
		return challenger;
	}
	
	/**
	 * @return the currentlySelected
	 */
	public KSItem getCurrentlySelected() {
		return currentlySelected;
	}

	/**
	 * @return the slope
	 */
	public double getSlope() {
		return slope;
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Gradient o) {
		if(this.getSlope() > o.getSlope()) return -1;
		if(this.getSlope() < o.getSlope()) return 1;
		return 0;
	}	
}
