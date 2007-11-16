package org.pgist.discussion;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.pgist.users.User;


/**
 * 
 * @author kenny
 *
 * @hibernate.class table="pgist_dicussion_post" lazy="true"
 */
public class DiscussionPost {
    
    
    public static final int CATEGORY_CONCERN = 1;
    
    public static final int CATEGORY_COMMENT = 2;
    
    
    protected Long id;
    
    protected DiscussionPost parent;
    
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
     * @hibernate.many-to-one column="parent_id" lazy="true"
     */
    public DiscussionPost getParent() {
        return parent;
    }
    
    
    public void setParent(DiscussionPost parent) {
        this.parent = parent;
    }
    
    
    /**
     * @return
     * @hibernate.set lazy="true" table="pgist_discourse_post" order-by="id" cascade="all"
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


    /*
     * ------------------------------------------------------------------------
     */
    

    public DiscussionPost addChild(String content, User owner) {
        DiscussionPost post = new DiscussionPost();
        
        post.setParent(this);
        this.getChildren().add(post);
        post.setContent(content);
        post.setOwner(owner);
        post.setTime(new Date());
        
        return post;
    }//addChild()
    
    
}//class DiscussionPost
