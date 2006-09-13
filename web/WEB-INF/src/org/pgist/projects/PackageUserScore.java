package org.pgist.projects;

import java.util.Date;
import org.pgist.users.User;


/**
 * @author  Arika and Guirong
 * @hibernate.class  table="pgist_cm_pack_user_score" lazy="true"
 */
public class PackageUserScore {
    
    
    private Long id;
    
    private Package pkg;
    
    private User user;
    
    private Float idealPointScore;
    
    private Date timestamp;
    
    private int versionNumber;
    
    private Float concordance;
    
    
    /**
     * @hibernate.id  generator-class="native"
     * @uml.property  name="id"
     */
    public Long getId() {
        return id;
    }
    
    
    /**
     * @param id  the id to set
     * @uml.property  name="id"
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    
    /**
     * @hibernate.property  not-null="true"
     * @uml.property  name="pkg"
     */
    public Package getPkg() {
        return pkg;
    }
    
    
    /**
     * @param pkg  the pkg to set
     * @uml.property  name="pkg"
     */
    public void setPkg(Package thePackage) {
        this.pkg = thePackage;
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
     * @hibernate.property
     * @uml.property  name="idealPointScore"
     */
    public Float getIdealPointScore() {
        return idealPointScore;
    }
    
    
    /**
     * @param idealPointScore  the idealPointScore to set
     * @uml.property  name="idealPointScore"
     */
    public void setIdealPointScore(Float idealPointScore) {
        this.idealPointScore = idealPointScore;
    }
    
    
    /**
     * @hibernate.property  not-null="true"
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
     * @hibernate.property
     * @uml.property  name="versionNumber"
     */
    public int getVersionNumber() {
        return versionNumber;
    }
    
    
    /**
     * @param versionNumber  the versionNumber to set
     * @uml.property  name="versionNumber"
     */
    public void setVersionNumber(int versionNumber) {
        this.versionNumber = versionNumber;
    }
    
    
    /**
     * @hibernate.property
     * @uml.property  name="concordance"
     */
    public Float getConcordance() {
        return concordance;
    }
    
    
    /**
     * @param concordance  the concordance to set
     * @uml.property  name="concordance"
     */
    public void setConcordance(Float concordance) {
        this.concordance = concordance;
    }
    
    
}//class PackageUserScore
