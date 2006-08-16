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
    
    
    protected Long id;
    
    protected Long discussionId;
    
    protected DiscussionPost parent;
    
    protected DiscussionPost quote;
    
    protected String title = "";
    
    protected String content = "";
    
    protected Set tags = new HashSet();
    
    protected User owner;
    
    protected int views = 0;
    
    protected int replies = 0;
    
    protected Date createTime;
    
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
     * @hibernate.property not-null="true"
     */
    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
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
     * 
     * @hibernate.set lazy="true" table="pgist_dpost_tag_link" order-by="tag_id"
     * @hibernate.collection-key column="dpost_id"
     * @hibernate.collection-many-to-many column="tag_id" class="org.pgist.cvo.Tag"
     */
    public Set getTags() {
        return tags;
    }


    public void setTags(Set tags) {
        this.tags = tags;
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
    public int getViews() {
        return views;
    }


    public void setViews(int views) {
        this.views = views;
    }


    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public int getReplies() {
        return replies;
    }


    public void setReplies(int replies) {
        this.replies = replies;
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


}//class DiscussionPost
