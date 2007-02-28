package org.pgist.packages;

import java.io.Serializable;

import org.pgist.cvo.CCT;


/**
 * 
 * @author kenny
 *
 * @hibernate.class table="pgist_package_vote" lazy="true"
 */
public class PackageVoteStat implements Serializable {
    
    
    public static int PHASE_ONE = 1;
    
    public static int PHASE_TWO = 2;
    
    
    private Long id;
    
    private CCT cct;
    
    private ClusteredPackage clusteredPackage;
    
    private int phase;
    
    private int voters;
    
    private int high;
    
    private int medium;
    
    private int low;
    
    
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
     * @return
     * 
     * @hibernate.property
     */
    public int getHigh() {
        return high;
    }


    public void setHigh(int high) {
        this.high = high;
    }


    /**
     * @return
     * 
     * @hibernate.property
     */
    public int getLow() {
        return low;
    }


    public void setLow(int low) {
        this.low = low;
    }


    /**
     * @return
     * 
     * @hibernate.property
     */
    public int getMedium() {
        return medium;
    }


    public void setMedium(int medium) {
        this.medium = medium;
    }


    /**
     * @return
     * 
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
     * @hibernate.property
     */
    public int getVoters() {
        return voters;
    }


    public void setVoters(int voters) {
        this.voters = voters;
    }
    
    
}//class PackageVoteStat
