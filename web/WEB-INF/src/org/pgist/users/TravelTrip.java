package org.pgist.users;

import java.util.HashSet;
import java.util.Set;
import org.postgis.Geometry;
/**
 * TravelTrip:{mode:tMode, frequency:tFrequency, markers:markerList, 
 * coords: a series of vertices, x,y,x,y,...}
 * @author Martin and Guirong
 * @hibernate.class table="pgist_travel_trip"
 */
public class TravelTrip {
	private long id;
	private Integer mode;
	private int frequency;
	private Set markers = new HashSet();
	private Geometry route;
	private User owner;
	private double[] coords;
	
	/**
	 * @hibernate.id generator-class="native"
	 */	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	/**
	 * @hibernate.property
	 */
	public Integer getMode() {
		return mode;
	}
	public void setMode(Integer mode) {
		this.mode = mode;
	}

	/**
	 * @hibernate.property
	 */
	public int getFrequency() {
		return frequency;
	}
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}
	
	/**
     * @hibernate.set lazy="false" cascade="all" order-by="index"
     * @hibernate.collection-one-to-many class="org.pgist.users.TravelMarker"
     * @hibernate.collection-key column="trip_id"
	 */
	public Set getMarkers() {
		return markers;
	}
	public void setMarkers(Set markers) {
		this.markers = markers;
	}
	
	/**
	 * @hibernate.property type="org.postgis.hibernate.GeometryType"
	 */
	public Geometry getRoute() {
		return route;
	}
	public void setRoute(Geometry route) {
		this.route = route;
	}
	
	/**
	 * @hibernate.many-to-one column="user_id" lazy="true"
	 */
	public User getOwner() {
		return owner;
	}
	public void setOwner(User owner) {
		this.owner = owner;
	}
	
	public double[] getCoords() {
		return coords;
	}
	public void setCoords(double[] coords) {
		this.coords = coords;
	}
}
