package org.pgist.projects;

import java.io.Serializable;
import java.util.SortedSet;
import java.util.TreeSet;


/**
 * Project class for transportation classes.<br>
 * 
 * One project consists of multiple project alternatives. Both project class and
 * project alternative class are self cotained, i.e., they don't depend on one
 * specific decision situation. Instead, decision situation class (@see org.pgist.cvo.CCT)
 * maintains a set of projects and funding sources. One project instance and one
 * funding source instance can be shared by different dicision situations. 
 * 
 * @author Guirong, kenny
 * 
 * @hibernate.class table="pgist_projects" lazy="true"
 */
public class Project implements Serializable {
    
    
    public static final int TRANSMODE_ROAD    = 1;
    public static final int TRANSMODE_TRANSIT = 2;
    
    private Long id;
    
    private String name;
    
    private String description;
    
    /**
     * project transportation mode<br>
     * <ul>
     *   <li>TRANSMODE_ROAD - road</li>
     *   <li>TRANSMODE_TRANSIT - transit</li>
     * </ul>
     */
    private int transMode;
    
    private Corridor corridor;
    
    private SortedSet<ProjectAlternative> alternatives = new TreeSet<ProjectAlternative>();
    
    /**
     * inclusive==true, multiple alternatives can be selected
     * inclusive==false, only one alternative can be selected
     */
    private boolean inclusive = false;
    
    /**
     * No use, maybe deleted later
     */
    private ProjectAlternative selected = null;
    
    /**
     * This is not a persistent value
     */
    private Object value;
    
    
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
     * @hibernate.property
     */
    public int getTransMode() {

        return transMode;
    }
    
    
    public void setTransMode(int transMode) {
        this.transMode = transMode;
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
     * @hibernate.set lazy="false" cascade="all-delete-orphan" sort="org.pgist.projects.ProjectAlternativeComparator"
     * @hibernate.collection-key column="project_id"
     * @hibernate.collection-one-to-many class="org.pgist.projects.ProjectAlternative"
     */
    public SortedSet<ProjectAlternative> getAlternatives(){
        return this.alternatives;
    }
    
    
	public void setAlternatives(SortedSet<ProjectAlternative> alts){
		this.alternatives = alts;
	}
    
	
    /**
     * @return
     * @hibernate.property
     */
    public boolean isInclusive() {
        return inclusive;
    }


    public void setInclusive(boolean inclusive) {
        this.inclusive = inclusive;
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


    public Object getValue() {
        return value;
    }


    public void setValue(Object value) {
        this.value = value;
    }


}//class Project
