package org.pgist.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.pgist.users.User;


/**
 * 
 * @author kenny
 *
 * @hibernate.class table="pgist_discourse_post"
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
     * @hibernate.set lazy="true" table="pgist_discourse_post" cascade="all" order-by="id"
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


    public Post addChild(Post root, String content, User owner) {
        Post post = new Post();
        post.setParent(this);
        this.getChildren().add(post);
        post.setContent(content);
        post.setOwner(owner);
        post.setTime(new Date());
        if (this.getId().longValue()==root.getId().longValue()) {
            synchronized(this) {
                descendantNum++;
            }
        } else {
            propogate(root);
        }
        return post;
    }//addChild()
    
    
    public void propogate(Post root) {
        synchronized(root) {
            descendantNum++;
            if (this.getId().longValue()==root.getId().longValue()) return;
            parent.propogate(root);
        }//synchronized
    }//propogate()
    
    
}
