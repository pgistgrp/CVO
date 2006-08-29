/**
 * 
 */
package org.pgist.projects;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Guirong
 * @hibernate.class table="pgist_data_alternatives" lazy="true"
 */
public class ProjectAlternative {
	private Long id;
	private String name;
	private String description;
	private double cost;
	private Project project;
	private String annoString;

	public void setCost(double co){
		this.cost = co;
	}
	
    /**
     * @return
     * 
     * @hibernate.property
     */
	public double getCost(){
		return this.cost;
	}
		
	public void setId(Long id){
		this.id = id;
	}
	
	/**
	 * @hibernate.id generator-class="native"
	 */	
	public Long getId(){
		return this.id;
	}

	public void setName(String n){
		this.name = n;
	}
	
    /**
     * @return
     * 
     * @hibernate.property not-null="true"
     */
	public String getName(){
		return name;
	}

	public void setDescription(String d){
		this.description = d;
	}
	
    /**
     * @return
     * 
     * @hibernate.property
     */
	public String getDescription(){
		return this.description;
	}
	
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

    /**
     * 
     * @hibernate.property
     */
	public String getAnnoString(){
		return this.annoString;
	}

	public void setAnnoString(String anno){
		this.annoString = anno;
	}

}
