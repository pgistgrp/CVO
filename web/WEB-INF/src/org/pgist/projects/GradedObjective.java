package org.pgist.projects;

import org.pgist.criteria.Objective;

/**
 * Represents an objective with a grade assigned to it.
 * 
 * @author Matt Paulin
 *
 * @hibernate.class table="pgist_project_graded_objective" lazy="true"
 */
public class GradedObjective {
	
	private Objective objective;
	private Long id;
	
	/**
	 * The grade assigned to the objective, should be between -3 and 3
	 */
	private int grade;

    /**
     * @hibernate.id generator-class="native"
     */
    public Long getId(){
        return this.id;
    }
    
    
    public void setId(Long id){
        this.id = id;
    }	
	
	/**
     * @hibernate.id generator-class="native"
     */    
	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

    /**
     * @return
     * 
     * @hibernate.many-to-one column="objective_id" cascade="none" lazy="true"
     */	
	public Objective getObjective() {
		return objective;
	}

	public void setObjective(Objective objective) {
		this.objective = objective;
	}		
}
