package org.pgist.model;

import java.util.Date;

import org.pgist.users.User;


/**
 * 
 * @author kenny
 *
 * @hibernate.class table="pgist_pgames" lazy="true"
 */
public class PGame {
    
    
    protected Long id;
    
    protected String name = "";
    
    protected String purpose = "";
    
    protected String instruction = "";
    
    protected User creator;
    
    protected Date createTime;
    
    protected boolean deleted = false;
    
    
    /**
     * @return
     * 
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
     * 
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
     * @hibernate.many-to-one column="creator_id" lazy="true"
     */
    public User getCreator() {
        return creator;
    }


    public void setCreator(User creator) {
        this.creator = creator;
    }


    /**
     * @return
     * 
     * @hibernate.property not-null="true"
     */
    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    /**
     * @return
     * 
     * @hibernate.property type="text" not-null="true"
     */
    public String getInstruction() {
        return instruction;
    }


    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }


    /**
     * @return
     * 
     * @hibernate.property type="text" not-null="true"
     */
    public String getPurpose() {
        return purpose;
    }


    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    
}//class PGame
