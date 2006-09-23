package org.pgist.projects;

import java.io.Serializable;
import java.util.Date;

import org.pgist.users.User;


/**
 * @author Arika and Guirong
 * 
 * @hibernate.class table="pgist_cm_pack_user_score" lazy="true"
 */
public class PackageUserScore implements Serializable {
    
    
    private Long id;
    
    private Package pkg;
    
    private User user;
    
    private Float idealPointScore;
    
    private Date timestamp;
    
    private int versionNumber;
    
    private Float concordance;
    
    
    /**
     * @hibernate.id generator-class="native"
     */
    public Long getId() {
        return id;
    }
    
    
    public void setId(Long id) {
        this.id = id;
    }
    
    
    /**
     * @hibernate.many-to-one column="pkg_id" cascade="all" lazy="true"
     */
    public Package getPkg() {
        return pkg;
    }
    
    
    public void setPkg(Package thePackage) {
        this.pkg = thePackage;
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
     * @hibernate.property
     */
    public Float getIdealPointScore() {
        return idealPointScore;
    }
    
    
    public void setIdealPointScore(Float idealPointScore) {
        this.idealPointScore = idealPointScore;
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
     * @hibernate.property
     */
    public int getVersionNumber() {
        return versionNumber;
    }
    
    
    public void setVersionNumber(int versionNumber) {
        this.versionNumber = versionNumber;
    }
    
    
    /**
     * @hibernate.property
     */
    public Float getConcordance() {
        return concordance;
    }
    
    
    public void setConcordance(Float concordance) {
        this.concordance = concordance;
    }
    
    
}//class PackageUserScore
