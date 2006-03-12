package org.pgist.model;

import org.pgist.users.User;


/**
 * 
 * @author kenny
 *
 * @hibernate.class table="pgist_discussible"
 */
public abstract class Discussible {
    
    
    protected Long id;
    
    protected DiscourseObject discourseObject;
    
    protected User owner;
    
    private boolean deleted;
    
    
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
     * @hibernate.many-to-one column="do_id" lazy="true" class="org.pgist.model.DiscourseObject" cascade="all"
     */
    public DiscourseObject getDiscourseObject() {
        return discourseObject;
    }
    
    
    public void setDiscourseObject(DiscourseObject object) {
        discourseObject = object;
    }
    
    
    /**
     * @return
     * @hibernate.many-to-one column="owner_id" lazy="true" class="org.pgist.users.User" cascade="all"
     */
    public User getOwner() {
        return owner;
    }


    public void setOwner(User owner) {
        this.owner = owner;
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
    
    
}
