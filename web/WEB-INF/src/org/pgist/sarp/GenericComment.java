package org.pgist.sarp;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.pgist.users.User;

/**
 * @author kenny
 *
 * @hibernate.class table="sarp_generic_comment"
 */
public abstract class GenericComment {
    
    
    protected Long id;
    
    protected Long workflowId;
    
    protected String title;
    
    protected String content;
    
    protected User author;
    
    protected Date createTime;
    
    protected Set tags = new HashSet();
    
    protected int numAgree;
    
    protected int numVote;
    
    protected boolean deleted;
    
    private boolean emailNotify = false;
    
    
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
    public Long getWorkflowId() {
        return workflowId;
    }
    public void setWorkflowId(Long workflowId) {
        this.workflowId = workflowId;
    }


    /**
     * @return
     * @hibernate.property
     */
    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }


    /**
     * @return
     * @hibernate.property type="text"
     */
    public String getContent() {
        return content;
    }


    public void setContent(String content) {
        this.content = content;
    }


    /**
     * @return
     * @hibernate.many-to-one column="author_id" lazy="true" cascade="all"
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
     * @hibernate.property not-null="true"
     */
    public boolean isEmailNotify() {
        return emailNotify;
    }


    public void setEmailNotify(boolean emailNotify) {
        this.emailNotify = emailNotify;
    }


}
