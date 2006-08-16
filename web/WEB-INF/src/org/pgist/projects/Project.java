/**
 * 
 */
package org.pgist.projects;

import java.util.List;
import java.util.ArrayList;
import org.postgis.PGgeometry;
import org.postgis.MultiLineString;
import org.postgis.LineString;
import org.postgis.Point;
import org.postgis.Geometry;

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
	
	private Long id;
	private String name;
	private String description;
	private List alternatives = new ArrayList();
	private double cost;
	
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
	 */	public Long getId(){
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

	public void setDescrption(String d){
		this.description = d;
	}
	
    /**
     * @return
     * 
     * @hibernate.property
     */
	public String getDescrption(){
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
}