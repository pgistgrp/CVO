package org.pgist.criteria;


/**
 * @author Mike and Guirong
 * 
 * @hibernate.class table="pgist_criteria_objective" lazy="true"
 */
public class Objective {

	private Long id; 
	
	private String description;

	
	/**
     * @hibernate.property not-null="true"
     */
	public String getDescription() {
		return description;
	}

	
	public void setDescription(String description) {
		this.description = description;
	}

	
    /**
     * @hibernate.id generator-class="native"
     */
	public Long getId() {
		return id;
	}

	
	public void setId(Long id) {
		this.id = id;
	}
	
	
}//public class
