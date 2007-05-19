package org.pgist.packages.cluster;

import java.util.Iterator;
import java.util.List;

public class PackageItem implements Item {

	/**
	 * The user packageID held in this package
	 */
	private Long userPkgId;
	
	/**
	 * Binary representation of the package, its a list of all of the selected package alternatives
	 * and selected funding source alternatives
	 */
	private List<Boolean> choices;
	
	/**
	 * Creates and loads the choices for this user
	 */
	public PackageItem(Long userPkgId, List<Boolean> choices) {
		super();
		this.userPkgId = userPkgId;
		this.choices = choices;		
	}
	
		
	/* (non-Javadoc)
	 * @see org.pgist.packages.cluster.Clusterable#distance(org.pgist.packages.cluster.Clusterable)
	 */
	public float distance(Clusterable c) {
		if(c instanceof PackageItem) {
			return calcDist(this, (PackageItem)c);
		}
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.pgist.packages.cluster.Measureable#compactness()
	 */
	public float compactness() {
		//We think this is calculated elsewhere
		return 0;
	}
	

	/**
	 * Utility method for calculating the distance between two package items based on the choices
	 * <p>
	 * See the DaisyMatrixMaker or check the web for the Daisy algorithm.  
	 * @param item1
	 * @param item2
	 * @return
	 */
	public static float calcDist(PackageItem item1, PackageItem item2) {
		return DaisyMatrixMaker.calcDist(item1.choices, item2.choices);
	}




	public List<Boolean> getChoices() {
		return choices;
	}




	public void setChoices(List<Boolean> choices) {
		this.choices = choices;
	}




	public Long getUserPkgId() {
		return userPkgId;
	}




	public void setUserPkgId(Long userPkgId) {
		this.userPkgId = userPkgId;
	}
	
	/**
	 * To String
	 */
	public String toString() {
		String choiceStr = "";
		Iterator<Boolean> iValue = choices.iterator();
		Boolean value;
		while(iValue.hasNext()) {
			value = iValue.next();
			choiceStr = choiceStr + toNum(value) + ", ";
		}		
		return "ID = " + this.getUserPkgId() +":"+ choiceStr;
	}
	
	public static String toNum(Boolean b) {
		if(b) return "1";
		return "0";
	}	
}
