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
    
    protected int numAgree = 0;
    
    protected int numVote = 0;
    
    protected Date respTime;
    
    protected Discussion discussion = new Discussion();
    
    
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
     * @hibernate.property column="discussion_id" not-null="true"
     */
    public Discussion getDiscussion() {
        return discussion;
    }


    public void setDiscussion(Discussion discussion) {
        this.discussion = discussion;
    }


}//abstract class GenericInfo
