package org.pgist.criteria;

import java.io.Serializable;

/**
 * @author John
 * 
 * @hibernate.class table="pgist_crit_objective" lazy="true"
 */
public class Objective implements Serializable {

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
	
	
}//public class
