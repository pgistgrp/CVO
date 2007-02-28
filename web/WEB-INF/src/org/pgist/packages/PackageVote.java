package org.pgist.packages;

import java.io.Serializable;

import org.pgist.cvo.CCT;
import org.pgist.users.User;


/**
 * 
 * @author kenny
 *
 * @hibernate.class table="pgist_package_vote" lazy="true"
 */
public class PackageVote implements Serializable {
    
    
    /**
     * participant has not voted on this package yet<br>
     * value: 0<br>
     */
    public static int VOTING_UNKNOWN = 0;
    
    /**
     * participant votes high.<br>
     * value: 1<br>
     */
    public static int VOTING_HIGH    = 1;
    
    /**
     * participant votes medium.<br>
     * value: 2<br>
     */
    public static int VOTING_MEDIUM  = 2;
    
    /**
     * participant votes low.<br>
     * value: 3<br>
     */
    public static int VOTING_LOW     = 3;
    
    
    private Long id;
    
    private CCT cct;
    
    private ClusteredPackage clusteredPackage;
    
    private int phase;
    
    private int result;
    
    private User author;
    
    
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
     * @return
     * 
     * @hibernate.many-to-one column="cct_id" cascade="none"
     */
    public CCT getCct() {
        return cct;
    }


    public void setCct(CCT cct) {
        this.cct = cct;
    }


    /**
     * @return
     * 
     * @hibernate.many-to-one column="pkg_id" cascade="none"
     */
    public ClusteredPackage getClusteredPackage() {
        return clusteredPackage;
    }


    public void setClusteredPackage(ClusteredPackage clusteredPackage) {
        this.clusteredPackage = clusteredPackage;
    }


    /**
     * @hibernate.property
     */
    public int getResult() {
        return result;
    }


    public void setResult(int result) {
        this.result = result;
    }


    /**
     * @hibernate.property
     */
    public int getPhase() {
        return phase;
    }


    public void setPhase(int phase) {
        this.phase = phase;
    }


    /**
     * @return
     * 
     * @hibernate.many-to-one column="user_id" cascade="none"
     */
    public User getAuthor() {
        return author;
    }


    public void setAuthor(User author) {
        this.author = author;
    }


}//class PackageVote
