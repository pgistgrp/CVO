package org.pgist.projects;

import java.sql.*;

import org.pgist.users.*;

/**
 * @author Arika and Guirong
 * @hibernate.class table="pgist_cm_pack_score" lazy="true"
 */
class PackageUerScore {
    private Package thePackage;
    private User user;
    private Float idealPointScore;
    private Date timestamp;
    private int versionNumber;
    private Float concordance;
    private Long id;

    /**
     * @hibernate.property not-null="true"
     */
    public Package getThePackage() {
        return thePackage;
    }

    /**
     * @hibernate.property not-null="true"
     */
    public User getUser() {
        return user;
    }

    /**
     * @hibernate.property
     */
    public Float getIdealPointScore() {
        return idealPointScore;
    }

    /**
     * @hibernate.property not-null="true"
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * @hibernate.property
     */
    public int getVersionNumber() {

        return versionNumber;
    }

    /**
     * @hibernate.property
     */
    public Float getConcordance() {
        return concordance;
    }

    /**
     * @hibernate.id generator-class="native"
     */
    public Long getId() {
        return id;
    }

    public void setThePackage(Package thePackage) {
        this.thePackage = thePackage;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setIdealPointScore(Float idealPointScore) {
        this.idealPointScore = idealPointScore;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public void setVersionNumber(int versionNumber) {

        this.versionNumber = versionNumber;
    }

    public void setConcordance(Float concordance) {
        this.concordance = concordance;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
