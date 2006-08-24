package org.pgist.discussion;


/**
 * 
 * @author kenny
 *
 * @hibernate.class table="pgist_info_object" lazy="true"
 */
public class InfoObject extends GenericInfo {
    
    
    private Object object;
    
    
    /**
     * @return object
     * 
     * @hibernate.any id-type="long" cascade="all" meta-type="string"
     * @hibernate.any-column name="class_name"
     * @hibernate.any-column name="class_id"
     */
    public Object getObject() {
        return object;
    }
    
    
    public void setObject(Object object) {
        this.object = object;
    }


}//class InfoObject
