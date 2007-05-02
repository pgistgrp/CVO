package org.pgist.projects;

import java.io.Serializable;


/**
 * @author Guirong, kenny
 * 
 * @hibernate.class table="pgist_project_alternatives" lazy="true"
 */
public class ProjectAlternative implements Serializable, Comparable {
    
    
    public static int PGIST_PROJECT_MODE_ROAD = 0;
    
    public static int PGIST_PROJECT_MODE_TRANSIT = 1;
    
    
    private Long id;
    
    private String name;
    
    private String shortDesc;
    
    private String detailedDesc;
    
    private String statementFor;
    
    private String statementAgainst;
    
    /**
     * "links" is a rich text paragraph which stores a list of links
     */
    private String links;
    
    private double cost;
    
    private String sponsor;
    
    private int geoType;
    
    private String annoString;
    
    private String photo;
    
    private Project project;
    
    /**
     * fpid: a comma-delimited footprint id list for
     * this porject, e.g., "1,2", or "1"
     */
    private String fpids;
    
    /**
     * state for project alternative
     * <ul>
     *   <li>0 - project is active</li>
     *   <li>1 - project is chosen</li>
     *   <li>2 - project is done</li>
     *   <li>3 - project is inactive</li>
     */
    private int state = 0;
    
    /**
     * Average grade based on equal weighting for all criteria,
     * need to be updated every time when participant changed his weights.
     * (TODO not fully defined)
     */
    private int eqlWeights = 0;
    
    /**
     * Average grade based on all participants weighting for all criteria
     * need to be updated every time when participant changed his weights.
     * (TODO not fully defined)
     */
    private int allWeights = 0;
    
    private String county;
    
    private Object object;
    
    
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
     * @hibernate.property length="2000"
     */
    public String getDetailedDesc() {
        return detailedDesc;
    }


    public void setDetailedDesc(String detailedDesc) {
        this.detailedDesc = detailedDesc;
    }


    /**
     * @return
     * @hibernate.property
     */
    public String getShortDesc() {
        return shortDesc;
    }


    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }


    /**
     * @return
     * @hibernate.property
     */
    public String getStatementAgainst() {
        return statementAgainst;
    }


    public void setStatementAgainst(String statementAgainst) {
        this.statementAgainst = statementAgainst;
    }


    /**
     * @return
     * @hibernate.property
     */
    public String getStatementFor() {
        return statementFor;
    }


    public void setStatementFor(String statementFor) {
        this.statementFor = statementFor;
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
    public String getLinks() {
        return links;
    }


    public void setLinks(String links) {
        this.links = links;
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
    public String getPhoto() {
        return photo;
    }


    public void setPhoto(String photo) {
        this.photo = photo;
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


    /**
     * @return
     * @hibernate.property
     */
    public int getState() {
        return state;
    }


    public void setState(int state) {
        this.state = state;
    }


    /**
     * @return
     * @hibernate.property
     */
    public int getAllWeights() {
        return allWeights;
    }


    public void setAllWeights(int allWeights) {
        this.allWeights = allWeights;
    }


    /**
     * @return
     * @hibernate.property
     */
    public int getEqlWeights() {
        return eqlWeights;
    }


    public void setEqlWeights(int eqlWeights) {
        this.eqlWeights = eqlWeights;
    }


    /**
     * @return
     * @hibernate.property
     */
    public String getCounty() {
        return county;
    }


    public void setCounty(String county) {
        this.county = county;
    }


    /*
     * ------------------------------------------------------------------------
     */
    
    
    public Object getObject() {
        return object;
    }


    public void setObject(Object object) {
        this.object = object;
    }


	public int compareTo(Object o) {
		if(o instanceof ProjectAlternative) {
			return this.name.compareTo(((ProjectAlternative)o).getName());
		}
		return 0;
	}
    
	public boolean equals(Object obj) {
		if(obj != null && obj instanceof ProjectAlternative) {
			ProjectAlternative temp = (ProjectAlternative)obj;
			if(this.getId().equals(temp.getId())) return true;
		}
		return false;
	}
    
}//class ProjectAlternative
