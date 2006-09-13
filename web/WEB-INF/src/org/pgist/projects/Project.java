package org.pgist.projects;

import java.util.ArrayList;
import java.util.List;



/**
 * @author  Guirong
 * @hibernate.class  table="pgist_projects" lazy="true"
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
     * @param id  the id to set
     * @uml.property  name="id"
     */
    public void setId(Long id){
        this.id = id;
    }
    
    
    /**
     * @hibernate.id  generator-class="native"
     * @uml.property  name="id"
     */
    public Long getId(){
        return this.id;
    }
    
    
    /**
     * @return
     * @hibernate.property
     * @uml.property  name="cost"
     */
	public double getCost(){
		return this.cost;
	}
	
    
    /**
     * @param cost  the cost to set
     * @uml.property  name="cost"
     */
    public void setCost(double co){
        this.cost = co;
    }
    
    
    /**
     * @return
     * @hibernate.property  not-null="true"
     * @uml.property  name="name"
     */
    public String getName(){
        return name;
    }
    
    
	/**
     * @param name  the name to set
     * @uml.property  name="name"
     */
	public void setName(String n){
		this.name = n;
	}
	
    
    /**
     * @return
     * @hibernate.property
     * @uml.property  name="description"
     */
	public String getDescription(){
		return this.description;
	}
	
    
    /**
     * @param description  the description to set
     * @uml.property  name="description"
     */
    public void setDescription(String d){
        this.description = d;
    }
    
    
    /**
     * @return
     * @hibernate.property
     * @uml.property  name="geoType"
     */
	public int getGeoType(){
		return this.geoType;
	}
	
    
    /**
     * @param geoType  the geoType to set
     * @uml.property  name="geoType"
     */
    public void setGeoType(int t){
        this.geoType = t;
    }
    
    
    /**
     * @return
     * @hibernate.list  lazy="false" cascade="all-delete-orphan"
     * @hibernate.collection-one-to-many  class="org.pgist.projects.ProjectAlternative"
     * @hibernate.collection-index  column="index"
     * @hibernate.collection-key  column="project_id"
     * @uml.property  name="alternatives"
     */
    public List<ProjectAlternative> getAlternatives(){
        return this.alternatives;
    }
    
    
	/**
     * @param alternatives  the alternatives to set
     * @uml.property  name="alternatives"
     */
	public void setAlternatives(List<ProjectAlternative> alts){
		this.alternatives = alts;
	}
    
	
    /**
     * @return
     * @hibernate.property
     * @uml.property  name="fpids"
     */
	public String getFpids(){
		return this.fpids;
	}
	
    
    /**
     * @param fpids  the fpids to set
     * @uml.property  name="fpids"
     */
    public void setFpids(String fpids){
        this.fpids = fpids;
    }
    
    
    /**
     * This attribute is used for map display. It has the format of "color,width,opacity,anchorX,anchorY, shiftHorizontalAnchor,shiftVerticalAnchor,shiftHorizontalFeature, shiftVerticalFeature"
     * @hibernate.property
     * @uml.property  name="annoString"
     */
	public String getAnnoString(){
		return this.annoString;
	}
    
	
    /**
     * @param annoString  the annoString to set
     * @uml.property  name="annoString"
     */
    public void setAnnoString(String anno){
        this.annoString = anno;
    }
    
    
    /**
     * @return
     * @hibernate.property
     * @uml.property  name="transMode"
     */
    public int getTransMode() {

        return transMode;
    }
    
    
    /**
     * @param transMode  the transMode to set
     * @uml.property  name="transMode"
     */
    public void setTransMode(int transMode) {
        this.transMode = transMode;
    }
    
    
    /**
     * @hibernate.property
     * @uml.property  name="sponsor"
     */
    public String getSponsor() {
        return sponsor;
    }
    
    
    /**
     * @param sponsor  the sponsor to set
     * @uml.property  name="sponsor"
     */
    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
    }
    
    
    /**
     * @hibernate.property
     * @uml.property  name="corridor"
     */
    public Corridor getCorridor() {
        return corridor;
    }
    
    
    /**
     * @param corridor  the corridor to set
     * @uml.property  name="corridor"
     */
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
