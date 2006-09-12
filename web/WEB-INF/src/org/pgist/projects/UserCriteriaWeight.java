package org.pgist.projects;

import java.sql.*;

import org.pgist.users.*;

/**
 * @author Mike and Guirong
 * @hibernate.class table="pgist_ag_weight" lazy="true"
 */
class UserCriteriaWeight {
    private User user;
    private Criteria criteria;
    private Date timestamp;
    private int version = 0;
    private int id;
    private Double weight;

    /**
     * @hibernate.property not-null="true"
     */
    public User getUser() {
        return user;
    }

    /**
     * @hibernate.property not-null="true"
     */
    public Criteria getCriteria() {
        return criteria;
    }

    /**
     * @hibernate.property
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * @hibernate.property not-null="true"
     */
    public int getVersion() {
        return version;
    }

    /**
     * @hibernate.id generator-class="native"
     */
    public int getId() {
        return id;
    }

    /**
     * @hibernate.property
     */
    public Double getWeight() {
        return weight;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCriteria(Criteria criteria) {
        this.criteria = criteria;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }
}
