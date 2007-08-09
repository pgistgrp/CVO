package org.pgist.users;

import org.postgis.hibernate.GeometryType;
import org.postgis.Geometry;

/**
 * TravelMarker:{mId:mId, mType:mType, mIndex:mIndex, mName:mName,
 * mData1:mData1, mData2:mData2, point:mPoint}
 * id::Integer (starts with 0!!)
 * type::{0,1,2}
 * index::Integer (starts with 0!!)
 * name::String
 * data1::String (can be null)
 * data2::String (can be null)
 * @author Martin and Guirong
 * 
 * @hibernate.class table="pgist_travel_marker"
 */

public class TravelMarker {
	private Long id;
	private Integer type;
	private Integer index;
	private String name;
	private String data1;
	private String data2;
	private Double lat;
	private Double lng;
	private Geometry point;
	
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
	 * @hibernate.property
	 */
	public Integer getType(){
		return this.type;
	}
	public void setType(Integer type){
		this.type = type;
	}
	
	/**
	 * @hibernate.property
	 */
	public Integer getIndex(){
		return this.index;
	}
	public void setIndex(Integer index){
		this.index = index;
	}

	/**
	 * @hibernate.property
	 */
	public String getName(){
		return this.name;
	}
	public void setName(String name){
		this.name = name;
	}
	
	/**
	 * @hibernate.property type="text"
	 */
	public String getData1(){
		return this.data1;
	}
	public void setData1(String d){
		this.data1 = d;
	}
	
	/**
	 * @hibernate.property type="text"
	 */
	public String getData2(){
		return this.data2;
	}
	public void setData2(String d){
		this.data2 = d;
	}

	public Double getLat(){
		return this.lat;
	}
	
	public void setLat(Double lat){
		this.lat = lat;
	}
	
	public Double getLng(){
		return this.lng;
	}
	
	public void setLng(Double lng){
		this.lng = lng;
	}
	
	/**
	 * @hibernate.property type="org.postgis.hibernate.GeometryType"
	 * @hibernate.column sql-type="geometry"
	 */
	public Geometry getPoint(){
		return this.point;
	}
	
	public void setPoint(Geometry point) {
		this.point = point;
	}
}
