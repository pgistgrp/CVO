package org.pgist.projects;


/**
 * @author  Mike and Guirong
 * @hibernate.class  table="pgist_ag_prjmoe" lazy="true"
 */
public class ProjectMOE {
    
    
    private Project project;
    
    private MOE moe;
    
    private Double value;
    
    private Long id;
    
    
    /**
     * @hibernate.id  generator-class="native"
     * @uml.property  name="id"
     */
    public Long getId() {
        return id;
    }
    
    
    /**
     * @param id  the id to set
     * @uml.property  name="id"
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    
    /**
     * @hibernate.property  not-null="true"
     * @uml.property  name="project"
     */
    public Project getProject() {
        return project;
    }
    
    
    /**
     * @param project  the project to set
     * @uml.property  name="project"
     */
    public void setProject(Project project) {
        this.project = project;
    }
    
    
    /**
     * @hibernate.property
     * @uml.property  name="value"
     */
    public Double getValue() {
        return value;
    }
    
    
    /**
     * @param value  the value to set
     * @uml.property  name="value"
     */
    public void setValue(Double value) {
        this.value = value;
    }
    
    
    /**
     * @hibernate.property  not-null="true"
     * @uml.property  name="moe"
     */
    public MOE getMoe() {
        return moe;
    }
    
    
    /**
     * @param moe  the moe to set
     * @uml.property  name="moe"
     */
    public void setMoe(MOE moe) {
        this.moe = moe;
    }
    
    
}//class ProjectMOE
