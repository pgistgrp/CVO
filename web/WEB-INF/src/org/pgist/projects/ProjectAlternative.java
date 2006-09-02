/**
 *
 */
package org.pgist.projects;



/**
 * @author Guirong
 * @hibernate.class table="pgist_data_alternatives" lazy="true"
 */
public class ProjectAlternative extends Project {
    private Project project;
    public void setProject(Project p){
		this.project = p;
	}

	/**
	 * @hibernate.many-to-one column = "project_id" class="org.pgist.projects.Project" cascade="none"
	 * @hibernate.column name="project_id"
	 * @return
	 */
	public Project getProject(){
		return this.project;
	}
}
