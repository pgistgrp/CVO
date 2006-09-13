package org.pgist.projects;

import java.util.ArrayList;
import java.util.List;



/**
 * @author Guirong
 * 
 * @hibernate.class table="pgist_projects" lazy="true"
 */
public class Project {
    
    
    public static int PGIST_PROJECT_MODE_ROAD = 0;
    
    public static int PGIST_PROJECT_MODE_TRANSIT = 1;
    
    
    private Long id;
    
    private String name;
    
    private String description;
    
    /**
     * fpid: a comma-delimited footprint id list for
     * this porject, e.g., "1,2", or "1"
     */
    private String fpids;
    
    private int geoType;
    
    private String annoString;
    
    private List<ProjectAlternative> alternatives = new ArrayList<ProjectAlternative>();
    
    private double cost;
    
    private int transMode;
    
    private String sponsor;
    
    private Corridor corridor;
    
    
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
     * @hibernate.property
     */
	public double getCost(){
		return this.cost;
	}
	
    
    public void setCost(double co){
        this.cost = co;
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
	public int getGeoType(){
		return this.geoType;
	}
	
    
    /**
     * @param the geoType to set
     */
    public void setGeoType(int t){
        this.geoType = t;
    }
    
    
    /**
     * @return
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
     * @hibernate.property
     */
	public String getFpids(){
		return this.fpids;
	}
	
    
    public void setFpids(String fpids){
        this.fpids = fpids;
    }
    
    
    /**
     * This attribute is used for map display. It has the format of "color,width,opacity,anchorX,anchorY, shiftHorizontalAnchor,shiftVerticalAnchor,shiftHorizontalFeature, shiftVerticalFeature"
     * @hibernate.property
     */
	public String getAnnoString(){
		return this.annoString;
	}
    
	
    public void setAnnoString(String anno){
        this.annoString = anno;
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
     * @hibernate.property
     */
    public String getSponsor() {
        return sponsor;
    }
    
    
    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
    }
    
    
    /**
     * @hibernate.many-to-one column="corridor_id" cascade="all" lazy="true"
     */
    public Corridor getCorridor() {
        return corridor;
    }
    
    
    public void setCorridor(Corridor corridor) {
        this.corridor = corridor;
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
