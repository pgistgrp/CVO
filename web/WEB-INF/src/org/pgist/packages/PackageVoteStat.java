package org.pgist.packages;

import org.pgist.cvo.CCT;
import org.pgist.users.User;


/**
 * 
 * @author kenny
 *
 * @hibernate.class table="pgist_package_vote" lazy="true"
 */
public class PackageVoteStat {
    
    
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
     * @hibernate.property
     */
    
    
}//class PackageVoteStat
