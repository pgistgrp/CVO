package org.pgist.projects;


/**
 * @author guirong
 * 
 * @hibernate.class table="pgist_vehicle" lazy="true"
 */
public class Vehicle {
    
    
    private Long id;
    
    private float milesPerGallon;
    
    private float milesPerYear;
    
    
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
    
    
}//class Vehicle
