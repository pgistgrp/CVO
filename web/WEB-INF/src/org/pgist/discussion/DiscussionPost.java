package org.pgist.discussion;

import java.util.Date;

import org.pgist.users.User;


/**
 * 
 * @author kenny
 *
 * @hibernate.class table="pgist_dicussion_post" lazy="true"
 */
public class DiscussionPost {
    
    
    protected Long id;
    
    protected Long discussionId;
    
    protected DiscussionPost parent;
    
    protected DiscussionPost quote;
    
    protected String content = "";
    
    protected User owner;
    
    protected Date time;
    
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
    public Long getDiscussionId() {
        return discussionId;
    }


    public void setDiscussionId(Long discussionId) {
        this.discussionId = discussionId;
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
     * @hibernate.many-to-one column="quote_id" lazy="true"
     */
    public DiscussionPost getQuote() {
        return quote;
    }
    
    
    public void setQuote(DiscussionPost quote) {
        this.quote = quote;
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
    public boolean isDeleted() {
        return deleted;
    }


    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }


}//class DiscussionPost
