package org.pgist.funding;

import java.util.ArrayList;
import java.util.List;

/**
 * Pojo for storing all the data provided in a funding cost alternative
 * <p>
 * This data is to be cycled through and drawn to create a report
 * 
 * @author Matt Paulin
 */
public class PersonalFundingCostAlternativeDTO {

	List<String> data = new ArrayList<String>();

	/**
	 * The data associated with this cost alternative
	 * 
	 * @return	The collection of data to use
	 */
	public List<String> getData() {
		return data;
	}

	public void setData(List<String> data) {
		this.data = data;
	}
}
