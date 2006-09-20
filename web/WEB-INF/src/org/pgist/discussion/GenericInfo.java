package org.pgist.discussion;

import java.io.Serializable;
import java.util.Date;


/**
 * 
 * @author kenny
 *
 */
public abstract class GenericInfo implements Serializable {
    
    
    protected Long id;
    
    protected int numDiscussion;
    
    protected int numAgree;
    
    protected int numVote;
    
    private Date respTime;
    
    protected DiscussionPost lastPost;
    
    
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
    public int getNumDiscussion() {
        return numDiscussion;
    }
    
    
    public void setNumDiscussion(int numDiscussion) {
        this.numDiscussion = numDiscussion;
    }
    
    
    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public int getNumAgree() {
        return numAgree;
    }
    
    
    public void setNumAgree(int numAgree) {
        this.numAgree = numAgree;
    }
    
    
    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public int getNumVote() {
        return numVote;
    }
    
    
    public void setNumVote(int numVote) {
        this.numVote = numVote;
    }
    
    
    /**
     * @return
     * @hibernate.property column="response_time" not-null="true"
     */
    public Date getRespTime() {
        return respTime;
    }


    public void setRespTime(Date respTime) {
        this.respTime = respTime;
    }


    /**
     * @return
     * 
     * @hibernate.many-to-one column="last_post"
     */
    public DiscussionPost getLastPost() {
        return lastPost;
    }
    
    
    public void setLastPost(DiscussionPost lastPost) {
        this.lastPost = lastPost;
    }
    
    
}//abstract class GenericInfo
