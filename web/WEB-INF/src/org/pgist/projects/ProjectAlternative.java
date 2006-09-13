package org.pgist.projects;


/**
 * @author Guirong
 * @hibernate.class table="pgist_data_alternatives" lazy="true"
 */
public class ProjectAlternative extends Project {
    
    
    private Project project;
    
    
	/**
     * @return
     * @hibernate.many-to-one column="project_id" cascade="none"
     */
	public Project getProject(){
		return this.project;
	}
    
    
    public void setProject(Project p){
        this.project = p;
    }
    
    
}//class ProjectAlternative
