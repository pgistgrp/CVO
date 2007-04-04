package org.pgist.funding;

import java.util.HashSet;
import java.util.Set;

/**
 * Simple Pojo used to convey the cost of a funding option to the user
 * 
 * @author Matt Paulin
 */
public class PersonalFundingCost {

	Set<String> headers = new HashSet<String>();
	Set<PersonalFundingCostAlternative> alternatives = new HashSet<PersonalFundingCostAlternative>();
	
	/**
	 * Returns a list of alternatives that are full of the data that corrisponds to the headers
	 * 
	 * @return	A list of all the alternatives to this funding cost
	 */
	public Set<PersonalFundingCostAlternative> getAlternatives() {
		return alternatives;
	}
	public void setAlternatives(Set<PersonalFundingCostAlternative> alternatives) {
		this.alternatives = alternatives;
	}
	
	/**
	 * A collection of strings that represent all the headers that are to be drawn in the funding cost
	 * 
	 * @return	A collection of strings that are to be used as headers for the columns
	 */
	public Set<String> getHeaders() {
		return headers;
	}
	public void setHeaders(Set<String> headers) {
		this.headers = headers;
	}	
}
