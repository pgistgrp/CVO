package org.pgist.discussion;


/**
 * 
 * @author kenny
 *
 * @hibernate.class table="pgist_info_object" lazy="true"
 */
public class InfoObject {
    
    
    private Long id;
    
    private Object object;
    
    
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
     * @return object
     * 
     * @hibernate.any id-type="long" cascade="all" meta-type="string"
     * @hibernate.any-column name="class_id"
     * @hibernate.any-column name="class_name"
     */
    public Object getObject() {
        return object;
    }
    
    
    public void setObject(Object object) {
        this.object = object;
    }
    
    
}//class InfoObject
