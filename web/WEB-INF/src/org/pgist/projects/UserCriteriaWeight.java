package org.pgist.projects;

import java.io.Serializable;
import java.util.Date;

import org.pgist.users.User;


/**
 * @author Mike and Guirong
 * 
 * @hibernate.class table="pgist_ag_user_crit_weight" lazy="true"
 */
public class UserCriteriaWeight implements Serializable {
    
    
    private int id;
    
    private User user;
    
    private Criteria criteria;
    
    private Date timestamp;
    
    private int version = 0;
    
    private Double weight;
    
    
    /**
     * @hibernate.id generator-class="native"
     */
    public int getId() {
        return id;
    }
    
    
    public void setId(int id) {
        this.id = id;
    }
    
    
    /**
     * @hibernate.many-to-one column="user_id" cascade="all" lazy="true"
     */
    public User getUser() {
        return user;
    }
    
    
    public void setUser(User user) {
        this.user = user;
    }
    
    
    /**
     * @hibernate.many-to-one column="criteria_id" cascade="all" lazy="true"
     */
    public Criteria getCriteria() {
        return criteria;
    }
    
    
    public void setCriteria(Criteria criteria) {
        this.criteria = criteria;
    }
    
    
    /**
     * @hibernate.property not-null="true"
     */
    public Date getTimestamp() {
        return timestamp;
    }
    
    
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
    
    
    /**
     * @hibernate.property not-null="true"
     */
    public int getVersion() {
        return version;
    }
    
    
    public void setVersion(int version) {
        this.version = version;
    }
    
    
    /**
     * @hibernate.property
     */
    public Double getWeight() {
        return weight;
    }
    
    
    public void setWeight(Double weight) {
        this.weight = weight;
    }
    
    
}//class UserCriteriaWeight
