package org.pgist.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.pgist.users.User;


/**
 * 
 * @author kenny
 *
 * @hibernate.class table="pgist_discourse_post" lazy="true"
 * @hibernate.cache usage="read-write"
 */
public class Post {
    
    
    protected Long id;
    
    protected Post parent;
    
    protected Set children = new HashSet();
    
    protected String content = "";
    
    protected User owner;
    
    protected Date time;
    
    protected int descendantNum = 0;
    
    protected int category = 0;
    
    protected boolean target = false;
    
    protected Post root;
    
    
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
     * @hibernate.many-to-one column="parent_id" lazy="true"
     */
    public Post getParent() {
        return parent;
    }
    
    
    public void setParent(Post parent) {
        this.parent = parent;
    }
    
    
    /**
     * @return
     * @hibernate.set lazy="true" table="pgist_discourse_post" order-by="id"
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
     * @hibernate.many-to-one column="owner_id" lazy="true" class="org.pgist.users.User"
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


    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public int getDescendantNum() {
        return descendantNum;
    }


    public void setDescendantNum(int descendantNum) {
        this.descendantNum = descendantNum;
    }
    
    
    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public int getCategory() {
        return category;
    }


    public void setCategory(int category) {
        this.category = category;
    }


    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public boolean isTarget() {
        return target;
    }


    public void setTarget(boolean target) {
        this.target = target;
    }


    /**
     * @return
     * @hibernate.many-to-one column="root_id" lazy="true" class="org.pgist.model.Post"
     */
    public Post getRoot() {
        return root;
    }


    public void setRoot(Post root) {
        this.root = root;
    }


    public Post addChild(String content, User owner) {
        Post post = new Post();
        post.setRoot(getRoot());
        post.setParent(this);
        this.getChildren().add(post);
        post.setContent(content);
        post.setOwner(owner);
        post.setTime(new Date());
        propogate();
        return post;
    }//addChild()
    
    
    public void propogate() {
        synchronized(getRoot()) {
            setDescendantNum(getDescendantNum()+1);
        }//synchronized
        if (this.isTarget()) return;
        this.getParent().propogate();
    }//propogate()


}
