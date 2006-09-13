package org.pgist.projects;

/**
 * Measure of 
 * @author  Mike and Guirong
 * @hibernate.class  table="pgist_ag_moe" lazy="true"
 */
public class MOE {
    
    
    private Long id;
    
    private String name;
    
    private String units;
    
    
    /**
     * @hibernate.id  generator-class="native"
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
     * @hibernate.property  not-null="true"
     * @uml.property  name="name"
     */
    public String getName() {
        return name;
    }
    
    
    /**
     * @param name  the name to set
     * @uml.property  name="name"
     */
    public void setName(String name) {
        this.name = name;
    }
    
    
    /**
     * @hibernate.property  not-null="true"
     * @uml.property  name="units"
     */
    public String getUnits() {
        return units;
    }
    
    
    /**
     * @param units  the units to set
     * @uml.property  name="units"
     */
    public void setUnits(String units) {
        this.units = units;
    }
    
    
}//class MOE
