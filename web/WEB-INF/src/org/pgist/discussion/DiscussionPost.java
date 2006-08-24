package org.pgist.discussion;

import java.util.Date;
import java.util.Set;


/**
 * 
 * @author kenny
 *
 * @hibernate.class table="pgist_dicussion_post" lazy="true"
 */
public class DiscussionPost extends GenericPost {
    
    
    protected Discussion discussion;
    
    protected DiscussionReply lastReply;
    
    protected int views = 0;
    
    protected int replies = 0;
    
    protected Date replyTime;
    
    
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
     * @hibernate.collection-many-to-many column="tag_id" class="org.pgist.tag.Tag"
     */
    public Set getTags() {
        return tags;
    }


}//class DiscussionPost
