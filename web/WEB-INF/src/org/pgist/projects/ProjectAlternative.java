package org.pgist.projects;

import java.io.Serializable;


/**
 * @author Guirong, kenny
 * 
 * @hibernate.class table="pgist_project_alternatives" lazy="true"
 */
public class ProjectAlternative implements Serializable {
    
    
    public static int PGIST_PROJECT_MODE_ROAD = 0;
    
    public static int PGIST_PROJECT_MODE_TRANSIT = 1;
    
    
    private Long id;
    
    private String name;
    
    private String description;
    
    private double cost;
    
    private String sponsor;
    
    private int geoType;
    
    private String annoString;
    
    private int transMode;
    
    private Project project;
    
    /**
     * fpid: a comma-delimited footprint id list for
     * this porject, e.g., "1,2", or "1"
     */
    private String fpids;
    
    
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
    public String getFpids(){
        return this.fpids;
    }
    
    
    public void setFpids(String fpids){
        this.fpids = fpids;
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
     * @hibernate.property
     */
    public String getSponsor() {
        return sponsor;
    }
    
    
    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
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
