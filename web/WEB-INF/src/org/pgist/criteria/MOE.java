package org.pgist.criteria;

import java.io.Serializable;

/**
 * Measure of Effeciency
 * 
 * @author Mike and Guirong
 * 
 * @hibernate.class table="pgist_ag_moe" lazy="true"
 */
public class MOE implements Serializable {
    
    
    private Long id;
    
    private String name;
    
    private String units;
    
    
    /**
     * @hibernate.id generator-class="native"
     */
    public Long getId() {
        return id;
    }
    
    
    public void setId(Long id) {
        this.id = id;
    }
    
    
    /**
     * @hibernate.property not-null="true"
     */
    public String getName() {
        return name;
    }
    
    
    public void setName(String name) {
        this.name = name;
    }
    
    
    /**
     * @hibernate.property not-null="true"
     */
    public String getUnits() {
        return units;
    }
    
    
    public void setUnits(String units) {
        this.units = units;
    }
    
    
}//class MOE
