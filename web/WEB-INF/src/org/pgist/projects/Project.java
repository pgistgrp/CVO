/**
 *
 */
package org.pgist.projects;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Guirong
 * @hibernate.class table="pgist_data_projects" lazy="true"
 */
public class Project {

    /**
     * fpid: a comman-delimited footprint id list for
     * this porject, e.g., "1,2", or "1"
     */
    private String fpids;
    private int geoType;
    private String annoString;

    private Long id;
    private String name;
    private String description;
    private List alternatives = new ArrayList();
    private double cost;
    private int mode;
    private String sponsor;

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

	public void setGeoType(int t){
		this.geoType = t;
	}

    /**
     * @return
     *
     * @hibernate.property
     */
	public int getGeoType(){
		return this.geoType;
	}

	public void setAlternatives(List alts){
		this.alternatives = alts;
	}

	/**
	 * @hibernate.list lazy="false" cascade="all-delete-orphan"
	 * @hibernate.collection-one-to-many class="org.pgist.projects.ProjectAlternative"
	 * @hibernate.collection-index column="index"
	 * @hibernate.collection-key column="project_id"
	 *
	 * @return
	 */
	public List getAlternatives(){
		return this.alternatives;
	}

	public void addAlternative(ProjectAlternative a){
		this.alternatives.add(a);
	}

	public void removeAlternative(ProjectAlternative a){
		this.alternatives.remove(a);
	}

	public void setFpids(String fpids){
		this.fpids = fpids;
	}

    /**
     * @return
     *
     * @hibernate.property
     */
	public String getFpids(){
		return this.fpids;
	}

    /**
     * This attribute is used for map display. It has the format of
     * "color,width,opacity,anchorX,anchorY,
     * shiftHorizontalAnchor,shiftVerticalAnchor,shiftHorizontalFeature, shiftVerticalFeature"
     * @hibernate.property
     */
	public String getAnnoString(){
		return this.annoString;
	}

    /**
     * @return
     *
     * @hibernate.property
     */
    public int getMode() {
        return mode;
    }

    /**
     * @return
     *
     * @hibernate.property
     */
    public String getSponsor() {
        return sponsor;
    }

    public void setAnnoString(String anno){
		this.annoString = anno;
	}

    public void setMode(int mode) {
        this.mode = mode;
    }

    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
    }

}
