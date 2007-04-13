package org.pgist.funding;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple Pojo used to convey the cost of a funding option to the user
 * 
 * @author Matt Paulin
 */
public class PersonalFundingCostDTO {

	List<String> headers = new ArrayList<String>();
	List<PersonalFundingCostAlternativeDTO> alternatives = new ArrayList<PersonalFundingCostAlternativeDTO>();
	
	/**
	 * Returns a list of alternatives that are full of the data that corrisponds to the headers
	 * 
	 * @return	A list of all the alternatives to this funding cost
	 */
	public List<PersonalFundingCostAlternativeDTO> getAlternatives() {
		return alternatives;
	}
	public void setAlternatives(List<PersonalFundingCostAlternativeDTO> alternatives) {
		this.alternatives = alternatives;
	}
	
	/**
	 * A collection of strings that represent all the headers that are to be drawn in the funding cost
	 * 
	 * @return	A collection of strings that are to be used as headers for the columns
	 */
	public List<String> getHeaders() {
		return headers;
	}
	public void setHeaders(List<String> headers) {
		this.headers = headers;
	}	
}
