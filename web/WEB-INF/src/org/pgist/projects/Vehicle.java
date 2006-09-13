package org.pgist.projects;


/**
 * @author  guirong
 */
public class Vehicle {
    
    
    private Long id;
    
    private float milesPerGallon;
    
    private float milesPerYear;
    
    
    /**
     * @return  the id
     * @uml.property  name="id"
     */
    public Long getId() {
        return id;
    }
    
    
    /**
     * @param id  the id to set
     * @uml.property  name="id"
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    
    /**
     * @return  the milesPerGallon
     * @uml.property  name="milesPerGallon"
     */
    public float getMilesPerGallon() {
        return milesPerGallon;
    }
    
    
    /**
     * @param milesPerGallon  the milesPerGallon to set
     * @uml.property  name="milesPerGallon"
     */
    public void setMilesPerGallon(float milesPerGallon) {
        this.milesPerGallon = milesPerGallon;
    }
    
    
    /**
     * @return  the milesPerYear
     * @uml.property  name="milesPerYear"
     */
    public float getMilesPerYear() {
        return milesPerYear;
    }
    
    
    /**
     * @param milesPerYear  the milesPerYear to set
     * @uml.property  name="milesPerYear"
     */
    public void setMilesPerYear(float milesPerYear) {
        this.milesPerYear = milesPerYear;
    }
    
    
}//class Vehicle
