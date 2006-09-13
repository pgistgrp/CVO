package org.pgist.projects;

/**
 * @author  Guirong
 * 
 * @hibernate.class  table="pgist_corridors" lazy="true"
 */
public class Corridor {
    
    
    private Long id;
    
    private String name;
    
    private String description;
    
    private Long fpid;
    
    
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
     * @hibernate.property
     * @uml.property  name="description"
     */
    public String getDescription() {
        return description;
    }
    

    /**
     * @param description  the description to set
     * @uml.property  name="description"
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    
    /**
     * @hibernate.property
     * @uml.property  name="fpid"
     */
    public Long getFpid() {
        return fpid;
    }
    
    
    /**
     * @param fpid  the fpid to set
     * @uml.property  name="fpid"
     */
    public void setFpid(Long fpid) {
        this.fpid = fpid;
    }
    
    
}//class Corridor
