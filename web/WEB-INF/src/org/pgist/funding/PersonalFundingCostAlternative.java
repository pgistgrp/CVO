package org.pgist.funding;

import java.util.HashSet;
import java.util.Set;

/**
 * Pojo for storing all the data provided in a funding cost alternative
 * <p>
 * This data is to be cycled through and drawn to create a report
 * 
 * @author Matt Paulin
 */
public class PersonalFundingCostAlternative {

	Set<String> data = new HashSet<String>();

	/**
	 * The data associated with this cost alternative
	 * 
	 * @return	The collection of data to use
	 */
	public Set<String> getData() {
		return data;
	}

	public void setData(Set<String> data) {
		this.data = data;
	}	
}
