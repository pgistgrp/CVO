package org.pgist.projects;

import java.io.Serializable;

import org.pgist.criteria.Objective;


/**
 * @author Mike and Guirong
 * 
 * @hibernate.class table="pgist_ag_alternative_crit_obj" lazy="true"
 */
public class ProjectAltCriteriaObjective implements Serializable {
    
    
    private Long id;
    
    private ProjectAlternativeCriteria criterion;
    
    private Objective objective;
    
    private int grade;
    
    
    /**
     * @hibernate.id generator-class="native"
     */
    public Long getId() {
        return id;
    }
    
    
    public void setId(Long id) {
        this.id = id;
    }
    
    
    /**
     * @hibernate.many-to-one column="criterion_id" cascade="none" lazy="true"
     */
    public ProjectAlternativeCriteria getCriterion() {
        return criterion;
    }
    
    
    public void setCriterion(ProjectAlternativeCriteria criterion) {
        this.criterion = criterion;
    }
    
    
    /**
     * @hibernate.many-to-one column="objective_id" cascade="none" lazy="true"
     */
    public Objective getObjective() {
        return objective;
    }
    
    
    public void setObjective(Objective objective) {
        this.objective = objective;
    }
    
    
    /**
     * @hibernate.property
     */
    public int getGrade() {
        return grade;
    }
    
    
    public void setGrade(int grade) {
        this.grade = grade;
    }
    
    
}//class ProjectAltCriteriaObjective
