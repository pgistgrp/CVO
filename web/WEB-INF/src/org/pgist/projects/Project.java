package org.pgist.projects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Guirong, kenny
 * 
 * @hibernate.class table="pgist_projects" lazy="true"
 */
public class Project implements Serializable {
    
    
    private Long id;
    
    private String name;
    
    private String description;
    
    /**
     * project type<br>
     * <ul>
     *   <li>road</li>
     *   <li>transit</li>
     * </ul>
     */
    private String type;
    
    private Corridor corridor;
    
    private List<ProjectAlternative> alternatives = new ArrayList<ProjectAlternative>();
    
    private ProjectAlternative selected = null;
    
    
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
     * @return
     * @hibernate.property not-null="true"
     */
    public String getName(){
        return name;
    }
    
    
	public void setName(String n){
		this.name = n;
	}
	
    
    /**
     * @return
     * @hibernate.property
     */
	public String getDescription(){
		return this.description;
	}
	
    
    public void setDescription(String d){
        this.description = d;
    }
    
    
    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public String getType() {
        return type;
    }


    public void setType(String type) {
        this.type = type;
    }


    /**
     * @return
     * 
     * @hibernate.many-to-one column="corridor_id" cascade="all" lazy="true"
     */
    public Corridor getCorridor() {
        return corridor;
    }
    
    
    public void setCorridor(Corridor corridor) {
        this.corridor = corridor;
    }
    
    
    /**
     * @return
     * 
     * @hibernate.list lazy="false" cascade="all-delete-orphan"
     * @hibernate.collection-one-to-many class="org.pgist.projects.ProjectAlternative"
     * @hibernate.collection-index  column="index"
     * @hibernate.collection-key column="project_id"
     */
    public List<ProjectAlternative> getAlternatives(){
        return this.alternatives;
    }
    
    
	public void setAlternatives(List<ProjectAlternative> alts){
		this.alternatives = alts;
	}
    
	
    /**
     * @return
     * 
     * @hibernate.many-to-one column="alternative_id" cascade="none"
     */
    public ProjectAlternative getSelected() {
        return selected;
    }


    public void setSelected(ProjectAlternative selected) {
        this.selected = selected;
    }
    
    
    /*
     * ------------------------------------------------------------------------
     */
    
    
    public void addAlternative(ProjectAlternative a){
        alternatives.add(a);
    }
    
    
    public void removeAlternative(ProjectAlternative a){
        alternatives.remove(a);
    }


}//class Project
