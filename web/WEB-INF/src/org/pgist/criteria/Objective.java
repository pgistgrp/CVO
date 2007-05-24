package org.pgist.criteria;

import java.io.Serializable;
import java.util.Comparator;

/**
 * @author John
 * 
 * @hibernate.class table="pgist_crit_objective" lazy="true"
 */
public class Objective implements Serializable, Comparator {

	private Long id; 
	
	private String description;
	
	
    /**
     * @return
     * 
     * @hibernate.property not-null="true"
     */
	public String getDescription() {
		return description;
	}

	
	public void setDescription(String description) {
		this.description = description;
	}

	
    /**
     * @return
     * 
     * @hibernate.id generator-class="native"
     */
	public Long getId() {
		return id;
	}

	
	public void setId(Long id) {
		this.id = id;
	}
	
	
	public int compare(Object o1, Object o2) {	
		return ((Objective)o1).getDescription().compareToIgnoreCase(((Objective)o2).getDescription());
	}
	
	
}//public class
