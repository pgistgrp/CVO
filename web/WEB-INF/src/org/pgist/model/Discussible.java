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
     * @hibernate.many-to-one column="do_id" class="org.pgist.model.DiscourseObject" casecad="all"
     */
    public DiscourseObject getDiscourseObject() {
        return discourseObject;
    }
    
    
    public void setDiscourseObject(DiscourseObject object) {
        discourseObject = object;
    }
    
    
    /**
     * @return
     * @hibernate.many-to-one column="owner_id" class="org.pgist.users.User" casecad="all"
     */
    public User getOwner() {
        return owner;
    }


    public void setOwner(User owner) {
        this.owner = owner;
    }
    
    
}
