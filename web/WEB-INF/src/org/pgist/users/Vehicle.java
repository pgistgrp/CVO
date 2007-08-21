package org.pgist.users;

import java.io.Serializable;


/**
 * @author guirong
 * 
 * @hibernate.class table="pgist_vehicle" lazy="true"
 */
public class Vehicle implements Serializable {
    
    
    private Long id;
    
    private float milesPerGallon;
    
    private float milesPerYear;
    
    private float approxValue;
    
    private User owner;
    
    
    /**
     * @return
     * @hibernate.id generator-class="native"
     */
    public Long getId() {
        return id;
    }
    
    
    public void setId(Long id) {
        this.id = id;
    }
    
    
    /**
     * @return the milesPerGallon
     * @hibernate.property
     */
    public float getMilesPerGallon() {
        return milesPerGallon;
    }
    
    
    public void setMilesPerGallon(float milesPerGallon) {
        this.milesPerGallon = milesPerGallon;
    }
    
    
    /**
     * @return the milesPerYear
     * @hibernate.property
     */
    public float getMilesPerYear() {
        return milesPerYear;
    }
    
    
    public void setMilesPerYear(float milesPerYear) {
        this.milesPerYear = milesPerYear;
    }


    /**
     * @return
     * @hibernate.property
     */
    public float getApproxValue() {
        return approxValue;
    }


    public void setApproxValue(float approxValue) {
        this.approxValue = approxValue;
    }


    /**
     * @return
     * @hibernate.many-to-one column="owner_id" lazy="true" cascade="all"
     */
    public User getOwner() {
        return owner;
    }


    public void setOwner(User owner) {
        this.owner = owner;
    }
    
    
}//class Vehicle
