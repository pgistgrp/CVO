package org.pgist.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.pgist.users.User;


/**
 * 
 * @author kenny
 *
 * @hibernate.class table="pgist_discouse_post"
 */
public class Post {
    
    
    protected Long id;
    
    protected Post parent;
    
    protected Set children = new HashSet();
    
    protected String content = "";
    
    protected User owner;
    
    protected Date time;
    
    
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
     * @hibernate.many-to-one column="parent_id" class="org.pgist.model.Post" casecad="all"
     */
    public Post getParent() {
        return parent;
    }
    
    
    public void setParent(Post parent) {
        this.parent = parent;
    }
    
    
    /**
     * @return
     * @hibernate.set lazy="true" table="pgist_discouse_post" cascade="all" order-by="id"
     * @hibernate.collection-key column="parent_id"
     * @hibernate.collection-one-to-many class="org.pgist.model.Post"
     */
    public Set getChildren() {
        return children;
    }
    
    
    public void setChildren(Set children) {
        this.children = children;
    }

    
    /**
     * @return
     * @hibernate.property type="text" not-null="true"
     */
    public String getContent() {
        return content;
    }

    
    public void setContent(String content) {
        this.content = content;
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


    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public Date getTime() {
        return time;
    }


    public void setTime(Date time) {
        this.time = time;
    }
    
    
}
