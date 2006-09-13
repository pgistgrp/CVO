package org.pgist.projects;


/**
 * @author Mike and Guirong
 * 
 * @hibernate.class table="pgist_ag_proj_crit" lazy="true"
 */
public class ProjectCriteria {
    
    
    private Long id;
    
    private Project project;
    
    private Criteria criterion;
    
    private Double value;
    
    
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
     * @hibernate.many-to-one column="project_id" cascade="all" lazy="true"
     */
    public Project getProject() {
        return project;
    }
    
    
    public void setProject(Project project) {
        this.project = project;
    }
    
    
    /**
     * @hibernate.many-to-one column="criterion_id" cascade="all" lazy="true"
     */
    public Criteria getCriterion() {
        return criterion;
    }
    
    
    public void setCriterion(Criteria criterion) {
        this.criterion = criterion;
    }
    
    
    /**
     * @hibernate.property
     */
    public Double getValue() {
        return value;
    }
    
    
    public void setValue(Double value) {
        this.value = value;
    }
    
    
}//class ProjectCriteria
