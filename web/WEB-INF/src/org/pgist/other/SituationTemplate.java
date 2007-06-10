package org.pgist.other;


/**
 * 
 * @author kenny
 * 
 * @hibernate.class table="pgist_situation_template"
 */
public class SituationTemplate {
    
    
    private Long id;
    
    private String name;
    
    private String path;
    
    
    /**
     * @return
     * 
     * @hibernate.id generator-class="native"
     */
    public Long getId() {
        return id;
    }
    
    
    public void setId(Long id) {
        this.id = id;
    }


    /**
     * @return
     * 
     * @hibernate.property
     */
    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    /**
     * @return
     * 
     * @hibernate.property
     */
    public String getPath() {
        return path;
    }


    public void setPath(String path) {
        this.path = path;
    }
    
    
}//class SituationTemplate
