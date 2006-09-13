package org.pgist.projects;

import java.util.Date;
import org.pgist.users.User;


/**
 * @author  Mike and Guirong
 * @hibernate.class  table="pgist_ag_user_crit_weight" lazy="true"
 */
public class UserCriteriaWeight {
    
    
    private int id;
    
    private User user;
    
    private Criteria criteria;
    
    private Date timestamp;
    
    private int version = 0;
    
    private Double weight;
    
    
    /**
     * @hibernate.id  generator-class="native"
     * @uml.property  name="id"
     */
    public int getId() {
        return id;
    }
    
    
    /**
     * @param id  the id to set
     * @uml.property  name="id"
     */
    public void setId(int id) {
        this.id = id;
    }
    
    
    /**
     * @hibernate.property  not-null="true"
     * @uml.property  name="user"
     */
    public User getUser() {
        return user;
    }
    
    
    /**
     * @param user  the user to set
     * @uml.property  name="user"
     */
    public void setUser(User user) {
        this.user = user;
    }
    
    
    /**
     * @hibernate.property  not-null="true"
     * @uml.property  name="criteria"
     */
    public Criteria getCriteria() {
        return criteria;
    }
    
    
    /**
     * @param criteria  the criteria to set
     * @uml.property  name="criteria"
     */
    public void setCriteria(Criteria criteria) {
        this.criteria = criteria;
    }
    
    
    /**
     * @hibernate.property
     * @uml.property  name="timestamp"
     */
    public Date getTimestamp() {
        return timestamp;
    }
    
    
    /**
     * @param timestamp  the timestamp to set
     * @uml.property  name="timestamp"
     */
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
    
    
    /**
     * @hibernate.property  not-null="true"
     * @uml.property  name="version"
     */
    public int getVersion() {
        return version;
    }
    
    
    /**
     * @param version  the version to set
     * @uml.property  name="version"
     */
    public void setVersion(int version) {
        this.version = version;
    }
    
    
    /**
     * @hibernate.property
     * @uml.property  name="weight"
     */
    public Double getWeight() {
        return weight;
    }
    
    
    /**
     * @param weight  the weight to set
     * @uml.property  name="weight"
     */
    public void setWeight(Double weight) {
        this.weight = weight;
    }
    
    
}//class UserCriteriaWeight
