package org.pgist.discussion;

import java.util.Date;
import java.util.Set;


/**
 * 
 * @author kenny
 *
 * @hibernate.class table="pgist_discussion_post" lazy="true"
 */
public class DiscussionPost extends GenericPost {
    
    
    protected Discussion discussion;
    
    protected DiscussionReply lastReply;
    
    protected int views = 0;
    
    protected int replies = 0;
    
    protected Date replyTime;
    
    protected boolean emailNotify = false;
    
    /**
     * value is a transient variable which can hold any object temporarilly
     */
    private Object value;
    
    
    /**
     * @return
     * 
     * @hibernate.many-to-one column="discussion_id"
     */
    public Discussion getDiscussion() {
        return discussion;
    }
    
    
    public void setDiscussion(Discussion discussion) {
        this.discussion = discussion;
    }
    
    
    /**
     * @return
     * 
     * @hibernate.many-to-one column="last_reply"
     */
    public DiscussionReply getLastReply() {
        return lastReply;
    }
    
    
    public void setLastReply(DiscussionReply lastReply) {
        this.lastReply = lastReply;
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
     * @hibernate.property
     */
    public Date getReplyTime() {
        return replyTime;
    }


    public void setReplyTime(Date replyTime) {
        this.replyTime = replyTime;
    }


    /**
     * @return
     * 
     * @hibernate.set lazy="true" table="pgist_dpost_tag_link" order-by="tag_id"
     * @hibernate.collection-key column="dpost_id"
     * @hibernate.collection-many-to-many column="tag_id" class="org.pgist.tagging.Tag"
     */
    public Set getTags() {
        return tags;
    }


    /**
     * @return
     * @hibernate.property
     */
    public boolean isEmailNotify() {
        return emailNotify;
    }


    public void setEmailNotify(boolean emailNotify) {
        this.emailNotify = emailNotify;
    }
    
    
    /*
     * ------------------------------------------------------------------------
     */


    public Object getValue() {
        return value;
    }


    public void setValue(Object value) {
        this.value = value;
    }


}//class DiscussionPost
