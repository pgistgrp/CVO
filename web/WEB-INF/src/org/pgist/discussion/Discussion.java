package org.pgist.discussion;

import java.io.Serializable;


/**
 * 
 * @author kenny
 *
 * @hibernate.class table="pgist_dicussion" lazy="true"
 */
public class Discussion implements Serializable {

    
    protected Long id;
    
    protected Long targetId;
    
    protected String targetType;
    
    protected boolean deleted;
    
    
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
     * @return
     * @hibernate.property not-null="true"
     */
    public Long getTargetId() {
        return targetId;
    }


    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }


    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public String getTargetType() {
        return targetType;
    }


    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }


    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public boolean isDeleted() {
        return deleted;
    }


    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }


}//class Discussion
