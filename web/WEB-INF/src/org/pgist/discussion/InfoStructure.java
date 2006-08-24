package org.pgist.discussion;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


/**
 * 
 * @author kenny
 *
 * @hibernate.class table="pgist_info_structure" lazy="true"
 */
public class InfoStructure extends GenericInfo {
    
    
    private String type;
    
    private Set infoObjects = new HashSet();
    
    
    /**
     * @return
     * 
     * @hibernate.property not-null="true"
     */
    public String getType() {
        return type;
    }
    
    
    public void setType(String type) {
        this.type = type;
    }


    /**
     * @return
     * 
     * @hibernate.set lazy="true" cascade="all" order-by="response_time desc"
     * @hibernate.collection-one-to-many class="org.pgist.discussion.InfoObject"
     * @hibernate.collection-key column="structure_id"
     */
    public Set getInfoObjects() {
        return infoObjects;
    }


    public void setInfoObjects(Set infoObjects) {
        this.infoObjects = infoObjects;
    }
    
    
    /*
     * ------------------------------------------------------------------------
     */
    
    
    public void addObject(Object object) {
        InfoObject one = new InfoObject();
        one.setObject(object);
        getInfoObjects().add(object);
    }//addObject()
    
    
    public void addObjects(Collection objects) {
        for (Object obj : objects) {
            InfoObject one = new InfoObject();
            one.setObject(obj);
            getInfoObjects().add(obj);
        }
    }//addObjects()


}//class InfoStructure
