package org.pgist.discussion;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * 
 * @author kenny
 *
 * @hibernate.class table="pgist_info_structure" lazy="true"
 */
public class InfoStructure {
    
    
    private Long id;
    
    private String type;
    
    private List infoObjects = new ArrayList();
    
    
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
     * @hibernate.list table="pgist_info_object" lazy="true" cascade="all"
     * @hibernate.collection-one-to-many class="org.pgist.discussion.InfoObject"
     * @hibernate.collection-index column="child_index"
     * @hibernate.collection-key column="parent_id"
     */
    public List getInfoObjects() {
        return infoObjects;
    }


    public void setInfoObjects(List infoObjects) {
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
