package org.pgist.projects;


/**
 * @author  Guirong
 * @hibernate.class  table="pgist_data_alternatives" lazy="true"
 */
public class ProjectAlternative extends Project {
    
    
    private Project project;
    
    
	/**
     * @return
     * @hibernate.many-to-one  column="project_id" class="org.pgist.projects.Project" cascade="none"
     * @uml.property  name="project"
     */
	public Project getProject(){
		return this.project;
	}
    
    
    /**
     * @param project  the project to set
     * @uml.property  name="project"
     */
    public void setProject(Project p){
        this.project = p;
    }
    
    
}//class ProjectAlternative
