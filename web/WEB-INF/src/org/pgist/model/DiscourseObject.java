package org.pgist.model;

import org.pgist.users.User;


/**
 * 
 * @author kenny
 *
 * @hibernate.class table="pgist_discourse_object"
 */
public class DiscourseObject {
    
    
    protected Long id;
    
    protected Discussible target;
    
    protected Post root;
    
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
     * @hibernate.many-to-one column="root_id" class="org.pgist.model.Post" lazy="true" casecad="all"
     */
    public Post getRoot() {
        return root;
    }
    
    
    public void setRoot(Post root) {
        this.root = root;
    }
    
    
    /**
     * @return
     * @hibernate.many-to-one column="target_id" class="org.pgist.model.Discussible" lazy="true" casecad="all"
     */
    public Discussible getTarget() {
        return target;
    }
    
    
    public void setTarget(Discussible target) {
        this.target = target;
    }
    
    
    /**
     * @return
     * @hibernate.many-to-one column="owner_id" lazy="true" class="org.pgist.users.User" casecad="all"
     */
    public User getOwner() {
        return owner;
    }


    public void setOwner(User owner) {
        this.owner = owner;
    }
    
    
}
