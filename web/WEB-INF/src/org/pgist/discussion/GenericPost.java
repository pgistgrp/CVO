package org.pgist.discussion;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.pgist.users.User;


/**
 * 
 * @author kenny
 *
 */
public abstract class GenericPost implements Serializable {
    
    
    protected Long id;
    
    protected String title = "";
    
    protected String content = "";
    
    protected Set tags = new HashSet();
    
    protected User owner;
    
    protected Date createTime;
    
    protected int numAgree;
    
    protected int numVote;
    
    protected boolean emailNotify = false;
    
    protected boolean deleted;
    
    protected Object object;
    
    
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


    abstract public Set getTags();


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
     * @hibernate.property
     */
    public boolean isEmailNotify() {
        return emailNotify;
    }


    public void setEmailNotify(boolean emailNotify) {
        this.emailNotify = emailNotify;
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


    /*
     * ------------------------------------------------------------------------
     */
    
    
    public Object getObject() {
        return object;
    }


    public void setObject(Object object) {
        this.object = object;
    }


}//abstract class GenericPost
