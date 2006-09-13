package org.pgist.projects;


/**
 * @author Mike and Guirong
 * 
 * @hibernate.class table="pgist_ag_proj_moe" lazy="true"
 */
public class ProjectMOE {
    
    
    private Project project;
    
    private MOE moe;
    
    private Double value;
    
    private Long id;
    
    
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
     * @hibernate.property
     */
    public Double getValue() {
        return value;
    }
    
    
    public void setValue(Double value) {
        this.value = value;
    }
    
    
    /**
     * @hibernate.many-to-one column="moe_id" cascade="all" lazy="true"
     */
    public MOE getMoe() {
        return moe;
    }
    
    
    public void setMoe(MOE moe) {
        this.moe = moe;
    }
    
    
}//class ProjectMOE
