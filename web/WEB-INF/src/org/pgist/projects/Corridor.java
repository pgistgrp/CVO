package org.pgist.projects;

import java.io.Serializable;

/**
 * @author Guirong
 * 
 * @hibernate.class table="pgist_corridors" lazy="true"
 */
public class Corridor implements Serializable {
    
    
    private Long id;
    
    private String name;
    
    private String description;
    
    private Long fpid;
    
    
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
     * @hibernate.property
     */
    public String getDescription() {
        return description;
    }
    

    public void setDescription(String description) {
        this.description = description;
    }
    
    
    /**
     * @hibernate.property
     */
    public Long getFpid() {
        return fpid;
    }
    
    
    public void setFpid(Long fpid) {
        this.fpid = fpid;
    }
    
    
}//class Corridor
