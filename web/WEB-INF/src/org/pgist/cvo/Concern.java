package org.pgist.cvo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.pgist.users.User;


/**
 * 
 * @author kenny
 *
 * @hibernate.class table="pgist_cvo_concerns" lazy="true"
 */
public class Concern {
    
    
    protected Long id;
    
    protected String content = "";
    
    protected User author;
    
    protected Date createTime;
    
    private boolean deleted = false;
    
    protected Set tags = new HashSet();
    
    
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
     * @hibernate.property
     */
    public String getContent() {
        return content;
    }


    public void setContent(String content) {
        this.content = content;
    }


    /**
     * @return
     * @hibernate.many-to-one column="author_id" lazy="true" class="org.pgist.users.User" cascade="all"
     */
    public User getAuthor() {
        return author;
    }


    public void setAuthor(User author) {
        this.author = author;
    }


    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public Date getCreateTime() {
        return createTime;
    }


    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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


    /**
     * @return
     * 
     * @hibernate.set lazy="true" table="pgist_cvo_tags" order-by="id" cascade="all"
     * @hibernate.collection-key column="parent_id"
     * @hibernate.collection-one-to-many class="org.pgist.cvo.Tag"
     */
    public Set getTags() {
        return tags;
    }


    public void setTags(Set tags) {
        this.tags = tags;
    }
    
    
}//class Concern
